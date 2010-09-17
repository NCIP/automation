#!/usr/bin/perl

my $cagridDir="/maven/cagrid-repo-copy/";
my $logFile="/maven/logs/cagrid-svnadd.log";

my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= localtime(time());
my $fdate=sprintf("%04d%02d%02d_%02d%02d%02d",$year+1900,$mon+1,$mday,$hour,$min,$sec);
print "/maven/logs/maven-rsync-${fdate}.run\n";
open(LF," >/maven/logs/cagrid-svnadd-${fdate}.run") || die "Cannot open file for reading\n";

my $complete='false';
my $lastTransfer=undef;
my $currentTransfer=undef;

&verifyOptions;
&svnadd;

close(LF);

sub svnadd ()
{
	print "Entering svnadd:\n";
	print LF "Entering svnadd:\n";

	my $runDate=sprintf("%02d/%02d/%02d %02d:%02d",$mon+1,$mday,$year-100,$hour,$min);
	my $svnUser= $cmdOpts{user};
	my $svnPass= $cmdOpts{password};
	
	my @addList,@updateList;
	my $unexpectedMsg;
	
	chdir $cagridDir;
	my @svnDirList=glob("*");
	foreach my $svnDir (@svnDirList)
	{
		$emailBody .= "svn status $svnDir\n";
		print LF "svn status $svnDir\n";
		print "svn status $svnDir\n";
		open (SVNSTATUS,"svn status $svnDir |") || die "Could not run svn command";
	
		while (my $line = <SVNSTATUS>)
		{
			chomp($line);
			$emailBody .= "\t\t$line\n";
			print "\t\t$line\n";
			print LF "\t\t$line\n";
			if($line =~/^\?\s+(.*)/)
			{
				my $obj = $1;
				push(@addList, $obj);
				$emailBody .= "Adding $obj to svn\n";
				print "Adding $obj to svn\n";
				print LF "Adding $obj to svn\n";
				my $rc = qx(svn --username $svnUser --password '$svnPass' add $obj );
				my $rc = qx(svn --username $svnUser --password '$svnPass' commit -m "Added by automated routine after rsync on $runDate" $obj );
				print LF "\tRC - $rc\n";
				$emailBody .= "\tRC - $rc\n";
			}
			elsif($line =~/^A\s+(.*)/)
			{
				my $obj = $1;
				push(@addList, $obj);
				$emailBody .= "Adding $obj to svn\n";
				print "Adding $obj to svn\n";
				print LF "Adding $obj to svn\n";
				my $rc = qx(svn --username $svnUser --password '$svnPass' commit -m "Added by automated routine after rsync on $runDate" $obj );
				print LF "\tRC - $rc\n";
				$emailBody .= "\tRC - $rc\n";
			}
			elsif($line =~/^\M\s+(.*)/)
			{
				my $obj=$1;
				print LF "\t\tin update if\n";
				if ($obj =~/metadata/)
				{
					push(@updateList,$obj);
					$emailBody .= "Updating $obj in svn\n";
					print "Updating $obj in svn\n";
					print LF "Updating $obj in svn\n";
					my $rc = qx(svn --username $svnUser --password '$svnPass' commit -m "Updated by automated routine after rsync on $runDate" $obj 2>&1 );
					print LF "\tRC - $rc\n";
					$emailBody .= "\tRC - $rc\n";
				}
				elsif($obj =~/[pom$|sha1$|md5%]/)
				{
					push(@updateList,$obj);
					$emailBody .= "Updating $obj in svn\n";
					print "Updating $obj in svn\n";
					print LF "Updating $obj in svn\n";
					my $rc = qx(svn --username $svnUser --password '$svnPass' commit -m "Updated by automated routine after rsync on $runDate" $obj 2>&1 );
					print LF "\tRC - $rc\n";
					$emailBody .= "\tRC - $rc\n";
				}
				else
				{
					print LF "\t\tunexpected\n";
					$unexpectedMsg.="updated in ibiblio repo but should not be $obj\n";
				}
			}
			else
			{
				print LF "\t\tunexpected\n";
				$unexpectedMsg.="File modified $line\n";
			}
		}
	}
		
	$emailBody .= "\n##### Unexepected status\n";
	$emailBody .= "${unexpectedMsg}\n";
	print "\n##### Unexepected status\n";
	print "${unexpectedMsg}\n";
	print LF "\n##### Unexepected status\n";
	print LF "${unexpectedMsg}\n";
	
}

# Verify the options provided on command line
sub verifyOptions 
{
    use Getopt::Long;

    (my $cmd = $0) =~ s/^.*\///;
    my $cwd = `pwd`; chomp $cwd;
    my $usage = "\n$cmd: $cmd [-h] [-u user] [-p password]
    -h              Display this USAGE message and exit.
    -u user         svn user
    -p passwd       svn password\n\n";

    Getopt::Long::Configure ("bundling", "ignore_case_always");
    my $rt=GetOptions("u=s"=>\$cmdOpts{user},
		      "p=s"=>\$cmdOpts{password}
		      );

    # die if unknown options and print usage
    die "$usage" if  ! $rt  ;
    die "$usage" if ! defined $cmdOpts{user} || ! defined $cmdOpts{password};
}
