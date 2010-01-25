#!/usr/bin/env perl
#######################################################
#  Name:  
#  Author:  Steven S. Saksa
#  $Id: $
#  Description:   
#  Usage: 
#
#######################################################

use Data::Dumper;
use Cwd 'abs_path';

my $servers,$numOps,$fileSize;
my @sendFileTimeArray,@getFileTimeArray;
print "Verifying command line options\n";
my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= localtime(time());
my $runDate=sprintf("%02d/%02d/%02d %02d:%02d",$mon+1,$mday,$year-100,$hour,$min);

&verifyOptions();

my $out=qx(dd if=/dev/zero of=tmpfile bs=1M count=${fileSize} 2>&1);
&testDisk();

sub testDisk ()
{
	my @serverList = split (",",$servers);
	foreach  my $server(@serverList)
	{
		@sendFileTimeArray=();
		@getFileTimeArray=();
	for ($count = 1 ; $count <= $numOps; $count++)
	{
	print "$server\t$count\tsend\n";
	my $begTime=time();
	my $out=qx(scp tmpfile $server:);
	my $exitcode= $? >> 8;

	my $endTime=time();
	my $totTime=($endTime - $begTime);
	
	#print "Exit Code- $exitcode\n";
	#print "Results- $out\n";
	#print "Took - $totTimes sec ($endTime $begTime)\n";

	push(@sendFileTimeArray,$totTime);

	print "$server\t$count\tget\n";
	my $begTime=time();
	my $out=qx(scp $server:tmpfile .);
	my $exitcode= $? >> 8;

	my $endTime=time();
	my $totTime=($endTime - $begTime);
	push(@getFileTimeArray,$totTime);
	}
	my $sendTimings=join(",", @sendFileTimeArray);
	print "$server,send,$sendTimings\n";
	my $getTimings=join(",", @getFileTimeArray);
	print "$server,get,$getTimings\n";
	}
}
sub verifyOptions ()
{
	use Getopt::Long;

	(my $cmd = $0) =~ s/^.*\///;
	my $cwd = `pwd`; chomp $cwd;
	my $usage = "\n$cmd: $cmd -h -t filelocation -p num -s num
	-h      Display this USAGE message and exit.
	-s      Comman separated list of servers to create files in 
	-z      Size of file to send and retrieve in MB
	-n      Number of times to run test\n\n";

	my $optrequestGroup,$optdbName,$optdbServer,$optdbPort,$optdbUser,$optdbPass;
	Getopt::Long::Configure ("bundling", "ignore_case_always");
	my $rt=GetOptions(
		"s=s"=>\$optServers,
		"z=n"=>\$optSize,
		"n=n"=>\$optNum
	);              

	die "$usage" if ! $rt  ;
	die "$usage" if ! defined $optServers || ! defined $optNum || ! defined $optSize;
	$servers=$optServers;
	$numOps=$optNum;
	$fileSize=$optSize,
}

