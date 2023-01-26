package com.studiorg.qa.selenium.utility;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.studiorg.qa.selenium.config.Constants;

public class ExtentLog {
	public static ExtentReports extent;
	public static ExtentTest test;

	public static void startTest() throws UnknownServiceException, UnknownHostException {

		InetAddress addr;
		addr = InetAddress.getLocalHost();
		String hostname = addr.getHostName();
		String timeStamp = new SimpleDateFormat("MM-dd hh.mm a").format(new Date());
		extent = new ExtentReports(Constants.Path_TestResults  + hostname + "_" + timeStamp + ".html",
				true);
		extent.addSystemInfo("Host Name", "Saurabh").addSystemInfo("Environment", "test").addSystemInfo("User Name",
				"Saurabh Gupta");
		extent.loadConfig(new File(Constants.PATH_RESOURCES + "extent-config.xml"));
	}

	public static void startTest(String sTestCaseName) {
		test = extent.startTest("Test case " + sTestCaseName);

		test.log(LogStatus.INFO, "Test Case " + sTestCaseName + " Started");
	}

	public static void endTestCase() {
		extent.endTest(test);
	}

	public static void endTest() {
		extent.flush();
		extent.close();
	}

}
