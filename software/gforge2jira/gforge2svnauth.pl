#!/usr/bin/env perl
#######################################################
#  Name:  gforge2svnauth.pl
#  Author:  Steven S. Saksa
#  $Id: gforge-tasks2jira.pl 1698 2009-05-27 13:11:24Z saksass $
#  Description:   This script is used to produce a csv file containing all tasks in a given "queue" for a given group.  Most groups
#  			will have multiple queues so there will be multiple output files with the group and queue in the name.
#  Usage: ./gforge2svnauth.pl -d dbname [-s dbserver -p dbport -u dbuser -w dbpassword] -l ldiffile -i inputFile -o outputfile -g groupsvnmapfile
#
#######################################################

use Data::Dumper;
use DBI;
use Cwd 'abs_path';

my $dbServer, $dbPort, $dbUser,$dbPass,$ldapFileName,$inputFile,$outputFile="";
my %gforgeUserGroupHash,%gforgeUserEmailHash,%gforgeUserRealNameHash,%gforgeEmailUserHash, %ldapUserHash, %ldapEmailHash, %ldapRealNameHash,%scmGroupUserHash, %gforgeGroupHash,%groupMapHash,%svnauthGroupHash,%svnDirHash,%gforgeUserLastNameHash;
my @cnList;

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
open (DUMP, ">target/gforge2svnauth.dmp") || die "Could not write dump.log\n";
open (LOG, ">target/gforge2svnauth.log") || die "Could not write dump.log\n";
open (USERFOUND, ">target/gforge2svnauth.found") || die "Could not write found log\n";
open (USERNOTFOUND, ">target/gforge2svnauth.notfound") || die "Could not write notfound log\n";

#&setUsers();
#exit 0;

print "Loading User/Group from Gforge\n";
&loadGforge();
#&genGroupFile();
#exit;
print "Loading Users From LDAP\n";
&loadUserLdap();
print "Loading Gforge to SVN Map file\n";
&loadGroupSvnMap();
print "Comparing Gforge2LDAP\n";
&compareGforge2Ldap();
print "Loading SVNAuthFile\n";
&loadSVNAuthFile();
print "Writing SVNAuthFile\n";
&writeSVNAuthFile();

#print DUMP Dumper(%userHash);
print DUMP "### gforgeUserGroupHash\n";
print DUMP Dumper(%gforgeUserGroupHash);
print DUMP "### gforgeUserEmailHash\n";
print DUMP Dumper(%gforgeUserEmailHash);
print DUMP "### gforgeUserEmailHash\n";
print DUMP Dumper(%gforgeUserRealNameHash);
print DUMP "###  gforgeEmailUserHash\n";
print DUMP Dumper(%gforgeEmailUserHash);
print DUMP "### ldapUserHash\n";
print DUMP Dumper(%ldapUserHash);
print DUMP "### ldapEmailHash\n";
print DUMP Dumper(%ldapEmailHash);
print DUMP "### ldapRealNameHash\n";
print DUMP Dumper(%ldapRealNameHash);
print DUMP "### scmGroupUserHash\n";
print DUMP Dumper(%scmGroupUserHash);
print DUMP "### svnauthGroupHash";
print DUMP Dumper(%svnauthGroupHash);
print DUMP "### gforgeUserLastNameHash";
print DUMP Dumper(%gforgeUserLastNameHash);

close DUMP;
close LOG;
close USERFOUND;
close USERNOTFOUND;

sub loadGforge () 
{
	print LOG "Entering loadGforge\n";	
	my $groupQuery="
		select distinct g.unix_group_name,u.user_name,u.realname, u.email
		from groups g,role r, users u, user_group ug, plugins p, group_plugin gp, role_setting rs
		where   g.group_id=r.group_id
		and	g.group_id=gp.group_id
		and	gp.plugin_id=p.plugin_id
		and     g.group_id=ug.group_id
		and     ug.user_id=u.user_id
		and	r.role_id=rs.role_id
		and	rs.section_name='scm'
		and	rs.value='1'
		and     r.role_name in ('Admin','Developer')
		and	p.plugin_name='scmsvn'
		and 	u.status='A'
		order by 1,2
		";
		# and	g.group_name='NCICB Build and Deployment Automation'

	print LOG "\tUsing Query String - $groupQuery\n";
	my $sth = $dbh->prepare($groupQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	$sth->execute;
	while( my ($groupName, $userName, $userRealName, $userEmail) = $sth->fetchrow_array)
	{
		# replacing spaces with _
		#$groupName=~ s/\s+/_/g;
		#fix 1 user  many groups, 1 group  many users, use array....
		push(@{$gforgeUserGroupHash{lc($userName)}},lc($groupName));
		$gforgeUserEmailHash{lc($userName)}=lc($userEmail);
		$gforgeUserRealNameHash{lc($userName)}=$gforgeUserRealNameHash;
		$gforgeEmailUserHash{lc($userEmail)}=lc($userName);
		$gforgeGroupHash{lc($groupName)}++;

		if ($userRealName =~/(\S+)$/)
		{
			my $lastName=$1;
			$lastName =~ s/\W+//;
			$gforgeUserLastNameHash{lc($userName)}=lc($lastName);
		}
	}
	$sth->finish();
}

sub genGroupFile()
{
	print LOG "Entering genGroupFile\n";
	open(GFILE,"> target/groups.map") || die "Can't create target/groups.map\n";
	foreach my $group (sort keys %gforgeGroupHash)
	{
		print GFILE "$group=\n";
	}
}

sub loadUserLdap ()
{
	print LOG "Entering loadUserLdap\n";
	my $oldifs = $/;
	$/="\n\n";
	#open(LDAP,"ldapsearch  -h ncids4a.nci.nih.gov -x -D '$ldapUser' -w '$ldapPass' -L -b '$ldapBase' '$ldapSearch'") || die "Could not open file $ldapFileName\n";
	open(LDAP,"$ldapFileName") || die "Could not open file $ldapFileName\n";
	while (my $rec = <LDAP>) 
	{       
		my $cn,$uid,$email,$sn,$givenName;
		if ($rec =~ /.*uid:\s+(.*)?\n/)
		{               
			$uid=lc($1);
		}               
		if ($rec =~ /.*mail:\s+(.*)?\n/)
		{               
			$email=lc($1);
		}               
		if ($rec =~ /.*sn:\s+(.*)?\n/)
		{               
			$sn=$1;
		}               
		if ($rec =~ /.*giveName:\s+(.*)?\n/)
		{               
			$giveName=$1;
		}               
		if ($rec =~ /.*cn:\s+(.*)?/)
		{               
			$cn=lc($1);
		}               

		my $realname="${givenName} ${sn}";

		push(@cnList,$cn);
		# Use uid if defnied otherwise use $cn
		if ($uid =~/\S+/)
		{
			$ldapEmailHash{$email}=$uid;
			$ldapUserHash{$uid}++;
			$ldapRealNameHash{$realname}=$id;
		}
		else
		{
			$ldapEmailHash{$email}=$cn;
			$ldapUserHash{$cn}++;
			$ldapRealNameHash{$realname}=$cn;
		}

	}       
	$/=$oldifs;
	close(LDAP);
}
sub loadGroupSvnMap()
{
	print LOG "Entering loadGroupSvnMap\n";
	my @groupMapList;
	open(MFILE,"$groupSvnMapFile") || die "Can't open $groupSvnMapFile\n";
	while(my $line = <MFILE>)
	{
		if($line =~ /(.*)=(.*)/)
		{
			my $gforgeGroup=$1;
			my $svnPath=$2;
			if ($svnGroup =~ /\S+/)
			{
				$groupMapHash{$gforgeGroup}=$svnPath;
				push(@groupMapList, $gforgeGroup);
			}
		}
	}
	print LOG "\tGroups not in map file\n";
	foreach my $group (sort keys %gforgeGroupMap)
	{
		print LOG "\t$group not in map file\n" if ! grep /$group/,@groupMapList;
	}
}
sub compareGforge2Ldap ()
{
	print LOG "Entering compareGforge2Ldap\n";
	foreach my $gforgeUserName (sort keys %gforgeUserGroupHash)
	{
		#if usernanme found in ldapUserHash
		#   use gforgeusername
		#elseif email in ldapEmailHash
		#   user ldap username
		#else
		#   log not found message
		if (defined $ldapUserHash{$gforgeUserName})
		{
			foreach my $gforgeGroup (@{$gforgeUserGroupHash{$gforgeUserName}})
			{
				print USERFOUND "Found $gforgeUserName with ldap email $gforgeUserName\n";
				push(@{$scmGroupUserHash{$gforgeGroup}},$gforgeUserName);
			}
		}
		elsif (defined $ldapEmailHash{$gforgeUserEmailHash{$gforgeUserName}})
		{
			foreach my $gforgeGroup (@{$gforgeUserGroupHash{$gforgeUserName}})
			{
				print USERFOUND "Found $gforgeUserName with ldap email $gforgeUserEmailHash{$gforgeUserName}\n";
				push(@{$scmGroupUserHash{$gforgeGroup}},$ldapEmailHash{$gforgeUserEmailHash{$gforgeUserName}});
			}
		}
		else
		{
			# search for last name in cnList, you will get more matches
			my $possibleMatches=join(",", grep(/$gforgeUserLastNameHash{$gforgeUserName}/, @cnList));
			my $as=length($possibleMatches);
			print USERNOTFOUND "NOT FOUND $gforgeUserName LDAP by name or email $gforgeUserEmailHash{$gforgeUserName}, possible matches for last name of $gforgeUserLastNameHash{$gforgeUserName} - [${possibleMatches}]\n" if $as > 0 && $gforgeUserLastNameHash{$gforgeUserName};
			print USERNOTFOUND "NOT FOUND $gforgeUserName LDAP by name or email $gforgeUserEmailHash{$gforgeUserName}\n" if $as == 0;
		}
	}
}
sub loadSVNAuthFile()
{
	print LOG "Entering loadSVNAuthFile\n";
	open(IFILE,"$inputFile") || die "Could not open $inputFile\n";
	my $section="";
	print LOG "\tReading svnauthfile $inputFile.\n";
	my $i=0;
	while (my $line = <IFILE>)
	{
		chomp($line);
		$i++;
		if ($line =~/\[(.*)\]/)
		{
			$section=$1;
			print LOG "\tProcessing section $section\n";
		}
		elsif ($line =~/^(.*)=(.*)/ && $section eq "groups")
		{
			my $groupName=$1;
			my @groupMemberList=split(/,/,$2);
			
			push(@{$svnauthGroupHash{$groupName}},@groupMemberList);
			print LOG "\tReading group $groupName  $2\n";
		}
		elsif ($line =~/^(.*)=(.*)/ && $section ne "groups")
		{
			my $user=$1;
			my $rights=$2;

			$svnDirHash{$section}{$user}=$rights;
			print LOG "\tReading rights $section $user\n";
		}
		else
		{
			print LOG "\tFailed to process line - '$line'\n";
		}
	}
	print "done with file \n";
	close(IFILE);
}

sub writeSVNAuthFile()
{
	print LOG "Entering writeSVNAuthFile\n";
	my @svnDirAddedList;
	my @svnAuthOutList;
	my %writeGroupHash,%writeUserHash;
	open(OFILE,">$outputFile") || die "Could not open $outputFile\n";
	# Build a list of groups that includes list of groups from gforge/map
	foreach my $group (sort keys %svnauthGroupHash)
	{
		$writeGroupHash{$group}++;
	}
	foreach my $group (sort keys %scmGroupUserHash)
	{
		$writeGroupHash{$group}++;
	}

	print OFILE "[groups]\n";

	foreach my $group (sort keys %writeGroupHash)
	{
		#print "\t$group\n";
		my %writeUserHash;
		print LOG "\tgroup: $group ($writeGroupHash{$group})\n";
		print OFILE "$group = ";
		foreach my $user (@{$scmGroupUserHash{$group}})
		{
			$writeUserHash{$user}++
		}
		foreach my $user (@{$svnauthGroupHash{$group}})
		{
			$writeUserHash{$user}++
		}
		
		my $userList="";
		foreach my $user (sort keys %writeUserHash)
		{
			#print "\t\t$user\n";
			print LOG "\t\tuser: $user ($writeUserHash{$user})\n";
			$userList.=" $user,";
		}
		$userList=~s/,$//;
		print OFILE "$userList\n";
	}
	print OFILE "\n";
	#my @pathList=keys %svnDirHash;

	# Processing dirs
	my @outList;
	foreach my $group (sort keys %writeGroupHash)
	{
		# $svnDirHash{$section}{$user}=$rights;
		my $groupSearch;
		my $outRec="";
		defined $groupMapHash{$group} ? $groupSearch=$groupMapHash{$group} : $groupSearch=$group;
		my @svnauthMatchs = grep /$groupSearch/, keys %svnDirHash;
		my $svnauthMatchCount=@svnauthMatchs;
		print LOG "\t$groupSearch matchs @svnauthMatchs\n";
		
		if ($svnauthMatchCount > 0)
		{
			foreach my $svnDir (@svnauthMatchs)
			{
				$outRec.="[${svnDir}] match\n";
				push(@svnDirAddedList,$svnDir);
				my $grpMatch,$starMatch="";
				foreach my $user (sort keys %{$svnDirHash{$svnDir}})
				{
					my $perms=$svnDirHash{$svnDir}{$user};
					if ("\@$group" eq "$user")
					{
						$grpMatch="true";
					}
					if ($user eq "\*.*")
					{
						$starMatch="true";
					}
					$outRec.= "$user = $perms\n";
				}
				$outRec.= "\* = r\n" if $starMatch !~ /\S+/;
				$outRec.= "\@${group} = rw\n" if $grpMatch !~ /\S+/;
				$outRec.= "\n";
			}
			push(@outList,$outRec);
		}
		else
		{
			my $addPath="${group}:/";
			push(@svnDirAddedList,$addPath);
			$outRec.= "[${addPath}]\n";
			$outRec.= "* = r\n";
			$outRec.= "\@${group} = rw\n\n";
			push(@outList,$outRec);
		}

	}
	# $svnDirHash{$section}{$user}=$rights;
	foreach my $svnDir (sort keys %svnDirHash)
	{
		my $outRec="";
		next if grep /$svnDir/,@svnDirAddedList;
		$outRec.="[${svnDir}] unmatch\n";
		foreach my $user (sort keys %{$svnDirHash{$svnDir}})
		{
			my $perms=$svnDirHash{$svnDir}{$user};
			$outRec.= "$user = $perms\n";
		}
		$outRec.="\n";
		push(@outList,$outRec);
		
	}

	foreach my $rec (sort @outList)
	{
		print OFILE "$rec";
	}

	close(OFILE);
}

sub verifyOptions ()
{
	print LOG "Entering verifyOptions\n";
	use Getopt::Long;

	(my $cmd = $0) =~ s/^.*\///;
	my $cwd = `pwd`; chomp $cwd;
	my $usage = "\n$cmd: $cmd -d dbname [-s dbserver -p dbport -u dbuser -w dbpassword] -l ldiffile -i inputFile -o outputfile -g groupsvnmapfile
	-h      Display this USAGE message and exit.
	-l	LDIF file (required)
	-i	Input file svnauth file (required)
	-o	Output file svnauth file (required) [cannot be same as input file]
	-g	Group to SVN Path Map File (required)
	-d      Database Name (required)
	-s      Database Server (optional db), enter all optional db or none, none will use local user authentication, not entering all will cause failure
	-p      Database Port (optional db)
	-u      Database User (optional db)
	-w      Database Password (optional db)
       	\n";

	my $optrequestGroup,$optdbName,$optdbServer,$optdbPort,$optdbUser,$optdbPass,$ldapFileName,$optinputFile,$optoutputfile,$optgroupSvnMapFile;
	Getopt::Long::Configure ("bundling", "ignore_case_always");
	my $rt=GetOptions(
		"d=s"=>\$optdbName,
		"s=s"=>\$optdbServer,
		"p=s"=>\$optdbPort,
		"u=s"=>\$optdbUser,
		"w=s"=>\$optdbPass,
		"l=s"=>\$optldapFileName,
		"i=s"=>\$optinputFile,
		"o=s"=>\$optoutputFile,
		"g=s"=>\$optgroupSvnMapFile
	);              

	die "$usage" if  ! $rt  ;
	die "$usage" if ! defined $optdbName || ! defined $optldapFileName || ! defined $optinputFile || ! defined $optoutputFile || ! defined $optgroupSvnMapFile;
	die "$usage" if ((defined  $optdbServer || defined $optdbPort || defined $optdbUser || defined $optdbPass) && (! defined  $optdbServer || ! defined $optdbPort || ! defined $optdbUser || ! defined $optdbPass));
	die "$optldapFileName does not exist\n" if ! -f $optldapFileName;
	die "$optinputFile does not exist\n" if ! -f $optinputFile;
	die "Input and output files cannot be same" if $optinputFile eq $optoutputFile;
	$dbName=$optdbName;
	$dbServer=$optdbServer;
	$dbPort=$optdbPort;
	$dbUser=$optdbUser;
	$dbPass=$optdbPass;
	$ldapFileName=$optldapFileName;
	$inputFile=$optinputFile;
	$outputFile=$optoutputFile;
	$groupSvnMapFile=$optgroupSvnMapFile
}
