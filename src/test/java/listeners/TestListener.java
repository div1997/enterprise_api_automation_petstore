package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("🏁 Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("🏁 Test Suite Finished: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("🚀 Starting Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✅ Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("❌ Failed: " + result.getMethod().getMethodName());
        saveTextLog("Test Failed: " + result.getMethod().getMethodName() + "\nReason: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⚠️ Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Typically left blank, but required for older TestNG compilation
    }

    // Attaches a custom text log to Allure when a test fails
    @Attachment(value = "Failure Log", type = "text/plain")
    public String saveTextLog(String message) {
        return message;
    }
}