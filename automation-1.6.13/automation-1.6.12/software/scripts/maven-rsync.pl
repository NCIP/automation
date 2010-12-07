#!/usr/bin/perl

my $mavenDir="/data1/src/maven2/";
#my $mavenDir="/data/home/ssaksa/tmp/test-rsync/dest/";
my $logFile="/tmp/maven-rsync.log";

my $complete='false';
my $lastTransfer=undef;
my $currentTransfer=undef;
my $emailBody="";

chdir $mavenDir;
print "Changing dir to $mavenDir\n";
while ($complete ne  "true")
{
	$lastTransfer=$currentTransfrer;
	my $beginEpoch=time();
	my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= localtime(time());
	my $beginDate=sprintf("%02d/%02d/%02d %02d:%02d:%02d",$mon+1,$mday,$year-100,$hour,$min,$sec);
	$emailBody .= "Starting rsync $beginDate\n";
	print "Starting rsync $beginDate\n";

	open (RSYNC, "rsync -v -t -l -r mirrors.ibiblio.org::maven2 $mavenDir 2> $logFile |") || die "Could not execute rsync command\n";
	#open (RSYNC, "rsync --rsh=ssh -v -t -l -r localhost:tmp/test-rsync/src/ $mavenDir 2> $logFile |") || die "Could not execute rsync command\n";
	my $transfered=undef;
	my $cnt=0;
	while (my $line = <RSYNC>)
	{
		chomp($line);
		chomp($line);
		if ($line=~/^receiving incremental file list/)
		{
			my $x=0;
		}
		elsif ($line =~ /^total size is\s+(\d+)/)
		{
			print "\t$line\n";
			$currentTransfer=$1;
		}
		elsif ($line=~/sent\s+(\d+).*received\s+(\d+)[^\d]+([\d\.]+).*/)
		{
			print "\t$line\n";
			my $sent=$1;
			my $received=$2;
			my $speed=$3;
		}
		elsif ($line =~/^\s*$/)
		{
			my $ingore=1;
		} else
		{
			print "\t$line\n";
			$cnt++;
		}
	}
	close(RSYNC);
	print "$cnt files transfered\n";
	if($cnt == 0) {$complete="true"} 

	my $endEpoch=time();
	my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= localtime(time());
	my $endDate=sprintf("%02d/%02d/%02d %02d:%02d:%02d",$mon+1,$mday,$year-100,$hour,$min,$sec);

	my $elapsedSeconds=($endEpoch - $beginEpoch);
	$emailBody .= "Completed resync $endDate\t($elapsedSeconds)\n";
	print "Completed resync $endDate\t($elapsedSeconds)\n";
	open (RSYNCERR, "$logFile");
	my $holdTerminator= $/;
	undef $/;
	my $rsycErrors= <RSYNCERR>;
	close (RSYNCERR);
	$/=$holdTerminator;
	
	$emailBody.="$rsyncErrors\n";
	print "$rsyncErrors\n";
}

my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= localtime(time());
my $checkDate=sprintf("%02d/%02d/%02d %02d:%02d:%02d",$mon+1,$mday,$year-100,$hour,$min,$sec);
use Net::SMTP;
my $smtp = Net::SMTP->new('localhost');
$smtp->mail('steve.saksa@gmail.com');
$smtp->to('steve.saksa@gmail.com');
$smtp->data();
$smtp->datasend("To: steve.saksa\@gmail.com
	From:  <steve.saksa\@gmail.com>
	Subject: Rsync status  $checkDate
	Importance: high

	$emailBody
	");
$smtp->dataend();
$smtp->quit;
