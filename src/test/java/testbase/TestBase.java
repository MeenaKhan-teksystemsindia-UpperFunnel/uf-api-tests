package testbase;

import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBase {

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        setupReportNg();
    }


    private void setupReportNg() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());

        String title = String.format("Api Test Suite Results - %s", date);

        System.setProperty("org.uncommons.reportng.escape-output", "false");
        System.setProperty("org.uncommons.reportng.title", title);
    }
}