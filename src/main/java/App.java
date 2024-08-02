import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.HashMap;
import org.openqa.selenium.NoSuchElementException;
public class App {
    public static void main(String[] args) throws java.io.IOException, InterruptedException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        HashMap<ElementsType, String> elementsXpath = new HashMap<>();
        elementsXpath.put(ElementsType.USERNAME_INPUT, "//*[@id=\"email\"]");
        elementsXpath.put(ElementsType.PASSWORD_INPUT, "//*[@id=\"pass\"]");
        elementsXpath.put(ElementsType.LOGIN_FORM, "//*[@id=\"login_form\"]");
        elementsXpath.put(ElementsType.ALWAYS_TRUST, "/html/body/div[1]/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[2]/div/div/div[3]/div[1]/div/div/div/div[1]/div/span/span");
        elementsXpath.put(ElementsType.OUTGOING_REQUESTS, "/html/body/div[1]/div/div[1]/div/div[3]/div/div/div[1]/div[1]/div[1]/div/div[2]/div[1]/div[2]/div/div[3]/div/span");


        WebDriver gecko = new FirefoxDriver();
        gecko.manage().window().minimize();
        gecko.manage().deleteAllCookies();
        gecko.get("https://www.facebook.com/login/");

        System.out.print("Please enter your Email or Phone Number: ");
        String user = buffer.readLine();
        System.out.print("Please enter your password: ");
        String pass = buffer.readLine();

        gecko.findElement(By.xpath(elementsXpath.get(ElementsType.USERNAME_INPUT))).sendKeys(user);
        gecko.findElement(By.xpath(elementsXpath.get(ElementsType.PASSWORD_INPUT))).sendKeys(pass);
        gecko.findElement(By.xpath(elementsXpath.get(ElementsType.LOGIN_FORM))).submit();

        System.out.println("Approve 2FA in your device please.");

        boolean isAuthenticated = false;
        while (!isAuthenticated) {
            try {
                gecko.findElement(By.xpath(elementsXpath.get(ElementsType.ALWAYS_TRUST))).click();
                isAuthenticated = true;
            } catch (Exception e) {
                Thread.sleep(500);
            }
        }

        System.out.println("Login successful, 2FA approved.");
        Thread.sleep(1000);
        gecko.get("https://www.facebook.com/friends/requests");
        Thread.sleep(2000);
        gecko.findElement(By.xpath(elementsXpath.get(ElementsType.OUTGOING_REQUESTS))).click();

        JavascriptExecutor js = (JavascriptExecutor) gecko;
        int height = 0;
        int lastPercent = 0;
        int percent = 0;
        int maxHeight = 54441;
        Thread.sleep(5000);
        while (height < (maxHeight - 75) && percent < 99) {
            WebElement heightElement = gecko.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/div[3]/div[2]"));
            String heightStyle = heightElement.getAttribute("style");
            int startIndex = heightStyle.indexOf("height:") + "height:".length();
            int endIndex = heightStyle.indexOf("px", startIndex);
            height = Integer.parseInt(heightStyle.substring(startIndex, endIndex).trim());


            percent = (height * 100) / maxHeight;
            if (lastPercent != percent) {
                System.out.println(percent + "%");
                lastPercent = percent;
            }

            WebElement scrollContainer = gecko.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/div[3]"));
            js.executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight);", scrollContainer);

            Thread.sleep(200);
        }

        String filePath = "/home/antonio/Documents/bitches";

        File bitches = new File(filePath);
        if (bitches.createNewFile()) {
            System.out.println("File created: " + bitches.getName());
        } else {
            System.out.println("File already exists.");
        }

        try {
            FileWriter myWriter = new FileWriter(filePath);
            gecko.manage().window().maximize();

            for (int i = 2; i <= 716; i++) {
                try {
                    String name = gecko.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/div[3]/div[1]/div[" + i + "]/div/a/div[1]/div[2]/div/div[1]/div/div[1]/span/span/span")).getText();
                    myWriter.write(name + "\n");
                    gecko.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div[2]/div/div/div/div[3]/div[1]/div[" + i + "]/div/a/div[1]/div[2]/div/div[2]/div/div/div/div/div/div[1]/div/span/span")).click();
                } catch (NoSuchElementException e) {
                    System.out.println("A bitch got away :(");
                }
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            System.out.println(e.getMessage());
        }
        gecko.quit();
    }
}
