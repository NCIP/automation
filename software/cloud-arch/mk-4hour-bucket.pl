#!/usr/bin/perl

my %queryBucketHash,%downloadBucketHash;
open(QRY,'cloud-perf-query.csv') || die ("could not open query file\n");
while (my $line = <QRY>)
{
	chomp($line);
	my @splitLine=split(/,/,$line);
	my $dateTime=@splitLine[0];
	next if ($dateTime=="date");
	my $aws=@splitLine[1];
	my $vmware=@splitLine[2];
	my $vcloud=@splitLine[3];
	my $hour;
	if ($dateTime=~/\d+\s+(\d{2})/){$hour=$1;}
	my $hourBucket;
	if ($hour<4){$hourBucket="00";}
	elsif ($hour>=4 && $hour<8) {$hourBucket="04";}
	elsif ($hour>=8 && $hour<12) {$hourBucket="08";}
	elsif ($hour>=12 && $hour<16) {$hourBucket="12";}
	elsif ($hour>=16 && $hour<20) {$hourBucket="16";}
	elsif ($hour>=20) {$hourBucket="20";}

	my ($date,$time)=split(/ /,$dateTime);
	my $bucketDate="$date $hourBucket";
	#print "$dateTime\t$hourBucket\n";
	
	$queryBucketHash{$bucketDate}{'count'}++;
	$queryBucketHash{$bucketDate}{'aws-query'}+=$aws;
	$queryBucketHash{$bucketDate}{'vmware-query'}+=$vmware;
	$queryBucketHash{$bucketDate}{'vcloud-query'}+=$vcloud;
}
close(QRY);
open(DL,'cloud-perf-download.csv') || die ("could not open download file\n");
while (my $line = <DL>)
{
	chomp($line);
	my @splitLine=split(/,/,$line);
	my $dateTime=@splitLine[0];
	next if ($dateTime=="date");
	my $aws=@splitLine[1];
	my $vmware=@splitLine[2];
	my $vcloud=@splitLine[3];
	my $hour;
	if ($dateTime=~/\d+\s+(\d{2})/){$hour=$1;}
	my $hourBucket;
	if ($hour<4){$hourBucket="00";}
	elsif ($hour>=4 && $hour<8) {$hourBucket="04";}
	elsif ($hour>=8 && $hour<12) {$hourBucket="08";}
	elsif ($hour>=12 && $hour<16) {$hourBucket="12";}
	elsif ($hour>=16 && $hour<20) {$hourBucket="16";}
	elsif ($hour>=20) {$hourBucket="20";}

	my ($date,$time)=split(/ /,$dateTime);
	my $bucketDate="$date $hourBucket";
	#print "$dateTime\t$hourBucket\n";
	
	$downloadBucketHash{$bucketDate}{'count'}++;
	$downloadBucketHash{$bucketDate}{'aws-query'}+=$aws;
	$downloadBucketHash{$bucketDate}{'vmware-query'}+=$vmware;
	$downloadBucketHash{$bucketDate}{'vcloud-query'}+=$vcloud;
}
close(DL);

open (QREP,">cloud-perf-query-bucket.csv") || die "Cannot open query report\n";
print QREP "date,aws-query,vmware-query,vcloud-query,cout,aws-sum,vmware-sum,vcloud-sum\n";
foreach my $bucketDate (sort keys %queryBucketHash)
{
	my $count=$queryBucketHash{$bucketDate}{'count'};
	my $awsSum=$queryBucketHash{$bucketDate}{'aws-query'};
	my $vmwareSum=$queryBucketHash{$bucketDate}{'vmware-query'};
	my $vcloudSum=$queryBucketHash{$bucketDate}{'vcloud-query'};
	my $awsAvg=int(($awsSum/$count)/1000);
	my $vmwareAvg=int(($vmwareSum/$count)/1000);
	my $vcloudAvg=int(($vcloudSum/$count)/1000);

	print QREP "${bucketDate},${awsAvg},${vmwareAvg},${vcloudAvg},${count},${awsSum},${vmwareSum},${vcloudSum}\n";
}
open (DREP,">cloud-perf-download-bucket.csv") || die "Cannot open download report\n";
print DREP "date,aws-download,vmware-download,vcloud-download,cout,aws-sum,vmware-sum,vcloud-sum\n";
foreach my $bucketDate (sort keys %downloadBucketHash)
{
	my $count=$downloadBucketHash{$bucketDate}{'count'};
	my $awsSum=$downloadBucketHash{$bucketDate}{'aws-query'};
	my $vmwareSum=$downloadBucketHash{$bucketDate}{'vmware-query'};
	my $vcloudSum=$downloadBucketHash{$bucketDate}{'vcloud-query'};
	my $awsAvg=int(($awsSum/$count)/1000);
	my $vmwareAvg=int(($vmwareSum/$count)/1000);
	my $vcloudAvg=int(($vcloudSum/$count)/1000);

	print DREP "${bucketDate},${awsAvg},${vmwareAvg},${vcloudAvg},${count},${awsSum},${vmwareSum},${vcloudSum}\n";
}
