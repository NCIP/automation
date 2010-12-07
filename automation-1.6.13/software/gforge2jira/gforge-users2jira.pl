#!/usr/bin/perl
#######################################################
#  Name:  gforge-users2jira.pl
#  Author: Steven S. Saksa
#  $Id: gforge-obfuscate-users.pl 1632 2009-05-05 12:58:50Z saksass $
#  Description:   This script is used to generate jelly script for creating all users in gforge in jira, uses random password. 
#  			Passwords for users in Jira will come from ldap, non ldap users will have unknown passwords so can't login.
#  Usage: "./gforge-users2jira.pl"
#  Note: May need to install String::Random "yum install perl-String-Random.noarch"
#
#######################################################
use Data::Dumper;
use DBI;
use String::Random;


my $requestGroup=@ARGV[0];
my $groupName="";
my %userHash;

#my $dbh = DBI->connect('dbi:Pg:host=localhost;database=gforgeprod', 'user', 'password') or die "Couldn't connect to database: " . DBI->errstr;
my $dbh = DBI->connect('dbi:Pg:database=gforgeprod') or die "Couldn't connect to database: " . DBI->errstr;

mkdir ("target");
open (DUMP, ">target/dump.log") || die "Could not write dump.log\n";
open (LOG, ">target/out.log") || die "Could not write dump.log\n";

#&setUsers();
#exit 0;

print "Reading user list from database\n";
&mkUserJelly;

close DUMP;
close LOG;

sub mkUserJelly () 
{
	my @userList;
	my $userQuery="select user_name,email,realname from users";
	print LOG "Using Query String - $userQuery\n";
	my $sth = $dbh->prepare($userQuery) or die "Couldn't prepare statement: " . $dbh->errstr;
	
	open (OUTFILE, ">target/gforge-users.xml") || die "could not open user jelly file";
	print OUTFILE "<JiraJelly xmlns:jira=\"jelly:com.atlassian.jira.jelly.JiraTagLib\">\n";

	$sth->execute;
	while( my ($userName, $email, $realName) = $sth->fetchrow_array)
	{
		push @userList, $userId;
		my $rand=new String::Random;
		my $passwd=$rand->randpattern("ssssssssssssssssssss");
		print OUTFILE "<jira:CreateUser username=\"$userName\" password=\"$passwd\" confirm=\"$passwd\" fullname=\"$realName\" email=\"$email\"\/>\n";
	}
	$sth->finish();
	print OUTFILE "<\/JiraJelly>\n";
	close (OUTFILE);
}
