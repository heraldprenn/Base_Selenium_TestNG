package stepDefinitions;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import utilities.Base;

public class ScenarioHooks {
	WebDriver webDriver = Base.getWebDriver();

	// ==================================================|Hooks|==================================================
	@Before
	public void scenarioBefore() {
		launchBrowser();
	}

	@After
	public void scenarioAfter() {
		quitBrowser();
		cleanDownloadDirectory();
	}

	@AfterStep
	public void scenarioStepAfter(Scenario scenario) {
		screenshotFailedScenario(scenario);
		raiseJIRATicketFailedScenario(scenario);
	}

	// ==================================================|Helpers|==================================================
	private void launchBrowser() {
		webDriver = Base.initializeWebDriver(Base.configurationVariables("browser"));
	}

	private void quitBrowser() {
		if (webDriver != null)
			webDriver.quit();
	}

	public void screenshotFailedScenario(Scenario scenario) {
		if (webDriver != null && scenario.isFailed()) {
			try {
				byte[] byteScreenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);

				scenario.attach(byteScreenshot, "image/png", "AttachedScreenshots");
			} catch (WebDriverException webDriverException) {
				webDriverException.printStackTrace();
			}
		}
	}

	public void raiseJIRATicketFailedScenario(Scenario scenario) {
		if (Base.configurationVariables("jiraAutoRaiseTicket").equals("true") && scenario.isFailed()) {
			try {
				BasicCredentials basicCredentials = new BasicCredentials(Base.configurationVariables("jiraUserEmail"),
						Base.configurationVariables("jiraAPIKey"));
				JiraClient jiraClient = new JiraClient(Base.configurationVariables("jiraURL"), basicCredentials);
				Issue issue = jiraClient.createIssue(Base.configurationVariables("jiraProjectKey"), "Bug")
						.field(Field.SUMMARY, scenario.getName() + " - " + "CAUSE")
						.field(Field.DESCRIPTION, "STEPS TO REPLICATE").execute();

				issue.link(scenario.getId().substring(
						scenario.getId().indexOf(Base.configurationVariables("jiraProjectKey") + "-"),
						scenario.getId().indexOf(".feature:")), "Relates");
			} catch (JiraException jiraException) {
				jiraException.printStackTrace();
			}
		}
	}
	
	public void cleanDownloadDirectory() {
		File downloadDirectory = new File(Base.configurationVariables("downloadPath"));
		try {
			FileUtils.cleanDirectory(downloadDirectory);
		} catch (IOException e) {
			System.out.println("Unable to Clean Download Directory.");
		} 
	}
}