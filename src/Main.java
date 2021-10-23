// selenium imports
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// time imports
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// util array imports
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Basic Setup for Selenium; add headless mode options here
        System.setProperty("webdriver.gecko.driver", "src/geckodriver.exe");

        // Headless mode options
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");

        // Remember to remove 'options' when not in headless mode
        WebDriver driver = new FirefoxDriver(options);

        // Information that needs to be inputted by user
        ArrayList<TrackingClass> classArrayList = DataReader.readData();

        while (true) {
            for (int i = 0; i < classArrayList.size(); i++) {
                String classAbb = classArrayList.get(i).classAbb;
                String classNumber = classArrayList.get(i).classNumber;
                String teacherName = classArrayList.get(i).teacherName;
                String wantedTime = classArrayList.get(i).wantedTime;
                int reservedForOthers = classArrayList.get(i).reservedForOthers;

                // Link and paths to elements being scraped
                String siteLink = "https://webapp4.asu.edu/catalog/classlist?t=2221&s=" + classAbb + "&n=" + classNumber + "&hon=F&promod=F&e=all&page=1";
                String openSeatsXPath = "//span[contains(text(), '" + teacherName + "')]/parent::a/parent::span/parent::span/parent::span/parent::span/parent::td/parent::tr/td[@class='availableSeatsColumnValue']/div/span[1]";
                String classTimesXPath = "//span[contains(text(), '" + teacherName + "')]/parent::a/parent::span/parent::span/parent::span/parent::span/parent::td/parent::tr/td[@class=' startTimeDateColumnValue hide-column-for-online']";

                // Will keep reloading the class page until there is an empty seat; currently loop never ends
                //            boolean classOpen = false;
                //            while (!classOpen) {

                // Opens the class search page
                driver.get(siteLink);

                // Waits until the page elements have loaded
                WebDriverWait wait = new WebDriverWait(driver,120);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSeatsXPath)));

                // Finds the number of open seats for all classes taught by selected prof
                List<WebElement> openSeats = driver.findElements(By.xpath(openSeatsXPath));
                // Finds the times that the prof teaches the selected class
                List<WebElement> classTimes = driver.findElements(By.xpath(classTimesXPath));

                // Prints out current time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));

                // Prints out all class times along with number of open seats
                System.out.println(classAbb + " " + classNumber + " is taught by " + teacherName + " at the following times:");
                for (int k = 0; k < classTimes.size(); k++) {
                    String classTime = classTimes.get(k).getText();
                    String classSeats = openSeats.get(k).getText();

                    System.out.println(classTime + " : " + classSeats + " Seats Open" + " : " + reservedForOthers + " seats reserved for others");
                }

                // runs loop for each class specified in the .csv file
                for (int k = 0; k < classTimes.size(); k++) {
                    String classTime = classTimes.get(k).getText();
                    String classSeats = openSeats.get(k).getText();
                    int classSeatsInt = Integer.parseInt(classSeats);

                    // Condition checks if user's class has open seats
                    if (classTime.equals(wantedTime + " ") && classSeatsInt > reservedForOthers) {
                        System.out.println("Seats are available for " + teacherName + " at " + classTime);

                        // prints success statement
                        letsgo();

                        // plays music for open class
                        String filepath = "src/LetsGo.wav";
                        Music musicObject = new Music();
                        for(int a = 0; a < 100; a++){
                            musicObject.playMusic(filepath);
                        }

                        System.out.println("Hello");
                        // Ends loop when class has empty seats; currently never end loop
                        //                    classOpen = true;


                        // Plays sound when class is open; replaced with actual music
//                                            for (int q = 0; q < 10; q++) {
//                                                try {
//                                                    music();
//                                                } catch (LineUnavailableException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }

                    }
                }

                // Separates each loop run
                System.out.println("------------------------------------------------------------");


                // TODO: 10/21/2021 uncomment wait time; could result in potential ip ban
                // Wait given milliseconds before rerunning program
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void letsgo() {
        System.out.println("\n" +
                "██╗     ███████╗████████╗███████╗     ██████╗  ██████╗ ██╗\n" +
                "██║     ██╔════╝╚══██╔══╝██╔════╝    ██╔════╝ ██╔═══██╗██║\n" +
                "██║     █████╗     ██║   ███████╗    ██║  ███╗██║   ██║██║\n" +
                "██║     ██╔══╝     ██║   ╚════██║    ██║   ██║██║   ██║╚═╝\n" +
                "███████╗███████╗   ██║   ███████║    ╚██████╔╝╚██████╔╝██╗\n" +
                "╚══════╝╚══════╝   ╚═╝   ╚══════╝     ╚═════╝  ╚═════╝ ╚═╝");
    }
}