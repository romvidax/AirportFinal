package Tests;


import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

public class TestRunner {

	public static void main(String[] args) {

		org.junit.runner.Result result = JUnitCore.runClasses(AllTests.class);
		
		
		for (Failure fail : result.getFailures()) {
			System.out.println(fail.toString());
		}
		
		System.out.println(result.wasSuccessful());
	}

}

