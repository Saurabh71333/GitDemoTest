package com.studiorg.qa.selenium.config;

public class Constants {
/*	public static String OTC = "http://test.orientaltrading.com/";
	public static String Learn = "http://test.learn365.orientaltrading.com/";
	public static String Mindware = "http://test.mindware.orientaltrading.com/";
	public static String Marryme = "http://test.marryme.orientaltrading.com/";
	public static String F365 = "http://test.fun365.orientaltrading.com/";
	public static  String CF365 = "http://test.customfun365.orientaltrading.com/";*/
	
	public static String UAT = "http://uats.azurewebsites.net/PartsTown";
	public static String PROD = "http://studiorguat.azurewebsites.net/PartsTown";
	public static String QA1 = "http://studiorgqa1.azurewebsites.net/PartsTown";
	

	public static final String PATH_CONFIG = System.getProperty("user.dir")
			+ "\\testSuites\\Configuration.xlsx";
	public static final String Path_TestResults = System.getProperty("user.dir") + "\\testResults\\";
	public static final String PATH_RESOURCES = System.getProperty("user.dir") + "\\resources\\";
	public static final String PATH_TESTSUITES = System.getProperty("user.dir") + "\\testSuites\\";
	public static final String PATH_LOG_CONFIG = System.getProperty("user.dir") + "\\resources\\log4j.xml";
	// public static final String File_TestData = "DataEngine.xlsx";
	public static final String PATH_EXTENT_CONFIG = System.getProperty("user.dir") + "\\resources\\extent-config.xml";
	public static final String Path_OR = System.getProperty("user.dir") + "\\resources\\OR.txt";
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";

	// List of Test Suit Variables
	public static final int COL_TESTSUITNAME = 1;
	public static final int COL_BROWSER = 2;
	public static final int COL_ENVIRONMENT = 3;
	public static final int COL_TESTSUITRESULT = 4;

	
	// List of Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;
	public static final int Col_TestScenarioID = 1;
	public static final int Col_PageObject = 3;
	public static final int Col_ActionKeyword = 4;
	public static final int Col_DataSet = 5;
	public static final int Col_TestStepResult = 6;
	public static final int Col_RunMode = 2;
	public static final int Col_Result = 3;

	// List of Data Engine Excel sheets
	public static final String SHEET_TESTSUITES = "Config";
	public static final String Sheet_TestSteps = "Test Steps";
	public static final String Sheet_TestCases = "Test Cases";

}

