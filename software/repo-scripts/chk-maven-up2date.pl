#!/usr/bin/env perl
use LWP::Simple;
use Time::Local;

my %monthHash=("Jan" => 1, "Feb" => 2, "Mar" => 3, "Apr" => 4, "May" => 5, "Jun" => 6, "Jul" => 7, "Aug" => 8, "Sep" => 9, "Oct" => 10, "Nov" => 11, "Dec" => 12,);
my $url = 'https://ncisvn.nci.nih.gov/svn/maven-mirror/trunk/last_updated.txt';
my $content = get $url;
die "Couldn't get $url" unless defined $content;

chomp($content);
print "Maven Repo last updated [${content}]\n";
# Sat Jul 31 00:52:25 CDT 2010
if ($content =~ /^(\w+)\s+(\w+)\s+(\d+)\s+([\d\:]+)\s+(\w+)\s+(\d+).*/)
{
	my $dow=$1;	#print "dow - ${dow}\n";
	my $month=$2;	#print "month - ${month}\n";
	my $day=$3;	#print "day - ${day}\n"; 
	my $time=$4;	#print "time - ${time}\n";
	my $tz=$5;	#print "tz - ${tz}\n";
	my $year=$6;	#print "year - ${year}\n";

	my $mon=$monthHash{$month};
	my $lastupdated=timelocal(0,0,0,$day,$mon,$year);
	my $curtime=time;
	my $twoweeks=time - 1209600;

	#print "month is - ${mon}\n";
	#print "Last updated epoch - ${lastupdated}\n";
	#print "Current time epoch - ${curtime}\n";
	#print "Two weeks ago epoch - ${twoweeks}\n";

	if ($lastupdated < $twoweeks)
	{
		print "Check FAILED last updated (${lastupdated}) is older than two weeks ago (${twoweeks})\n";
	}
	else
	{
		print "Check passed, last updated is within the last two weeks.\n";
	}
}
