package helpers.web;

import com.codeborne.selenide.WebDriverRunner;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.openqa.selenium.Cookie;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;

public class CookiesHelper {
    private static final String cookieFileName = "Cookies.data";

    public void saveCookies() throws IOException {
        open("/");

        var file = new File(cookieFileName);

        file.delete();
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        CSVWriter csv = new CSVWriter(fw);
        var cookies = WebDriverRunner.getWebDriver().manage().getCookies();
        for (Cookie cookie : cookies) {
            var expiry = cookie.getExpiry();

            var line = new String[6];
            line[0] = cookie.getName();
            line[1] = cookie.getValue();
            line[2] = cookie.getDomain();
            line[3] = cookie.getPath();
            line[4] = (expiry != null ? String.valueOf(expiry.getTime()) : "null");
            line[5] = String.valueOf(cookie.isSecure());
            csv.writeNext(line);
        }
        csv.close();
        fw.close();
    }

    public void loadCookies() throws Exception {
        File file = new File(cookieFileName);
        if (!file.exists())
            throw new Exception("Файл с куки не найден");

        FileReader fileReader = new FileReader(file);
        CSVReader csv = new CSVReader(fileReader);
        List<String[]> data = csv.readAll();
        for (var line : data) {
            String name = line[0];
            String value = line[1];
            String domain = line[2];
            String path = line[3];
            String expiryString = line[4];
            boolean isSecure = Boolean.parseBoolean(line[5]);

            Date expiry = null;
            if (!expiryString.equals("null")) {
                expiry = new Date(Long.parseLong(expiryString));
            }
            Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
            System.out.println(ck);
            WebDriverRunner.getWebDriver().manage().addCookie(ck);
        }
    }
}
