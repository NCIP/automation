#!/usr/bin/perl

my $fileHeader="
<project name=\"test-properties\" default=\"run\" basedir=\".\">
	<import file=\"test-common.xml\" />
	<target name=\"run\">
		<mkdir dir=\"\${antunit.xml.report.dir}\"/>
		<antunit failOnError=\"false\">
			<file file=\"\${ant.file}\"/>
			<xmllistener toDir=\"\${antunit.xml.report.dir}\" logLevel=\"verbose\" />
			<plainlistener logLevel=\"info\"/>
		</antunit>      
		<osfamily property=\"os.family\"/>
		<if>
			<or>
				<equals arg1=\"\${os.family}\" arg2=\"mac\"/>
				<equals arg1=\"\${os.family}\" arg2=\"unix\"/>
			</or>
			<then>
				<exec executable=\"egrep\">
					<arg line=\"'**** Entering|**** Exiting|passed all validation rules.|failed validation.' \${antunit.xml.report.dir}/TEST-test-suite-properties-jdk15_xml.xml\"/>
				</exec>
			</then>
			<else>
				<echo mesage=\"Grep not supported on windows\"/>
			</else>
		</if>
	</target>
	<target name=\"suiteSetUp\">
		<!-- Use this to run something when you enter this suite, like if your tests are upgrade related you can do an install first to make sure things are in a good state.-->
		<echo message=\"**** Entering Properties suiteSetUp\"/>
		<ant inheritAll=\"false\" inheritrefs=\"false\"
			antfile=\"build.xml\"             
			dir=\"\${build.dir}\"              
			target=\"dist:installer:prep\"        
			>                               
		</ant>                  
		<echo message=\"**** Exiting Properties suiteSetUp\"/>
	</target>\n";


my $passContents,$failContents,$passFileName,$failFileName;
my $passCount=0;
my $failCount=0;
my $passFileCount=0;
my $failFileCount=0;
my $testType, $prop, $rule, $value, $type;
while (my $line = <>)
{
	chomp($line);
	($prop,$type, $value)=split(/,/,$line);
	if ($type =~ /pass/)
	{
		$rule="";
		$testType="pass";
	}
	if ($type =~ /fail-(.*)/)
	{
		$rule=$1;
		$testType="fail";
	}

	# used ## for commas in source file
	$value=~ s/##/,/g;

	if ($testType eq "pass")
	{
		$passCount++;
		$passContents.="	<target name=\"test:property:${testType}:${prop}\">
		<echo message=\"**** Entering test:property:${testType}:${prop}\"/>
		<echo file=\"\${dist.exploded.dir}/local.properties\" append=\"false\">
			${prop}=${value}
			evaluate.property.name=${prop}
			groovy.debug=true
		</echo>
		<replaceregexp file=\"\${dist.exploded.dir}/local.properties\" byline=\"true\"
			match=\"^\\s+(.*)\\s*\$\"
			replace=\"\\1\"
			/>
		<execute-property-test-${testType}
			/>
		<echo message=\"**** Exiting test:property:${testType}:${prop}\"/>
	</target>\n";
	}	
	if ($testType eq "fail")
	{
		$failCount++;
		$failContents.="	<target name=\"test:property:${testType}:${prop}-${rule}\">
		<echo message=\"**** Entering test:property:${testType}:${prop}\"/>
		<echo file=\"\${dist.exploded.dir}/local.properties\" append=\"false\">
			${prop}=${value}
			evaluate.property.name=${prop}
			groovy.debug=true
		</echo>
		<replaceregexp file=\"\${dist.exploded.dir}/local.properties\" byline=\"true\"
			match=\"^\\s+(.*)\\s*\$\"
			replace=\"\\1\"
			/>
		<execute-property-test-${testType}
			/>
		<echo message=\"**** Exiting test:property:${testType}:${prop}\"/>
	</target>\n";
	}	
	if ($passCount == 20 && $testType eq "pass")
	{
		$passFileCount++;
		$passFileName="test-suite-properties-pass-${passFileCount}-jdk1.5.xml";
		open(PASS,"> ${passFileName}") || die "Could not open ${passFileName}\n";
		my $myHeader=$fileHeader;
		$myHeader=~ s/TEST-test-suite-properties-jdk15_xml.xml/TEST-test-suite-properties-pass-${passFileCount}-jdk1_5_xml.xml/;
		print PASS $myHeader;
		print PASS $passContents;
		print PASS "</project>\n";
		$passCount=0;
		$passContents="";
		close(PASS);
	}
	if ($failCount == 20 && $testType eq "fail")
	{
		$failFileCount++;
		$failFileName="test-suite-properties-fail-${failFileCount}-jdk1.5.xml";
		open(FAIL,"> ${failFileName}") || die "Could not open ${failFileName}\n";
		my $myHeader=$fileHeader;
		$myHeader=~ s/TEST-test-suite-properties-jdk15_xml.xml/TEST-test-suite-properties-fail-${failFileCount}-jdk1_5_xml.xml/;
		print FAIL $myHeader;
		print FAIL $failContents;
		print FAIL "</project>\n";
		$failCount=0;
		$failContents="";
		close(FAIL);
	}
}
