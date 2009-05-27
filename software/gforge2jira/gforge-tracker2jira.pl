#!/usr/bin/env perl
#######################################################
#  Name:  gforge-tracerk2jira.pl
#  Author:  Steven S. Saksa
#  $Id$
#  Description:   This script is used to produce a csv file containing all trackers in a given "queue" for a given group.  Most groups
#                       will have multiple queues so there will be multiple output files with the group and queue in the name.
#  Usage: ./gforge-tracker2jira.pl "group name" # " are recommended when group names contain spaces
#
#######################################################

use Data::Dumper;
use DBI;
use Switch;
use Cwd 'abs_path';


my $requestGroup, $dbServer, $dbPort, $dbUser,$dbPass="";
my $groupName="";
my %userHash;
my %artifactHash;
my %elementIDHash;
my @artifactIDList;
my %queueFieldList;

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
open (DUMP, ">target/gforge-tracker2jira.dmp") || die "Could not write dump.log\n";
open (LOG, ">target/gforge-tracker2jira.log") || die "Could not write dump.log\n";

print "Verifying Group\n";
&verifyGroup;
print "Reading user list from database\n";
&getUsers;
print "Reading Custom Field Elements\n";
&getCustomFieldsElements();
print "Reading artifacts for ${groupName}\n";
&getArtifacts;
print "Adding custom fields with null values\n";
&getCustomFields();
print "Adding custom field values\n";
&getCustomData();
print "Processing Followups\n";
&getMessages();
print "Processing History\n";
&getHistory();
print "Generate CSVs and CNFs\n";
&generateCSVs();

#print DUMP Dumper(%userHash);
print DUMP "### Artifact Hash\n";
print DUMP Dumper(%artifactHash);
print DUMP "### Element Hash\n";
print DUMP Dumper(%elementIDHash);
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
	my $artifactQuery="select agl.name, s.status_name, a.artifact_id, a.summary, a.details, a.priority, a.submitted_by, a.assigned_to, a.open_date, a.close_date, a. last_modified_date
	from artifact a, artifact_group_list agl, groups g, artifact_status s
	where	g.group_id=agl.group_id
	and     a.group_artifact_id=agl.group_artifact_id
	and     a.status_id=s.id
	and 	g.group_name='${groupName}'
	order by  1,2,3";
	print LOG "Using Query String - $artifactQuery\n";
	my $sth = $dbh->prepare($artifactQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	$sth->execute;
	while( my ($trackerQue,$trackerStatus,$trackerId,$trackerSummary,$trackerDetails, $trackerPriority, $trackerSubmitId, $trackerAssignedId, $trackerOpenEpoch, $trackerClosedEpoch, $trackerLastEpoch) = $sth->fetchrow_array)
	{
		$artifactHash{$trackerId}{"TrackerQueue"}=$trackerQue;
		$artifactHash{$trackerId}{"TrackerStatus"}=$trackerStatus;
		$artifactHash{$trackerId}{"TrackerSummary"}=$trackerSummary;
		$artifactHash{$trackerId}{"TrackerDetails"}=$trackerDetails;
		$artifactHash{$trackerId}{"TrackerPriority"}=$trackerPriority;
		$artifactHash{$trackerId}{"TrackerSubmittedBy"}=$userHash{$trackerSubmitId};
		$artifactHash{$trackerId}{"TrackerAssignedTo"}=$userHash{$trackerAssignedId};
		$artifactHash{$trackerId}{"TrackerOpenEpoch"}=$trackerOpenEpoch;
		$artifactHash{$trackerId}{"TrackerClosedEpoch"}=$trackerClosedEpoch;
		$artifactHash{$trackerId}{"TrackerLastEpoch"}=$trackerLastEpoch;
		$artifactHash{$trackerId}{"TrackerHistory"}="##############\nGforge Tracker Field History\n##############\n\n";
		$artifactHash{$trackerId}{"TrackerFollowup"}="##############\nGforge Tracker Followups\n##############\n\n";
		$artifactHash{$trackerId}{"TrackerCustomFields"}="##############\nGforge Tracker Custom Fields\n##############\n\n";

		switch ($trackerQueue)
		{
			case /bug/i {$artifactHash{$trackerId}{"JiraIssueType"}="Bug"}
			case /feature/i {$artifactHash{$trackerId}{"JiraIssueType"}="New Feature"}
			else {$artifactHash{$trackerId}{"JiraIssueType"}="Bug"}
		}
			

		push @artifactIDList, $trackerId;

		$queueFieldList{$trackerQue}{"TrackerStatus"}++;
		$queueFieldList{$trackerQue}{"TrackerSummary"}++;
		$queueFieldList{$trackerQue}{"TrackerDetails"}++;
		$queueFieldList{$trackerQue}{"TrackerPriority"}++;
		$queueFieldList{$trackerQue}{"TrackerSubmittedBy"}++;
		$queueFieldList{$trackerQue}{"TrackerAssignedTo"}++;
		$queueFieldList{$trackerQue}{"TrackerOpenEpoch"}++;
		$queueFieldList{$trackerQue}{"TrackerClosedEpoch"}++;
		$queueFieldList{$trackerQue}{"TrackerLastEpoch"}++;
		$queueFieldList{$trackerQue}{"TrackerHistory"}++;
		$queueFieldList{$trackerQue}{"TrackerFollowup"}++;
		$queueFieldList{$trackerQue}{"JiraIssueType"}++;
		$queueFieldList{$trackerQue}{"TrackerCustomFields"}++;

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
sub getCustomFields()
{
	my $cfQuery="select g.group_name,agl.name, aefl.field_name
	from artifact_group_list agl, groups g, artifact_extra_field_list aefl
	where   g.group_id=agl.group_id
	and     agl.group_artifact_id=aefl.group_artifact_id
	and     g.status='A'
	and     g.group_name='${groupName}'
	order by 1,2,3";

	print LOG "Using Query String - $cfQuery\n";
	my $sth = $dbh->prepare($cfQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	$sth->execute;
	my @flist;
	print LOG "Custom fields\n";
	while( my ($group,$queueName,$fieldName) = $sth->fetchrow_array)
	{
		print LOG "\t$group,$queueName,$fieldName\n";
		foreach my $id (@artifactIDList)
		{
			$queueFieldList{$queueName}{$fieldName}++;
			if ($artifactHash{$id}{"Queue"} eq $queueName)
			{
				$artifactHash{$id}{$fieldName}="";
			}
		}
	}
	$sth->finish();
}
sub getCustomData()
{
	my $cfQuery="select a.artifact_id, aefl.field_name, aefd.field_data, aefd.extra_field_id,aefl.field_type
	from artifact a, artifact_extra_field_data aefd, artifact_extra_field_list aefl, artifact_group_list agl, groups g
	where   a.artifact_id=aefd.artifact_id
	and	aefd.extra_field_id=aefl.extra_field_id
	and	g.group_id=agl.group_id
	and     agl.group_artifact_id=aefl.group_artifact_id
	and	g.group_name=?
	order by 1,2,3";

	print LOG "Using Query String - $cfQuery\n";
	my $sth = $dbh->prepare($cfQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	$sth->execute($groupName);
	print LOG "Custom fields\n";
	while( my ($id,$fieldName,$fieldData,$fieldID,$fieldType) = $sth->fetchrow_array)
	{
		print LOG "\t$group,$queueName,$fieldName\n";
		if ($fieldType == 1 || $fieldType == 2 || $fieldType == 3 || $fieldType == 5)
		{
			if ($fieldData == 100)
			{
				$artifactHash{$id}{$fieldName}="None";
				$artifactHash{$id}{"TrackerCustomFields"}.="#####\n${fieldName}\t:  None\n######\n\n";
			}
			else
			{
				$artifactHash{$id}{$fieldName}=$elementIDHash{$fieldData};
				$artifactHash{$id}{"TrackerCustomFields"}.="#####\n${fieldName}\t:  $elementIDHash{$fieldData}\n######\n\n";
			}
		}
		else
		{
			$artifactHash{$id}{$fieldName}=$fieldData;
			$artifactHash{$id}{"TrackerCustomFields"}.="#####\n${fieldName}\t:  $fieldData\n######\n\n";
		}
	}
	$sth->finish();
}
sub getCustomFieldsElements()
{
	my $cfQuery="select  aefe.extra_field_id,aefe.element_id,aefe.element_name
	from artifact_group_list agl, groups g, artifact_extra_field_list aefl,artifact_extra_field_elements aefe
	where   g.group_id=agl.group_id
	and     agl.group_artifact_id=aefl.group_artifact_id
	and	aefl.extra_field_id=aefe.extra_field_id
	and     g.status='A'
	and     g.group_name='${groupName}'
	order by 1,2,3";

	print LOG "Using Query String - $cfQuery\n";
	my $sth = $dbh->prepare($cfQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	$sth->execute;
	while( my ($extraFieldID,$elementID,$elementName) = $sth->fetchrow_array)
	{
		$elementIDHash{$elementID}=$elementName;
	}
	$sth->finish();
}
sub getHistory()
{
	my $historyQuery="select  a.artifact_id, ah.field_name, ah.old_value, ah.mod_by, ah.entrydate
	from artifact a, artifact_group_list agl, groups g,artifact_history ah
	where	g.group_id=agl.group_id
	and     a.group_artifact_id=agl.group_artifact_id
	and	a.artifact_id=ah.artifact_id
	and 	g.group_name='${groupName}'
	order by  1,4";
	print LOG "Using Query String - $historyQuery\n";
	my $sth = $dbh->prepare($historyQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	my $messageHistory="" ;
	$sth->execute;
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
		$ticketHistory= "${user} - ${modDate} - ${fieldName} old value '${oldValue}'\n";
		$artifactHash{$id}{"TrackerHistory"}.=$ticketHistory;
	}
	$sth->finish();
}
sub getMessages()
{
	my $messageQuery="select  a.artifact_id, am.submitted_by,  am.from_email, am.adddate, am.body
	from artifact a, artifact_group_list agl, groups g,artifact_message am
	where	g.group_id=agl.group_id
	and     a.group_artifact_id=agl.group_artifact_id
	and	a.artifact_id=am.artifact_id
	and 	g.group_name='${groupName}'
	order by  1,4";
	print LOG "Using Query String - $messageQuery\n";
	my $sth = $dbh->prepare($messageQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	my $messageHistory="" ;
	$sth->execute;
	while( my ($id, $submittedby, $email, $addepoch, $body) = $sth->fetchrow_array)
	{
		my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= gmtime($addepoch);
		my $addDate=sprintf("%02d/%02d/%04d %02d:%02d:%02d",$mon+1,$mday,$year+1900,$hour,$min,$sec);

		my $user=$userHash{$submittedby};
		$ticketMessages= "###############\n${user} - ${email} - ${addDate}\n${body}\n###############\n";
		$artifactHash{$id}{"TrackerFollowup"}.=$ticketMessages;
	}
	$sth->finish();
}
sub generateCSVs()
{
	my $fieldValue;
	foreach my $queue (sort keys %queueFieldList)
	{
		my $fname="target/tracker-exp-${groupName}-${queue}.csv";
		$fname=~s/\s+//g;
		$absFname=abs_path($fname);
		print "\t${absFname}\n";
		&createJiraCnf($queue);
		open (OUTFILE, ">$fname") ||die "Could not create $fname\n";
		print OUTFILE "GforgeID,";
		my @fieldList=();
		foreach my $fieldName (sort keys %{$queueFieldList{$queue}})
		{
			push (@fieldList,$fieldName);
			$fieldName=~ s/epoch/Date/i;
			print OUTFILE "$fieldName, ";
		}
		print OUTFILE "\n";
		foreach my $id (@artifactIDList)
		{
			next if($artifactHash{$id}{"TrackerQueue"} ne $queue);
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
	my $fname="target/tracker-exp-${groupName}-${queue}.cnf";
	$fname=~s/\s+//g;
	$absFname=abs_path($fname);
	print "\t${absFname}\n";
	open (OUTFILE, ">$fname") ||die "Could not create $fname\n";

	my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= gmtime($fieldValue);
	my $tmpDate=sprintf("%02d/%02d/%04d %02d:%02d:%02d",$mon+1,$mday,$year+1900,$hour,$min,$sec);

	# project level details
	print OUTFILE "# Written by gforge-tracker2jira.pl\n";
	print OUTFILE "# $(tmpDate)\n";
	print OUTFILE "delimiter = \\,\n"; 
	print OUTFILE "existingprojectkey = SSAKSA\n";
	print OUTFILE "importsingleproject = false\n";
	print OUTFILE "importexistingproject = true\n";
	print OUTFILE "mapfromcsv = false\n";
	print OUTFILE "user.email.suffix = \@mail.nih.gov\n";
	
	# fields
	print OUTFILE "field.GforgeID = customfield_10001\n"; 
	print OUTFILE "field.TrackerStatus = status\n"; 
	print OUTFILE "field.TrackerHistory = comment\n"; 
	print OUTFILE "field.TrackerPriority = priority\n"; 
	print OUTFILE "field.TrackerSummary = summary\n"; 
	print OUTFILE "field.TrackerDetails = description\n"; 
	print OUTFILE "field.TrackerLastDate = updated\n"; 
	print OUTFILE "field.TrackerSubmittedBy = reporter\n"; 
	print OUTFILE "field.TrackerFollowup = comment\n"; 
	print OUTFILE "field.TrackerOpenDate = created\n"; 
	print OUTFILE "field.TrackerAssignedTo = assignee\n"; 
	print OUTFILE "field.JiraIssueType = type\n"; 
	print OUTFILE "field.TrackerCustomFields = comment\n"; 

	# mappings
	print OUTFILE "value.JiraIssueType.Bug = 1\n"; 
	print OUTFILE "value.TrackerPriority.1 = 1\n"; 
	print OUTFILE "value.TrackerPriority.2 = 2\n"; 
	print OUTFILE "value.TrackerPriority.3 = 3\n"; 
	print OUTFILE "value.TrackerPriority.4 = 4\n"; 
	print OUTFILE "value.TrackerPriority.5 = 5\n"; 
	print OUTFILE "value.TrackerStatus.Open = 1\n"; 
	print OUTFILE "value.TrackerStatus.Closed = 6\n"; 

	# date info
	print OUTFILE "date.import.format = MM\/dd\/yyyyy hh:mm:ss\n";
	print OUTFILE "date.fields = TrackerLastDate\n";
	print OUTFILE "date.fields = TrackerOpenDate\n";
	
	close(OUTFILE);
 }

sub verifyOptions ()
{
	use Getopt::Long;

	(my $cmd = $0) =~ s/^.*\///;
	my $cwd = `pwd`; chomp $cwd;
	my $usage = "\n$cmd: $cmd -g group name -d dbname [-s dbserver -p dbport -u dbuser -w dbpassword]
	-h	Display this USAGE message and exit.
	-g	Gforge Group Name (required) if exact match is not found other matches will be suggested and program will exit
	-d	Database Name (required)
	-s	Database Server (optional db), enter all optional db or none, none will use local user authentication, not entering all will cause failure
	-p	Database Port (optional db)
	-u	Database User (optional db)
	-w	Database Password (optional db)\n\n";

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

	#print "CMD line options\n";
	#print "\trequestGroup - $optrequestGroup\n";
	#print "\tdbName - $optdbName\n";
	#print "\tdbServer - $optdbServer\n";
	#print "\tdbPort - $optdbPort\n";
	#print "\tdbUser - $optdbUser\n";
	#print "\tdbPass - $optdbPass\n";

	$requestGroup=$optrequestGroup;
	$dbName=$optdbName;
	$dbServer=$optdbServer;
	$dbPort=$optdbPort;
	$dbUser=$optdbUser;
	$dbPass=$optdbPass;
}
