import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // Basic Setup for Selenium
        System.setProperty("webdriver.gecko.driver", "src/geckodriver.exe");

        // Headless mode options
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");

        // Remember to remove 'options' when not in headless mode
        WebDriver driver = new FirefoxDriver(options);

        // Information that needs to be inputted by user
        ArrayList<classInfoObject> classArrayList = DataReader.readData();

        while (true) {
            for (classInfoObject classInfoObject : classArrayList) {
                String className = classInfoObject.className;
                String classNumber = classInfoObject.classNumber;
                String teacherName = classInfoObject.teacherName;
                String wantedTime = classInfoObject.wantedTime;
                int reservedForOthers = classInfoObject.reservedForOthers;
                String classID = classInfoObject.classID;

                // Link and paths to element being scraped
                String siteLink = "https://webapp4.asu.edu/catalog/classlist?t=2227&k=" + classID + "&hon=F&promod=F&e=all&page=1";
                String openSeatsXPath = "//*[@Class='availableSeatsColumnValue']/div/span";

                // Opens the class search page
                driver.get(siteLink);

                // Waits until the page elements have loaded
                WebDriverWait wait = new WebDriverWait(driver, 120);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSeatsXPath)));

                // Finds the number of open seats for all classes taught by selected prof
                WebElement openSeats = driver.findElement(By.xpath(openSeatsXPath));

                String classSeats = openSeats.getText();
                System.out.println(teacherName + " has " + classSeats + " seats open at " + wantedTime + " " + className + " " + classNumber);

                // checks if user's class has open seats
                if (Integer.parseInt(classSeats) > reservedForOthers) {
                    System.out.println("Seats are available for " + teacherName);

                    // prints success statement
                    letsgo();

                    // plays music for open class
                    String filepath = "src/LetsGo.wav";
                    Music musicObject = new Music();
                    musicObject.playMusic(filepath);
                }
            }

            System.out.println("------------------------------------------------------------");

            // Prints out current time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));

            // Wait given milliseconds before rerunning program
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void letsgo() {
        System.out.println("""

                ██╗     ███████╗████████╗███████╗     ██████╗  ██████╗ ██╗
                ██║     ██╔════╝╚══██╔══╝██╔════╝    ██╔════╝ ██╔═══██╗██║
                ██║     █████╗     ██║   ███████╗    ██║  ███╗██║   ██║██║
                ██║     ██╔══╝     ██║   ╚════██║    ██║   ██║██║   ██║╚═╝
                ███████╗███████╗   ██║   ███████║    ╚██████╔╝╚██████╔╝██╗
                ╚══════╝╚══════╝   ╚═╝   ╚══════╝     ╚═════╝  ╚═════╝ ╚═╝""");
    }
}