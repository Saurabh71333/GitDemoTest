package com.studiorg.qa.selenium.executionEngine;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import com.relevantcodes.extentreports.LogStatus;
import com.studiorg.qa.selenium.config.ActionKeywords;
import com.studiorg.qa.selenium.config.Constants;
import com.studiorg.qa.selenium.utility.ExcelUtils;
import com.studiorg.qa.selenium.utility.ExtentLog;
import com.studiorg.qa.selenium.utility.Log;

public class DriverScript {
	// This is a class object, declared as 'public static'
	// So that it can be used outside the scope of main[] method
	public static ActionKeywords actionKeywords;
	public static String sPageObject;
	public static String sActionKeyword;
	public static String sTestSuitName;
	public static String sData;
	public static String sBrowser;
	public static String sEnvironment;
	public static Method[] method;
	public static String sPath;
	public static String sTestCaseID;
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sRunMode;
	public static Properties OR;
	public static boolean bResult=true;
	public static boolean bStep=true;

	public static void main(String[] args) throws Exception {
		try {
			System.out.println("Automation has been started successfully!");
			// Method name capture
			method = ActionKeywords.class.getMethods();
			// Declaring String variable for storing Object Repository path
			String Path_OR = Constants.Path_OR;

			// This is to start the Log4j logging in the test case
			DOMConfigurator.configure(Constants.PATH_LOG_CONFIG);
			// System.setProperty("my.log", "C:/logfile.log");
			// Creating file system object for Object Repository text/property
			// file
			FileInputStream fs = new FileInputStream(Path_OR);
			// Creating an Object of properties
			OR = new Properties(System.getProperties());
			// Loading all the properties from Object Repository property file
			// in to OR object
			OR.load(fs);

			// Set Configuration File Path
			ExcelUtils.setExcelFile(Constants.PATH_CONFIG);

			// Instantiate DriverScript Object
			DriverScript startEngine = new DriverScript();

			// Read Configuration File
			startEngine.readConfigFile();
			// End of Main Method
		} catch (Exception e) {
			System.out.println("Unable to Load Files -----" + e.getMessage());
		}
		System.out.println("End of Automation");

	}

	// Get The TestSuit Names
	private void readConfigFile() throws Exception {
		try {
			// Read data from "Config" Test Sheet
			int iTotalTestSuites = ExcelUtils.getRowCount(Constants.SHEET_TESTSUITES);

			// Start Extent Log
			ExtentLog.startTest();

			// Start Test Suit
			for (int iTestSuit = 1; iTestSuit < iTotalTestSuites; iTestSuit++) {

				// Set bResult token to True
				bResult = true;

				// Reset Excel File Path to point to Config
				ExcelUtils.setExcelFile(Constants.PATH_CONFIG);

				// Get Test Suit Name, Browser Name, Environment
				sTestSuitName = ExcelUtils.getCellData(iTestSuit, Constants.COL_TESTSUITNAME,
						Constants.SHEET_TESTSUITES);
				sBrowser = ExcelUtils.getCellData(iTestSuit, Constants.COL_BROWSER, Constants.SHEET_TESTSUITES);
				sEnvironment = ExcelUtils.getCellData(iTestSuit, Constants.COL_ENVIRONMENT, Constants.SHEET_TESTSUITES);

				// Start Test Suit Log
				Log.startTestSuit(sTestSuitName);
				Log.info("Starting Test Suit " + sTestSuitName);

				// Execute Test Suit
				executeTestSuit();

				// End Log4J Test Suit Logs
				Log.endTestSuit(sTestSuitName);
			}
			// End Extent Logs
			ExtentLog.endTest();

		} catch (Exception e) {
			// Notify Test Suit Failure
			System.out.println("Failed to Execute Test Suit - " + sTestSuitName + " ------ " + e.getMessage());
			Log.error("Failed to Execute Test Suit - " + sTestSuitName + " ------- " + e.getMessage());
			Log.endTestSuit(sTestSuitName);
			ExtentLog.test.log(LogStatus.FAIL,
					"Failed to Execute Test Suit - " + sTestSuitName + " ------- " + e.getMessage());
			ExtentLog.endTest();
		}
	}

	private void executeTestSuit() throws Exception {
		int iTestcase = 1;
		try {

			// Go to the Test Suit
			sPath = Constants.PATH_TESTSUITES + sTestSuitName + ".xlsx";

			// Set the Excel file path to find Test Suites
			ExcelUtils.setExcelFile(sPath);

			// Calculate total number of test cases in the suite
			int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);

			// Execute Test Case Row by row
			for (iTestcase = 1; iTestcase < iTotalTestCases; iTestcase++) {

				// Set the Value of Token to True
				bResult = true;

				// This is to get the Test case name from the Test Cases sheet
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);

				// This is to get the value of the Run Mode column for the
				// current test case
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode, Constants.Sheet_TestCases);

				// Start Test Case
				ExtentLog.startTest(sTestCaseID);

				// This is the condition statement on RunMode value
				if (sRunMode.equals("Yes")) {
					// Start The Test Case Log
					Log.startTestCase(sTestCaseID);
					Log.info("Starting Test Case " + sTestCaseID);
					// Only if the value of Run Mode is 'Yes', this part of code
					// will execute
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID,
							Constants.Sheet_TestSteps);

					iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
					// Set bResult Token = TRUE
					bResult = true;

					ActionKeywords.openBrowser("", sBrowser);

					ActionKeywords.selectEnvironmenment("", sEnvironment);
					// This loop will execute number of times equal to Total
					// number of test steps

					for (; iTestStep < iTestLastStep; iTestStep++) {
						sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,
								Constants.Sheet_TestSteps);
						sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject,
								Constants.Sheet_TestSteps);
						// Now we will use the data value and pass it to the
						// methods
						sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);

						execute_Actions();
						//Change the Step value to true
						bStep =true;
						// This is the result code, this code will execute after
						// each test step
						// The execution flow will go in to this only if the
						// value of bResult is 'false'
						if (bResult == false) {
							// If 'false' then store the test case result as
							// Fail
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestcase, Constants.Col_Result,
									Constants.Sheet_TestCases,sPath);
							// End the test case in the logs
							Log.error("Test Case " + sTestCaseID + "Failed");
							Log.endTestCase(sTestCaseID);
							ExtentLog.test.log(LogStatus.FAIL, "Test Case " + sTestCaseID + " Failed");
							// ExtentLog.extent.endTest(ExtentLog.test);
							// By this break statement, execution flow will not
							// execute any more test step of the failed test
							// case
							break;
						}

					}
					// This will only execute after the last step of the test
					// case, if value is not 'false' at any step
					if (bResult == true) {
						// Storing the result as Pass in the excel sheet
						ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestcase, Constants.Col_Result,
								Constants.Sheet_TestCases,sPath);
						Log.info("Test Case " + sTestCaseID + " Passed");
						Log.endTestCase(sTestCaseID);
						ExtentLog.test.log(LogStatus.PASS, "Test Case " + sTestCaseID + " Passed");
						ExtentLog.extent.endTest(ExtentLog.test);
					}
				} else
					ExtentLog.test.log(LogStatus.SKIP, "Test Case " + sTestCaseID + " Skipped");
				ExtentLog.extent.endTest(ExtentLog.test);
			}

		} catch (Exception e) {
			System.out.println("Unable to load Test Suit and other files  --- " +e.getMessage());
			ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestcase, Constants.Col_Result, Constants.Sheet_TestCases,sPath);

			Log.endTestCase(sTestCaseID);
		}

		System.out.println();
	}

	// This method contains the code to perform some action
	// As it is completely different set of logic, which revolves around the
	// action only,
	// It makes sense to keep it separate from the main driver script
	// This is to execute test step (Action)
	private void execute_Actions() throws Exception {
		// This is a loop which will run for the number of actions in the Action
		// Keyword class
		// method variable contain all the method and method.length returns the
		// total number of methods
		for(int i = 0;i <method.length;i++)
		{
			if(method[i].getName().equals(sActionKeyword))
			{
				method[i].invoke(actionKeywords,sPageObject,sData);
				//This code block will execute after every test step
				if(bResult==true)
				{
					if(bStep==true)
					{
						//If the executed test step value is true, Pass the test step in Excel sheet
						ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps,sPath);
						break;
					}
					else if(bStep==false)
					{
						//If the executed test step value is false, Fail the test step in Excel sheet
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps,sPath);
						break;
					}
				}
			
			else if(bResult==false)
			{	
				
				//In case of false, the test execution will not reach to last step of closing browser
				//So it make sense to close the browser before moving on to next test case
				ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps,sPath);
				ActionKeywords.takeScreenshot("","");
				ActionKeywords.closeBrowser("","");
				break;
			}
		}
	}
	}
}
