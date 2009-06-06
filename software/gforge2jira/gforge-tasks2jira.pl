#!/usr/bin/env perl
#######################################################
#  Name:  gforge-tasks2jira.pl
#  Author:  Steven S. Saksa
#  $Id$
#  Description:   This script is used to produce a csv file containing all tasks in a given "queue" for a given group.  Most groups
#  			will have multiple queues so there will be multiple output files with the group and queue in the name.
#  Usage: ./gforge-tasks2jira.pl "group name" # " are recommended when group names contain spaces
#
#######################################################

use Data::Dumper;
use DBI;
use Cwd 'abs_path';

my $requestGroup, $dbServer, $dbPort, $dbUser,$dbPass="";
my $groupName="";
my %userHash;
my %artifactHash;
my @artifactIDList;
my %queueHash;

print "Verifying command line options\n";
&verifyOptions();

my $dbh;
if (defined $dbServer)
{
	$dbh = DBI->connect("dbi:Pg:host=$dbServer;port=$dbPort;database=$dbName", "$dbUser", "$dbPass") or die "Couldn't connect to database: " . DBI->errstr;
}
else
{
	$dbh = DBI->connect("dbi:Pg:database=$dbName") or die "Couldn't connect to database: " . DBI->errstr;
}

mkdir("target");
open (DUMP, ">target/gforge-tasks2jira.dmp") || die "Could not write dump.log\n";
open (LOG, ">target/gforge-tasks2jira.log") || die "Could not write dump.log\n";

#&setUsers();
#exit 0;

print "Verifying Group\n";
&verifyGroup;
print "Reading user list from database\n";
&getUsers;
print "Reading artifacts for ${groupName}\n";
&getArtifacts;
print "Processing Followups\n";
&getMessages();
print "Processing History\n";
&getHistory();
print "Generating CSVs and CNFs\n";
&generateCSVs();

#print DUMP Dumper(%userHash);
print DUMP "### Artifact Hash\n";
print DUMP Dumper(%artifactHash);
print DUMP "### Queue Field Hash\n";
print DUMP Dumper(%queueFieldList);



close DUMP;
close LOG;

sub verifyGroup () 
{
	my @groupList;
	
	my $groupQuery="select group_name from groups where group_name='${requestGroup}'";
	print LOG "Using Query String - $groupQuery\n";
	my $sth = $dbh->prepare($groupQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	$sth->execute;
	while( ($groupResult) = $sth->fetchrow_array)
	{
		print LOG "Adding ${groupResult} to list\n";
		$groupName=$groupResult;
		push @groupList, $groupName;
	}
	$sth->finish();
	my $cnt=@groupList;
	print LOG "Found ${cnt} matches.\n";
	if ($cnt > 1)
	{
		print "More than one match found, please run utility with one of the names below\n";
		foreach  my $group (@groupList)
		{
			print "\t$group\n";
		}
		exit 1;
	}
	elsif ($cnt == 1)
	{
		print "Found single match for ${requestGroup} -> '${groupName}'\n";
		print LOG "Found single match for ${requestGroup} -> '${groupName}'\n";
	}
	else
	{
		print "No matches found for that name\n";
		my $groupQuery="select group_name from groups where lower(group_name) like ?";
		print LOG "Using Query String - $groupQuery\n";
		my $sth = $dbh->prepare($groupQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
		my $searchGroup="%".lc($requestGroup)."%";
		$sth->execute($searchGroup);
		while( ($groupResult) = $sth->fetchrow_array)
		{
			print "\t$groupResult\n";
		}
		exit 1;
	}
}
sub getArtifacts()
{
	my $artifactQuery="select 
	pgl.project_name,
	pt.project_task_id,
	pt.summary,
	pt.details,
	pt.percent_complete,
	pt.priority,
	pt.hours,
	pt.start_date,
	pt.end_date,
	pt.created_by,
	s.status_name,
	pc.category_name,
	pt.duration,
	pt.parent_id,
	pt.last_modified_date,
	pat.assigned_to_id,
	pd.is_dependent_on_task_id
	from project_task pt left outer join project_dependencies pd on pt.project_task_id=pd.project_task_id, project_group_list pgl, groups g, project_status s, project_category pc, project_assigned_to pat
	where   g.group_name=?
		and     g.group_id=pgl.group_id
		and     pt.group_project_id=pgl.group_project_id
		and     pt.status_id=s.status_id
		and     pt.category_id=pc.category_id
		and     pt.project_task_id=pat.project_task_id
		order by  1,2,3";

	print LOG "Using Query String - $artifactQuery\n";
	my $sth = $dbh->prepare($artifactQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	$sth->execute($groupName);
	while( my ($taskQueue, $taskID, $taskSummary, $taskDetails, $taskPercentComplete, $taskPriority, $taskHours, $taskStartEpoch, $taskEndEpoch, $taskCreatedBy, $taskStatus, $taskCategory, $taskDuration, $taskParentId,$taskModEpoch,$taskAssignedTo, $taskDependsOn) = $sth->fetchrow_array)
	{
		$artifactHash{$taskID}{"Queue"}=$taskQueue; 
		$artifactHash{$taskID}{"Summary"}=$taskSummary; 
		$artifactHash{$taskID}{"Details"}=$taskDetails; 
		$artifactHash{$taskID}{"PercentComplete"}=$taskPercentComplete; 
		$artifactHash{$taskID}{"Priority"}=$taskPriority; 
		$artifactHash{$taskID}{"Hours"}=$taskHours; 
		$artifactHash{$taskID}{"StartEpoch"}=$taskStartEpoch; 
		$artifactHash{$taskID}{"EndEpoch"}=$taskEndEpoch; 
		$artifactHash{$taskID}{"CreatedBy"}=$userHash{$taskCreatedBy}; 
		$artifactHash{$taskID}{"Status"}=$taskStatus; 
		$artifactHash{$taskID}{"Category"}=$taskCategory; 
		$artifactHash{$taskID}{"Duration"}=$taskDuration; 
		$artifactHash{$taskID}{"ParentID"}=$taskParentId; 
		$artifactHash{$taskID}{"ModEpoch"}=$taskModEpoch; 
		$artifactHash{$taskID}{"AssignedTo"}=$userHash{$taskAssignedTo}; 
		$artifactHash{$taskID}{"DependsOn"}=$taskDependsOn; 
		$artifactHash{$taskID}{"TaskHistory"}="";
		$artifactHash{$taskID}{"TaskMessages"}="";
		$artifactHash{$taskID}{"JiraIssueType"}="Task";
		push @artifactIDList, $taskID;
		$queueHash{$taskQueue}++;

		$artifactHash{$taskID}{"CustomFields"}="";

		$artifactHash{$taskID}{"CustomFields"}.="#####\nCategory\t:  $taskCategory\n######\n\n";
		$artifactHash{$taskID}{"CustomFields"}.="#####\nDuration\t:  $taskDuration\n######\n\n";
		$artifactHash{$taskID}{"CustomFields"}.="#####\nHours\t:  $taskHours\n######\n\n";
		$artifactHash{$taskID}{"CustomFields"}.="#####\nPercentComplete\t:  $taskPercentComplete\n######\n\n";
		
		$artifactHash{$taskID}{"CustomFields"}.="#####\nParentId\t:  $taskParentId\n######\n\n";
		$artifactHash{$taskID}{"CustomFields"}.="#####\nDependsOn\t:  $taskDependsOn\n######\n\n";
	}
	$sth->finish();
}

sub getUsers () 
{
	my $userQuery="select user_id,user_name, email from users";
	print LOG "Using Query String - $userQuery\n";
	my $sth = $dbh->prepare($userQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	$sth->execute;
	while( my ($userId, $userName, $email) = $sth->fetchrow_array)
	{
		$userHash{$userId}="$userName";
	}
	$sth->finish();
}
sub setUsers () 
{
	my @userList;
	my $userQuery="select user_id from users";
	print LOG "Using Query String - $userQuery\n";
	my $sth = $dbh->prepare($userQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	$sth->execute;
	while( my ($userId) = $sth->fetchrow_array)
	{
		push @userList, $userId;
	}
	$sth->finish();
	foreach my $userid (@userList)
	{
		my $name="user$userid";
		my $email="email$userid";
		my $update_handle= $dbh->prepare_cached('update users set user_name=?, email=? where user_id=?');
		$update_handle->execute($name, $email,$userid) or  die "Couldn't update: " . $dbh->errstr;
	}
}
sub getHistory()
{
	my $historyQuery="select pt.project_task_id, ph.field_name, ph.old_value, ph.mod_by, ph.mod_date
		from project_task pt, project_group_list pgl, groups g, project_history ph
		where   g.group_name=?
		and     g.group_id=pgl.group_id
		and     pt.group_project_id=pgl.group_project_id
		and     pt.project_task_id=ph.project_task_id
		order by  1,2,3";

	print LOG "Using Query String - $historyQuery\n";
	my $sth = $dbh->prepare($historyQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	my $messageHistory="" ;
	$sth->execute($groupName);
	while( my ($id, $fieldName, $oldValue, $modBy, $modepoch) = $sth->fetchrow_array)
	{
		my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= gmtime($modepoch);
		my $modDate=sprintf("%02d/%02d/%04d %02d:%02d:%02d",$mon+1,$mday,$year+1900,$hour,$min,$sec);

		if ($fieldName =~ /date/i)
		{               
			my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= gmtime($oldValue);
			$oldValue=sprintf("%02d/%02d/%04d %02d:%02d:%02d",$mon+1,$mday,$year+1900,$hour,$min,$sec);
		}               

		my $user=$userHash{$modBy};
		$taskHistory= "${user} - ${modDate} - ${fieldName} old value '${oldValue}'\n";
		$artifactHash{$id}{"TaskHistory"}.=$taskHistory;
	}
	$sth->finish();
}
sub getMessages()
{
	my $messageQuery="select  pt.project_task_id, pm.posted_by, pm.postdate, pm.body
		from project_task pt, project_group_list pgl, groups g, project_messages pm
		where   g.group_name=?
		and     g.group_id=pgl.group_id
		and     pt.group_project_id=pgl.group_project_id
		and     pt.project_task_id=pm.project_task_id
		order by  1,2,3";

	print LOG "Using Query String - $messageQuery\n";
	my $sth = $dbh->prepare($messageQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	my $taskMessages="" ;
	$sth->execute($groupName);
	while( my ($id, $postedBy, $postEpoch,$body) = $sth->fetchrow_array)
	{
		my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= gmtime($postEpoch);
		my $postDate=sprintf("%02d/%02d/%04d %02d:%02d:%02d",$mon+1,$mday,$year+1900,$hour,$min,$sec);

		my $user=$userHash{$postedBy};
		$taskMessages= "${user} - ${postDate}\n${body}\n\n";
		$artifactHash{$id}{"TaskMessages"}.=$taskMessages;
	}
	$sth->finish();
}
sub generateCSVs()
{
	my $fieldValue;
	foreach my $queue (sort keys %queueHash)
	{
		mkdir("target");
		my $fname="target/tasks-exp-${groupName}-${queue}.csv";
		$fname=~s/\s+//g;
		$absFname=abs_path($fname);
		print "\t${absFname}\n";
		&createJiraCnf($queue);
		open (OUTFILE, ">$fname") ||die "Could not create $fname\n";
		print OUTFILE "GforgeID, ";
		my @fieldList=();
		my $firstId=@artifactIDList[1];
		foreach my $fieldName (sort keys %{$artifactHash{$firstId}})
		{
			push (@fieldList,$fieldName);
			$fieldName=~ s/epoch/Date/i;
			print OUTFILE "$fieldName, ";
		}
		print OUTFILE "\n";
		foreach my $id (@artifactIDList)
		{
			next if($artifactHash{$id}{"Queue"} ne $queue);
			print OUTFILE "$id, ";
			foreach my $fieldName (@fieldList)
			{
				$fieldValue=$artifactHash{$id}{$fieldName};

				if ($fieldName =~ /epoch/i)
				{
					my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= gmtime($fieldValue);
					my $tmpDate=sprintf("%02d/%02d/%04d %02d:%02d:%02d",$mon+1,$mday,$year+1900,$hour,$min,$sec);
					$fieldValue=$tmpDate;
				}
				if ($fieldValue =~ /\"|\n|,/)
				{
					$fieldValue=~s/\"/'/g;
					#$fieldValue=~s/\'/\\\'/g;
					$fieldValue=~s/\,//g;
					$fieldValue=~s/[\n\r]+/\r/g;
					$fieldValue=~s/^/\"/;
					$fieldValue=~s/$/\"/;
				}
				print OUTFILE "$fieldValue,";
			}
			print OUTFILE "\n";
		}
		close(OUTFILE);
	}

}
sub createJiraCnf ($)
{
	my $queue=$_[0];
	my $fname="target/tasks-exp-${groupName}-${queue}.cnf";
	$fname=~s/\s+//g;
	$absFname=abs_path($fname);
	print "\t${absFname}\n";
	open (OUTFILE, ">$fname") ||die "Could not create $fname\n";

	my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= gmtime($fieldValue);
	my $tmpDate=sprintf("%02d/%02d/%04d %02d:%02d:%02d",$mon+1,$mday,$year+1900,$hour,$min,$sec);

	# project level details
	print OUTFILE "# Written by gforge-tasks2jira.pl\n";
	print OUTFILE "# $(tmpDate)\n";
	print OUTFILE "delimiter = \\,\n";
	print OUTFILE "existingprojectkey = ${groupName}\n"; 
	print OUTFILE "importsingleproject = false\n"; 
	print OUTFILE "importexistingproject = true\n"; 
	print OUTFILE "mapfromcsv = false\n"; 
	print OUTFILE "user.email.suffix = \@mail.nih.gov\n"; 

	# fields
	print OUTFILE "field.GforgeID = customfield_10001\n"; 
	print OUTFILE "field.JiraIssueType = type\n"; 
	print OUTFILE "field.EndDate = duedate\n"; 
	print OUTFILE "field.Details = description\n"; 
	print OUTFILE "field.Queue = components\n"; 
	print OUTFILE "field.Status = status\n"; 
	print OUTFILE "field.TaskMessages = comment\n"; 
	print OUTFILE "field.TaskHistory = comment\n"; 
	print OUTFILE "field.CustomFields = comment\n"; 
	print OUTFILE "field.Summary = summary\n"; 
	print OUTFILE "field.StartDate = updated\n"; 
	print OUTFILE "field.ModDate = updated\n"; 
	print OUTFILE "field.AssignedTo = assignee\n"; 
	print OUTFILE "field.CreatedBy = reporter\n"; 
	print OUTFILE "field.Priority = priority\n"; 

	# mappings
	print OUTFILE "value.JiraIssueType.Bug = 1\n"; 
	print OUTFILE "value.JiraIssueType.Task = 3\n"; 
	print OUTFILE "value.TrackerPriority.1 = 1\n"; 
	print OUTFILE "value.TrackerPriority.2 = 2\n"; 
	print OUTFILE "value.TrackerPriority.3 = 3\n"; 
	print OUTFILE "value.TrackerPriority.4 = 4\n"; 
	print OUTFILE "value.TrackerPriority.5 = 5\n"; 
	print OUTFILE "value.TrackerStatus.Open = 1\n"; 
	print OUTFILE "value.TrackerStatus.Closed = 6\n"; 
	print OUTFILE "value.Queue.BDA = BDA\n"; 
	print OUTFILE "value.Priority.1 = 1\n"; 
	print OUTFILE "value.Priority.3 = 3\n"; 
	print OUTFILE "value.Priority.5 = 5\n"; 
	print OUTFILE "value.Priority.4 = 4\n"; 
	print OUTFILE "value.Status.Closed = 6\n"; 
	print OUTFILE "value.Status.Open = 1\n"; 

	# date info
	print OUTFILE "date.import.format = MM\/dd\/yyyyy hh:mm:ss\n"; 
	print OUTFILE "date.fields = EndDate\n"; 
	print OUTFILE "date.fields = StartDate\n"; 
	print OUTFILE "date.fields = ModDate\n"; 


	close(OUTFILE);
}
sub verifyOptions ()
{
	use Getopt::Long;

	(my $cmd = $0) =~ s/^.*\///;
	my $cwd = `pwd`; chomp $cwd;
	my $usage = "\n$cmd: $cmd -g group name -d dbname [-s dbserver -p dbport -u dbuser -w dbpassword]
	-h      Display this USAGE message and exit.
	-g      Gforge Group Name (required) if exact match is not found other matches will be suggested and program will exit
	-d      Database Name (required)
	-s      Database Server (optional db), enter all optional db or none, none will use local user authentication, not entering all will cause failure
	-p      Database Port (optional db)
	-u      Database User (optional db)
	-w      Database Password (optional db)\n\n";

	my $optrequestGroup,$optdbName,$optdbServer,$optdbPort,$optdbUser,$optdbPass;
	Getopt::Long::Configure ("bundling", "ignore_case_always");
	my $rt=GetOptions(
		"g=s"=>\$optrequestGroup,
		"d=s"=>\$optdbName,
		"s=s"=>\$optdbServer,
		"p=s"=>\$optdbPort,
		"u=s"=>\$optdbUser,
		"w=s"=>\$optdbPass
	);              

	die "$usage" if  ! $rt  ;
	die "$usage" if ! defined $optrequestGroup || ! defined $optdbName;
	die "$usage" if ((defined  $optdbServer || defined $optdbPort || defined $optdbUser || defined $optdbPass) && (! defined  $optdbServer || ! defined $optdbPort || ! defined $optdbUser || ! defined $optdbPass));

	$requestGroup=$optrequestGroup;
	$dbName=$optdbName;
	$dbServer=$optdbServer;
	$dbPort=$optdbPort;
	$dbUser=$optdbUser;
	$dbPass=$optdbPass;
}

