#!/usr/bin/perl

my %perfHash;
open(AWS,'aws/nbia-perf.csv') || die ("could not open AWS file\n");
while (my $line = <AWS>)
{
	chomp($line);
	my @splitLine=split(/,/,$line);
	my $dateTime=@splitLine[0];
	my $numRec=@splitLine[2];
	my $qTime=@splitLine[3];
	my $dTime=@splitLine[4];
	$dateTime=~s/\d{2}$/00/;

	#print "date- $dateTime\tnumrec- $numRec\tqtime- $qTime\tdtime- $dTime\n";
	$perfHash{$dateTime}{'aws-qtime'}=$qTime;
	$perfHash{$dateTime}{'aws-dtime'}=$dTime;
	$perfHash{$dateTime}{'aws-numrec'}=$numRec;
}
open(VMWARE,'vmware/nbia-perf.csv') || die ("could not open VMWARE file\n");
while (my $line = <VMWARE>)
{
	chomp($line);
	my @splitLine=split(/,/,$line);
	my $dateTime=@splitLine[0];
	my $numRec=@splitLine[2];
	my $qTime=@splitLine[3];
	my $dTime=@splitLine[4];
	$dateTime=~s/\d{2}$/00/;

	#print "vmware\t$dateTime\n";
	$perfHash{$dateTime}{'vmware-qtime'}=$qTime;
	$perfHash{$dateTime}{'vmware-dtime'}=$dTime;
	$perfHash{$dateTime}{'vmware-numrec'}=$numRec;
}
open(VCLOUD,'vcloud/nbia-perf.csv') || die ("could not open VCLOUD file\n");
while (my $line = <VCLOUD>)
{
	chomp($line);
	my @splitLine=split(/,/,$line);
	my $dateTime=@splitLine[0];
	my $numRec=@splitLine[2];
	my $qTime=@splitLine[3];
	my $dTime=@splitLine[4];

	my $modDate;
	if($dateTime=~/^(\d{8}\s\d{2})(\d{2}).*/)
	{
		my $min=sprintf("%02s",${2}-2);
		$modDate="${1}${min}00";
	}

	#print "vmware\t$modDate\n";
	$perfHash{$modDate}{'vcloud-qtime'}=$qTime;
	$perfHash{$modDate}{'vcloud-dtime'}=$dTime;
	$perfHash{$modDate}{'vcloud-numrec'}=$numRec;
}

open (MREP,">cloud-perf-merge.csv") || die "Cannot open merge report\n";
open (QREP,">cloud-perf-query.csv") || die "Cannot open query report\n";
open (DREP,">cloud-perf-download.csv") || die "Cannot open download report\n";

print MREP "date,aws-numrec,aws-query,aws-download,vmware-numrec,vmware-query,vmware-download,vcloud-numrec,vcloud-query,vcloud-download\n";
print QREP "date,aws-query,vmware-query,vcloud-query\n";
print DREP "date,aws-download,vmware-download,vcloud-download\n";
foreach my $date (sort keys %perfHash)
{
	my $awsQTime=$perfHash{$date}{'aws-qtime'};	
	my $awsDTime=$perfHash{$date}{'aws-dtime'};	
	my $awsNumRec=$perfHash{$date}{'aws-numrec'};	
	my $vmwareQTime=$perfHash{$date}{'vmware-qtime'};	
	my $vmwareDTime=$perfHash{$date}{'vmware-dtime'};	
	my $vmwareNumRec=$perfHash{$date}{'vmware-numrec'};	
	my $vcloudQTime=$perfHash{$date}{'vcloud-qtime'};	
	my $vcloudDTime=$perfHash{$date}{'vcloud-dtime'};	
	my $vcloudNumRec=$perfHash{$date}{'vcloud-numrec'};	

	print MREP "${date},${awsNumRec},${awsQTime},${awsDTime},${vmwareNumRec},${vmwareQTime},${vmwareDTime},${vcloudNumRec},${vcloudQTime},${vcloudDTime}\n";
	print QREP "${date},${awsQTime},${vmwareQTime},${vcloudQTime}\n";
	print DREP "${date},${awsDTime},${vmwareDTime},,${vcloudDTime}\n";
}
