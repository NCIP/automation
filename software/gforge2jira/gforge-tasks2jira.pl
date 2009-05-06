#!/usr/bin/perl
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

my $requestGroup=@ARGV[0];
my $groupName="";
my %userHash;
my %artifactHash;
my @artifactIDList;
my %queueHash;

mkdir("target");
#my $dbh = DBI->connect('dbi:Pg:host=192.168.1.116;database=gforgeprod', 'user', 'password') or die "Couldn't connect to database: " . DBI->errstr;
my $dbh = DBI->connect('dbi:Pg:database=gforgeprod') or die "Couldn't connect to database: " . DBI->errstr;

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
print "Generating CSVs\n";
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
		push @artifactIDList, $taskID;
		$queueHash{$taskQueue}++;
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
		my $postDate=sprintf("%02d/%02d/%02d %02d:%02d:%02d",$mon+1,$mday,$year-100,$hour,$min,$sec);

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
		open (OUTFILE, ">$fname") ||die "Could not create $fname\n";
		print OUTFILE "GforgeID, ";
		my @fieldList=();
		my $firstId=@artifactIDList[1];
		foreach my $fieldName (sort keys %{$artifactHash{$firstId}})
		{
			print OUTFILE "$fieldName, ";
			push (@fieldList,$fieldName);
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
					my $tmpDate=sprintf("%02d/%02d/%02d %02d:%02d:%02d",$mon+1,$mday,$year-100,$hour,$min,$sec);
					$fieldValue=$tmpDate;
				}
				if ($fieldValue =~ /\"|\n|,/)
				{
					$fieldValue=~s/\"/\\\"/g;
					#$fieldValue=~s/\'/\\\'/g;
					$fieldValue=~s/\,//g;
					$fieldValue=~s/\n|\r/\\n/g;
					$fieldValue=~s/^/\"/;
					$fieldValue=~s/$/\"/;
				}
				print OUTFILE "$fieldValue, ";
			}
			print OUTFILE "\n";
		}
		close(OUTFILE);
	}

}
