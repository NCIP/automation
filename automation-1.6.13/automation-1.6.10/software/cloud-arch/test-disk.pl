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

my $writePaths,$numOps,$fileSize;
my @writeFileTimeArray,@delFileTimeArray;
print "Verifying command line options\n";
my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= localtime(time());
my $runDate=sprintf("%02d/%02d/%02d %02d:%02d",$mon+1,$mday,$year-100,$hour,$min);

&verifyOptions();
&testDisk();

sub testDisk ()
{
	my @pathList = split (",",$writePaths);
	foreach  my $writePath(@pathList)
	{
		@writeFileTimeArray=();
		@delFileTimeArray=();
	for ($count = 1 ; $count <= $numOps; $count++)
	{
	my $begTime=time();
	my $out=qx(dd if=/dev/zero of=${writePath}/tmpfile bs=1M count=${fileSize} 2>&1);
	my $exitcode= $? >> 8;

	my $endTime=time();
	my $totTime=($endTime - $begTime);
	
	#print "Exit Code- $exitcode\n";
	#print "Results- $out\n";
	#print "Took - $totTimes sec ($endTime $begTime)\n";

	push(@writeFileTimeArray,$totTime);
	my $begTime=time();
	my $out=qx(rm ${writePath}/tmpfile 2>&1);
	my $exitcode= $? >> 8;

	my $endTime=time();
	my $totTime=($endTime - $begTime);
	push(@delFileTimeArray,$totTime);
	}
	my $writeTimings=join(",", @writeFileTimeArray);
	print "$writePath,write,$writeTimings\n";
	my $delTimings=join(",", @delFileTimeArray);
	print "$writePath,del,$delTimings\n";
	}
}
sub verifyOptions ()
{
	use Getopt::Long;

	(my $cmd = $0) =~ s/^.*\///;
	my $cwd = `pwd`; chomp $cwd;
	my $usage = "\n$cmd: $cmd -h -t filelocation -p num -s num
	-h      Display this USAGE message and exit.
	-p      Comman separated list of paths to create files in 
	-s	Size of file in Megs
	-n      Number of times to run test\n\n";

	my $optrequestGroup,$optdbName,$optdbServer,$optdbPort,$optdbUser,$optdbPass;
	Getopt::Long::Configure ("bundling", "ignore_case_always");
	my $rt=GetOptions(
		"p=s"=>\$optPath,
		"s=n"=>\$optSize,
		"n=n"=>\$optNum
	);              

	die "$usage" if ! $rt  ;
	die "$usage" if ! defined $optPath || ! defined $optNum || ! defined $optSize;
	$writePaths=$optPath;
	$numOps=$optNum;
	$fileSize=$optSize;
}

