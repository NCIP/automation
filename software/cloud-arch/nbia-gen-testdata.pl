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

my $dicomTemplate, $numPeople, $numSeries;
print "Verifying command line options\n";
&verifyOptions();

sub verifyOptions ()
{
	use Getopt::Long;

	(my $cmd = $0) =~ s/^.*\///;
	my $cwd = `pwd`; chomp $cwd;
	my $usage = "\n$cmd: $cmd -h -t filelocation -p num -s num
	-h      Display this USAGE message and exit.
	-t      Location of Dicom template file
	-p      Number of people to generate
	-s      Number of series per person\n\n";

	my $optrequestGroup,$optdbName,$optdbServer,$optdbPort,$optdbUser,$optdbPass;
	Getopt::Long::Configure ("bundling", "ignore_case_always");
	my $rt=GetOptions(
		"t=s"=>\$optTemplate,
		"p=n"=>\$optNumPeople,
		"s=n"=>\$optNumSeries
	);              

	die "$usage" if ! $rt  ;
	die "$usage" if ! defined $optTemplate || ! defined $optNumPeople || ! defined $optNumSeries;
	die "Template file does not exist $optTemplate\n" if ! -f $optTemplate;
	#die "Nubmer of People (-p option) needs to be numeric\n" if $optNumPeople!~ /^\d+$/;
	#die "Nubmer of Series (-s option) needs to be numeric\n" if $optNumSeries!~ /^\d+$/;
	$dicomTemplate=$optTemplate;
	$numPeople=$optNumPeople;
	$numSeries=$optNumSeries;
}

