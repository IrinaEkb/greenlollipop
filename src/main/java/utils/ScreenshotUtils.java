package utils;

import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static final String BASE_DIR = "screenshots";

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public static byte[] captureScreenshot(WebDriver driver, String testName) {
        byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        saveScreenshotToFile(bytes, testName);
        return bytes;
    }

    private static void saveScreenshotToFile(byte[] bytes, String testName) {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String time = new SimpleDateFormat("HHmmss").format(new Date());

            File dir = new File(BASE_DIR + "/" + date);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(dir, testName + "_" + time + ".png");
            FileUtils.writeByteArrayToFile(file, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Очистка файлов старше 90 дней
    public static void cleanOldScreenshots(int days) {
        File folder = new File(BASE_DIR);
        if (!folder.exists()) return;

        long maxAge = days * 24L * 60 * 60 * 1000;

        for (File dir : folder.listFiles()) {
            for (File file : dir.listFiles()) {
                if (System.currentTimeMillis() - file.lastModified() > maxAge) {
                    file.delete();
                }
            }
        }
    }
}