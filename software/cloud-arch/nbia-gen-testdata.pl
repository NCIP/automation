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

my $dicomTemplate, $numPeople, $numStudy;
print "Verifying command line options\n";
&verifyOptions();
&generateData();

sub generateData ()
{
	my $personInc=0;
	my $studyInc=0;
	my $personID,$studyID;
	for ($peopleCount = 1 ; $peopleCount <= $numPeople; $peopleCount++) 
	{
		$personInc++;
		$personID=sprintf("%06s",$personInc);
		for ($studyCount = 1 ; $studyCount <= $numStudy; $studyCount++) 
		{
			$studyInc++;
			$studyID=sprintf("%06s",$studyInc);

			mkdir "target";
			mkdir "target/xml";
			mkdir "target/dicom";

			my $fnameBase="p$personID-s$studyID";
			my $xmlOutFile="target/xml/${fnameBase}.xml";
			my $dicomOutFile="target/dicom/${fnameBase}.dcm";

			print "Creating files for $fnameBase\n";

			open (TFILE, "$dicomTemplate") || die "Cannot open $dicomTemplate\n";
			open (OFILE, ">$xmlOutFile") || die "Cannot create $xmlOutFile\n";
			while(my $line=<TFILE>)
			{
				if ($line =~ /(<attr tag=\"00100020\".*)>(.*)<\/attr>/)
				{
					my $newline="$1>${personID}<\/attr>\n";
					print OFILE "$newline";
				}
				elsif ($line =~ /(<attr tag=\"00100010\".*)>(.*)<\/attr>/)
				{
					my $newline="$1>Person-${personID}<\/attr>\n";
					print OFILE "$newline";
				}
				elsif ($line =~ /(<attr tag=\"0020000D\".*)>(.*)<\/attr>/)
				{
					my $newline="$1>${studyID}<\/attr>\n";
					print OFILE "$newline";
				}
				elsif ($line =~ /(<attr tag=\"0020000E\".*)>(.*)<\/attr>/)
				{
					my $newline="$1>${personID}-${studyID}<\/attr>\n";
					print OFILE "$newline";
				}
				elsif ($line =~ /(<attr tag=\"00080018\".*)>(.*)<\/attr>/)
				{
					my $newline="$1>SOP-${personID}-${studyID}<\/attr>\n";
					print OFILE "$newline";
				}
				else
				{
					print OFILE "$line";
				}
			}
			close(TFILE);
			close(OFILE);
			my $rc=qx(xml2dcm -e -E -o $dicomOutFile -x $xmlOutFile);
			print "$rc\n";
		}
	}

}
sub verifyOptions ()
{
	use Getopt::Long;

	(my $cmd = $0) =~ s/^.*\///;
	my $cwd = `pwd`; chomp $cwd;
	my $usage = "\n$cmd: $cmd -h -t filelocation -p num -s num
	-h      Display this USAGE message and exit.
	-t      Location of Dicom template file
	-p      Number of people to generate
	-s      Number of study per person\n\n";

	my $optrequestGroup,$optdbName,$optdbServer,$optdbPort,$optdbUser,$optdbPass;
	Getopt::Long::Configure ("bundling", "ignore_case_always");
	my $rt=GetOptions(
		"t=s"=>\$optTemplate,
		"p=n"=>\$optNumPeople,
		"s=n"=>\$optNumStudy
	);              

	die "$usage" if ! $rt  ;
	die "$usage" if ! defined $optTemplate || ! defined $optNumPeople || ! defined $optNumStudy;
	die "Template file does not exist $optTemplate\n" if ! -f $optTemplate;
	#die "Nubmer of People (-p option) needs to be numeric\n" if $optNumPeople!~ /^\d+$/;
	#die "Nubmer of Study (-s option) needs to be numeric\n" if $optNumStudy!~ /^\d+$/;
	$dicomTemplate=$optTemplate;
	$numPeople=$optNumPeople;
	$numStudy=$optNumStudy;
}

