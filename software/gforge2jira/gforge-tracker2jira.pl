#!/usr/bin/perl
use Data::Dumper;
use DBI;

my $requestGroup=@ARGV[0];
my $groupName="";
my %userHash;
my %artifactHash;
my %elementIDHash;
my @artifactIDList;
my %queueFieldList;

#my $dbh = DBI->connect('dbi:Pg:host=localhost;database=gforgeprod', 'user', 'password') or die "Couldn't connect to database: " . DBI->errstr;
my $dbh = DBI->connect('dbi:Pg:database=gforgeprod') or die "Couldn't connect to database: " . DBI->errstr;

open (DUMP, ">dump.log") || die "Could not write dump.log\n";
open (LOG, ">out.log") || die "Could not write dump.log\n";

#&setUsers();
#exit 0;

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
print "Generate CSVs\n";
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
		$artifactHash{$trackerId}{"Queue"}=$trackerQue;
		$artifactHash{$trackerId}{"TrackerStatus"}=$trackerStatus;
		$artifactHash{$trackerId}{"Summary"}=$trackerSummary;
		$artifactHash{$trackerId}{"Details"}=$trackerDetails;
		$artifactHash{$trackerId}{"Priority"}=$trackerPriority;
		$artifactHash{$trackerId}{"SubmittedBy"}=$userHash{$trackerSubmitId};
		$artifactHash{$trackerId}{"AssignedTo"}=$userHash{$trackerAssignedId};
		$artifactHash{$trackerId}{"OpenEpoch"}=$trackerOpenEpoch;
		$artifactHash{$trackerId}{"ClosedEpoch"}=$trackerClosedEpoch;
		$artifactHash{$trackerId}{"LastEpoch"}=$trackerLastEpoch;
		$artifactHash{$trackerId}{"TicketHistory"}="";
		$artifactHash{$trackerId}{"TicketFollowup"}="";
		push @artifactIDList, $trackerId;

		$queueFieldList{$trackerQue}{"TrackerStatus"}++;
		$queueFieldList{$trackerQue}{"Summary"}++;
		$queueFieldList{$trackerQue}{"Details"}++;
		$queueFieldList{$trackerQue}{"Priority"}++;
		$queueFieldList{$trackerQue}{"SubmittedBy"}++;
		$queueFieldList{$trackerQue}{"AssignedTo"}++;
		$queueFieldList{$trackerQue}{"OpenEpoch"}++;
		$queueFieldList{$trackerQue}{"ClosedEpoch"}++;
		$queueFieldList{$trackerQue}{"LastEpoch"}++;
		$queueFieldList{$trackerQue}{"TicketHistory"}++;
		$queueFieldList{$trackerQue}{"TicketFollowup"}++;
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
			}
			else
			{
				$artifactHash{$id}{$fieldName}=$elementIDHash{$fieldData};
			}
		}
		else
		{
			$artifactHash{$id}{$fieldName}=$fieldData;
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
		my $modDate=sprintf("%02d/%02d/%02d %02d:%02d:%02d",$mon+1,$mday,$year-100,$hour,$min,$sec);

		my $user=$userHash{$modBy};
		$ticketHistory= "${user} - ${modDate} - ${fieldName} old value '${oldValue}'\n";
		$artifactHash{$id}{"TicketHistory"}.=$ticketHistory;
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
		my $addDate=sprintf("%02d/%02d/%02d %02d:%02d:%02d",$mon+1,$mday,$year-100,$hour,$min,$sec);

		my $user=$userHash{$submittedby};
		$ticketMessages= "${user} - ${email} - ${addDate}\n${body}\n\n";
		$artifactHash{$id}{"TicketFollowup"}.=$ticketMessages;
	}
	$sth->finish();
}
sub generateCSVs()
{
	my $fieldValue;
	foreach my $queue (sort keys %queueFieldList)
	{
		my $fname="exp-${groupName}-${queue}.csv";
		$fname=~s/\s+//g;
		open (OUTFILE, ">$fname") ||die "Could not create $fname\n";
		print OUTFILE "IssueID, ";
		my @fieldList=();
		foreach my $fieldName (sort keys %{$queueFieldList{$queue}})
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
