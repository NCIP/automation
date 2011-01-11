#!/usr/bin/perl

my $mavenDir="/maven/maven-mirror/";
my $logFile="/maven/logs/mr.log";

my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= localtime(time());
my $fdate=sprintf("%04d%02d%02d_%02d%02d%02d",$year+1900,$mon+1,$mday,$hour,$min,$sec);
print "/maven/logs/maven-rsync-${fdate}.run\n";
open(LF," >/maven/logs/maven-rsync-${fdate}.run") || die "Cannot open maven-rsync.run for reading\n";

my $complete='false';
my $lastTransfer=undef;
my $currentTransfer=undef;

my $emailBody="";
my $sendmail = "/usr/sbin/sendmail -t";
my $reply_to = "ncicbiitbda\@mail.nih.gov\n";
my $subject = "maven-rsync status report\n";
my $send_to="steven.saksa\@stelligent.com\n";
my $from="ncicbiitbda\@mail.nih.gov\n";

my $rsync_count=0;
my $svn_added=0;
my $svn_updated=0;
my $svn_failed=0;

&verifyOptions;
&mavensync;
&updateFileList;
&fixsha;
&svnadd;
&notify;

close(LF);

sub mavensync() 
{
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
		print LF "Starting rsync $beginDate\n";
	
		open (RSYNC, "rsync -v -t -l -r  --exclude=**/.svn mirrors.ibiblio.org::maven2 $mavenDir 2> $logFile |") || die "Could not execute rsync command\n";
		#open (RSYNC, "rsync --rsh=ssh -v -t -l -r localhost:tmp/test-rsync/src/ $mavenDir 2> $logFile |") || die "Could not execute rsync command\n";
		my $transfered=undef;
		my $cnt=0;
		while (my $line = <RSYNC>)
		{
			chomp($line);
			chomp($line);
			if ($line=~/^receiving incremental file list/ || $line=~/receiving file list/)
			{
				my $x=0;
			}
			elsif ($line =~ /^total size is\s+(\d+)/)
			{
				$emailBody .= "\t$line\n";
				print "\t$line\n";
				print LF"\t$line\n";
				$currentTransfer=$1;
			}
			elsif ($line=~/sent\s+(\d+).*received\s+(\d+)[^\d]+([\d\.]+).*/)
			{
				$emailBody .= "\t$line\n";
				print "\t$line\n";
				print LF "\t$line\n";
				my $sent=$1;
				my $received=$2;
				my $speed=$3;
			}
			elsif ($line =~/^\s*$/)
			{
				my $ingore=1;
			} else
			{
				$emailBody .= "\t$line\n";
				print "\t$line\n";
				print LF "\t$line\n";
				$cnt++;
			}
		}
		close(RSYNC);
		$emailBody .= "$cnt files transfered\n";
		print "$cnt files transfered\n";
		print LF "$cnt files transfered\n";
		$rsync_count+=$cnt;
		if($cnt == 0) {$complete="true"} 
	
		my $endEpoch=time();
		my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= localtime(time());
		my $endDate=sprintf("%02d/%02d/%02d %02d:%02d:%02d",$mon+1,$mday,$year-100,$hour,$min,$sec);
	
		my $elapsedSeconds=($endEpoch - $beginEpoch);
		$emailBody .= "Completed resync $endDate\t($elapsedSeconds)\n";
		print "Completed resync $endDate\t($elapsedSeconds)\n";
		print LF "Completed resync $endDate\t($elapsedSeconds)\n";
		open (RSYNCERR, "$logFile");
		my $holdTerminator= $/;
		undef $/;
		my $rsycErrors= <RSYNCERR>;
		close (RSYNCERR);
		$/=$holdTerminator;
		
		$emailBody.="$rsyncErrors\n";
		print "$rsyncErrors\n";
		print LF "$rsyncErrors\n";
	}
	
	my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)= localtime(time());
	my $checkDate=sprintf("%02d/%02d/%02d %02d:%02d:%02d",$mon+1,$mday,$year-100,$hour,$min,$sec);
	$emailBody.="Rsync Finished at ${checkDate}\n";
	print "Rsync Finished at ${checkDate}\n";
	print LF "Rsync Finished at ${checkDate}\n";
}

sub notify()
{
#	open(SENDMAIL, "|$sendmail") or die "Cannot open $sendmail: $!";
#	print SENDMAIL $reply_to;
#	print SENDMAIL $subject;
#	print SENDMAIL $send_to;
#	print SENDMAIL "Content-type: text/plain\n\n";
#	print SENDMAIL $emailBody;
#	close(SENDMAIL);
	my $email_msg="Records synced via rsync - ${rsync_count}
Records updated in svn - ${svn_updated}
Records added in svn - ${svn_added}
Records failed in svn - ${svn_failed}
";

	my $smtp = Net::SMTP->new('mailfwd.nih.gov');
	$smtp->mail('bda_user@mail.nih.gov');
	$smtp->to($send_to);
	$smtp->data();
	$smtp->datasend("To: ${send_to}
From:  <saksass\@mail.nih.gov>
Subject: ${subject}
Importance: high

$email_msg
");
	$smtp->dataend();
	$smtp->quit;
	print "EMAIL Sent.";
	print LF "EMAIL Sent.";
}

sub fixsha()
{
	print "Entering fixsha:\n";
	print LF "Entering fixsha:\n";
	my @fileList=qx(find . -name "*.sha1"  -exec grep -il "=" {} \\;);
	
	foreach my $file (@fileList)
	{
		$emailBody .= "Processing file $file\n";
		print "Processing file $file\n";
		print LF "Processing file $file\n";
		my $sha="";
		open(INFILE,"$file") || die "Cannot open $file for reading\n";
		while (my $line =<INFILE>)
		{
			chomp($line);
			if($line =~ /SHA1.*= (\S+)/)
			{
			$sha=$1;
			}
		}
		close(INFILE);
		if ($sha=~/\S+/)
		{
			open(OUTFILE,">$file") || die "Cannot open $file for writing\n";
			$emailBody .= "Updating sha file format for ${file}\n";
			print "Updating sha file format for ${file}\n";
			print LF "Updating sha file format for ${file}\n";
			print OUTFILE "$sha\n";
			close(OUTFILE);
		}
	}
}

sub svnadd ()
{
	print "Entering svnadd:\n";
	print LF "Entering svnadd:\n";

	my $runDate=sprintf("%02d/%02d/%02d %02d:%02d",$mon+1,$mday,$year-100,$hour,$min);
	my $svnUser= $cmdOpts{user};
	my $svnPass= $cmdOpts{password};
	
	my @addList,@updateList;
	my $unexpectedMsg;
	
	chdir $mavenDir;
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
				if ($rc !~ /Commmited revision \d+/)
				{
					print "No commit message, commit probably failed\n";
					print LF "No commit message, commit probably failed\n";
					$svn_failed++;
				}
				print LF "\tAdd 1 RC - $rc\n";
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
				if ($rc !~ /Commmited revision \d+/)
				{
					print "No commit message, commit probably failed\n";
					print LF "No commit message, commit probably failed\n";
					$svn_failed++;
				}
				print LF "\tAdd 2 RC - $rc\n";
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
					if ($rc !~ /Commmited revision \d+/)
					{
						print "No commit message, commit probably failed\n";
						print LF "No commit message, commit probably failed\n";
						$svn_failed++;
					}
					print LF "\tUpdate 1 RC - $rc\n";
					$emailBody .= "\tRC - $rc\n";
				}
				elsif($obj =~/[pom$|sha1$|md5%]/)
				{
					push(@updateList,$obj);
					$emailBody .= "Updating $obj in svn\n";
					print "Updating $obj in svn\n";
					print LF "Updating $obj in svn\n";
					my $rc = qx(svn --username $svnUser --password '$svnPass' commit -m "Updated by automated routine after rsync on $runDate" $obj 2>&1 );
					if ($rc !~ /Commmited revision \d+/)
					{
						print "No commit message, commit probably failed\n";
						print LF "No commit message, commit probably failed\n";
						$svn_failed++;
					}
					print LF "\tUpdate 2 RC - $rc\n";
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
sub updateFileList
{
	print "Updating File List\n";
	print LF "Updating File List\n";
	chdir $mavenDir;
	print "Changing dir to $mavenDir\n";
	my $fileListFile="$mavenDir/file-list.txt";
	my $i=0;
	open (FL,">$fileListFile") || die "Could not open $fileListFile}\n";
	open(FIND,"find . -name *.jar |") || die "could not run find command\n";
	while (my $line = <FIND>)
	{
		$i++;
		print FL "$line";
	}
	close(FIND);
	close(FL);
}
