package com.commons;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ReportingListener implements ITestListener {

    public static ExtentTest test;
    public static ExtentReports reporter;
    // Maintain class and test level extent tests
    public static ThreadLocal<ExtentTest> parentTests = new ThreadLocal<>();
    public static ThreadLocal<ExtentTest> tests = new ThreadLocal<>();

    public synchronized static ExtentReports getReporter() {
        long time = System.currentTimeMillis();
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/index_" + time + ".html");
        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        return extentReports;
    }

    public static void warning(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.warning(message);
        }
    }

    public static void info(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.info(message);
        }
    }

    public static void fail(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.fail(message);
        }
    }

    public static void pass(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.pass(message);
        }
    }

    public static void error(Throwable throwable) {
        ExtentTest test = getTest();
        if (test != null) {
            test.log(Status.FAIL, throwable);
        }
    }

    public static void error(String message, Throwable throwable) {
        ExtentTest test = getTest();
        if (test != null) {
            test.info(message);
            test.log(Status.FAIL, throwable);
        }
    }

    public static void code(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.info(MarkupHelper.createCodeBlock(message));
        }
    }

    public static void onClassStart(String className) {
        if (reporter == null) {
            reporter = getReporter();
        }

        ExtentTest extentTest = reporter.createTest(className);
        parentTests.set(extentTest);
    }

    public static void onClassEnd() {
        parentTests.set(null);
    }

    public static ExtentTest getTest() {
        if (tests.get() != null) {
            return tests.get();
        }

        if (parentTests.get() != null) {
            return parentTests.get();
        }

        return null;
    }

    public synchronized void onTestStart(ITestResult result) {
        ExtentTest extentTest = parentTests.get().createNode(result.getMethod().getMethodName());
        tests.set(extentTest);
    }

    public synchronized void onTestSuccess(ITestResult result) {
        tests.get().pass(result.getName() + " passed.");
        tests.set(null);
    }

    public synchronized void onTestFailure(ITestResult result) {
        tests.get().fail(result.getName() + " failed.");

        if (result.getThrowable() != null) {
            tests.get().fail(result.getThrowable());
        }

        tests.set(null);
    }

    public synchronized void onTestSkipped(ITestResult result) {
        tests.get().skip(result.getName() + " skipped.");
        tests.set(null);
    }

    public synchronized void onStart(ITestContext context) {
        reporter = getReporter();
    }

    public synchronized void onFinish(ITestContext context) {
        reporter.flush();
    }
}
