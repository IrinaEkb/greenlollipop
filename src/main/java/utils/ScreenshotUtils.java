package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            String timeStamp = new SimpleDateFormat("MMdd_HHmmss").format(new Date());
            File dest = new File("screenshots/" + screenshotName + "_" + timeStamp + ".png");

            FileUtils.copyFile(src, dest);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanOldScreenshots() {
        File folder = new File("screenshots");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (System.currentTimeMillis() - file.lastModified() > 90L * 24 * 60 * 60 * 1000) { // 90 дней
                    file.delete();
                }
            }
        }
    }
}