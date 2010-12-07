#!/usr/bin/perl

my %diskTimingsHash;
my @headings;
while (my $line = <>)
{
	chomp($line);
	my ($diskType,$platform,$op,@times)=split(/,/,$line);
	my $heading="$platform-$diskType-$op";
	push(@{$diskTimingsHash{"$heading"}},@times);
	push(@headings,$heading);
}
my $heads=join(",",@headings);
print "$heads\n";
for (my $i = 0; $i < 50; $i++)
{
	foreach my $heading (@headings)
	{
		my $timing=@{$diskTimingsHash{$heading}}[$i];
		print "$timing,";
	}
	print "\n";
}

