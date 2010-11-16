package bda.blueprints.business.service;

import junit.framework.Test;

import com.clarkware.junitperf.TimedTest;

public class ExampleTimedTest {

	private static final String TEST_PERFORMANCE_GET_STUDY = "testPerformanceGetStudy";

	public static Test suite() {

		long maxElapsedTime = 100;

		Test testCase = new StudyServicePerformanceTest(
				TEST_PERFORMANCE_GET_STUDY);
		Test timedTest = new TimedTest(testCase, maxElapsedTime);

		return timedTest;
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
}