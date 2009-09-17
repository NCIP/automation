#!/usr/bin/perl


my %tableHash;
my %portConfigs;
my @fileList=glob("*.properties");
foreach my $fileName(@fileList)
{
	chomp($fileName);
	my @fparts = split(/\./,$fileName);
	my $portConfig=@fparts[0];
	open(PFILE,"$fileName") || die ("could not open $fileName\n");
	while (my $line = <PFILE>)
	{
		chomp($line);
		my @lparts = split(/=/,$line);
		my $property = @lparts[0];
		my $value = @lparts[1];

		$tableHash{$property}{$portConfig}=$value;
		$portConfigs{$portConfig}++
		
	}
}

print "|| property ||";
foreach my $portConfig (sort keys %portConfigs)
{
	print " $portConfig ||";
}
print "\n";
foreach my $property ( sort keys %tableHash)
{
	print "| $property |";
	foreach my $portConfig (sort keys %portConfigs)
	{
		print " $tableHash{$property}{$portConfig} |";
	}
	print "\n";
}



