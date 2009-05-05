#!/usr/bin/perl
#######################################################
#  Name:  gforge-obfuscate-users.pl
#  Author: Steven S. Saksa
#  $Id$
#  Description:   This script is used to obfuscate the user and password in a gforge dump. Requires perl is installed.
#  Usage: "./gforge-obfuscate-users.pl"
#
#######################################################
use Data::Dumper;
use DBI;

my $requestGroup=@ARGV[0];
my $groupName="";
my %userHash;

#my $dbh = DBI->connect('dbi:Pg:host=localhost;database=gforgeprod', 'user', 'password') or die "Couldn't connect to database: " . DBI->errstr;
my $dbh = DBI->connect('dbi:Pg:database=gforgeprod') or die "Couldn't connect to database: " . DBI->errstr;

open (DUMP, ">dump.log") || die "Could not write dump.log\n";
open (LOG, ">out.log") || die "Could not write dump.log\n";

#&setUsers();
#exit 0;

print "Reading user list from database\n";
&setUsers;

close DUMP;
close LOG;

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
