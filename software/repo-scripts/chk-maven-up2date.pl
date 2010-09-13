#!/usr/bin/env perl
#use LWP::Simple;
use Time::Local;

my %monthHash=("Jan" => 1, "Feb" => 2, "Mar" => 3, "Apr" => 4, "May" => 5, "Jun" => 6, "Jul" => 7, "Aug" => 8, "Sep" => 9, "Oct" => 10, "Nov" => 11, "Dec" => 12,);
my $url = 'https://ncisvn.nci.nih.gov/svn/maven-mirror/trunk/last_updated.txt';
my $debug=true if $ENV{"debug"};
#my $content = get $url;
#die "Couldn't get $url" unless defined $content;
unlink "/maven/tmp/last_updated.txt";
my $rc =qx(wget -q -P /maven/tmp/  $url);
print "rc - [${rc}]\n" if $debug;
die "Couldn't get $url" unless defined $rc;

my $content;
{
  local $/=undef;
  open FILE, "/maven/tmp/last_updated.txt" or die "Couldn't open file: $!";
  binmode FILE;
  $content = <FILE>;
  close FILE;
}

chomp($content);
print "Maven Repo last updated [${content}]\n";
# Sat Jul 31 00:52:25 CDT 2010
if ($content =~ /^(\w+)\s+(\w+)\s+(\d+)\s+([\d\:]+)\s+(\w+)\s+(\d+).*/)
{
	my $dow=$1;
	my $month=$2;
	my $day=$3;
	my $time=$4;
	my $tz=$5;
	my $year=$6;

	my $mon=$monthHash{$month};
	my $lastupdated=timelocal(0,0,0,$day,($mon-1),($year-1900));
	my $curtime=time;
	my $twoweeks=time - 1209600;

	print "dow - ${dow}\n" if $debug;
	print "month - ${month}\n" if $debug;
	print "mon - ${mon}\n" if $debug;
	print "day - ${day}\n" if $debug;
	print "time - ${time}\n" if $debug;
	print "tz - ${tz}\n" if $debug;
	print "year - ${year}\n" if $debug;
	print "Last updated epoch - ${lastupdated}\n" if $debug;
	print "Current time epoch - ${curtime}\n" if $debug;
	print "Two weeks ago epoch - ${twoweeks}\n" if $debug;

	if ($lastupdated < $twoweeks)
	{
		print "Check FAILED last updated (${lastupdated}) is older than two weeks ago (${twoweeks})\n";
		print "Sending Failure email\n";
		my $sendmail = "/usr/sbin/sendmail -t"; 
		open(SENDMAIL, "|$sendmail") or die "Cannot open $sendmail: $!";
		print SENDMAIL "To: steve.saksa\@gmail.com\n";
		print SENDMAIL "From: saksass\@mail.nih.gov\n";
		print SENDMAIL "Subject: Maven is out of date\n";
		print SENDMAIL "Content-type: text/plain\n\n";
		print SENDMAIL "The date (${content}) is older than two weeks, maven is probably out of date.\n";
		close(SENDMAIL); 
	}
	else
	{
		print "Check passed, last updated is within the last two weeks.\n";
	}
}
