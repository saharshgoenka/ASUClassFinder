// selenium imports

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("test");
        System.out.println("bruh");
        // Basic Setup for Selenium; add headless mode options here
        System.setProperty("webdriver.gecko.driver", "src/geckodriver.exe");

        // Headless mode options
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");

        // Remember to remove 'options' when not in headless mode
        WebDriver driver = new FirefoxDriver(options);

        // Information that needs to be inputted by user
        ArrayList<TrackingClass> str = DataReader.readData();

        while(true) {
            for (int i = 0; i < str.size(); i++) {
                String classAbb = str.get(i).classAbb;
                String classNumber = str.get(i).classNumber;
                String teacherName = str.get(i).teacherName;
                String wantedTime = str.get(i).wantedTime;
                int reservedForOthers = str.get(i).reservedForOthers;

                // Link and paths to elements being scraped
                String siteLink = "https://webapp4.asu.edu/catalog/classlist?t=2221&s=" + classAbb + "&n=" + classNumber + "&hon=F&promod=F&e=all&page=1";
                String openSeatsXPath = "//span[contains(text(), '" + teacherName + "')]/parent::a/parent::span/parent::span/parent::span/parent::span/parent::td/parent::tr/td[@class='availableSeatsColumnValue']/div/span[1]";
                String classTimesXPath = "//span[contains(text(), '" + teacherName + "')]/parent::a/parent::span/parent::span/parent::span/parent::span/parent::td/parent::tr/td[@class=' startTimeDateColumnValue hide-column-for-online']";

                // Will keep reloading the class page until there is an empty seat
                //            boolean classOpen = false;
                //            while (!classOpen) {

                // Opens the class search page
                driver.get(siteLink);

                // Waits until the page elements have loaded
                WebDriverWait wait = new WebDriverWait(driver, 30);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSeatsXPath)));

                // Finds the number of open seats for all classes taught by selected prof
                List<WebElement> openSeats = driver.findElements(By.xpath(openSeatsXPath));

                // Finds the times that the prof teaches the selected class
                List<WebElement> classTimes = driver.findElements(By.xpath(classTimesXPath));

                // Prints out all class times along with number of open seats
                System.out.println(classAbb + " " + classNumber + " is taught by " + teacherName + " at the following times:");
                for (int k = 0; k < classTimes.size(); k++) {
                    String classTime = classTimes.get(k).getText();
                    String classSeats = openSeats.get(k).getText();

                    System.out.println(classTime + " : " + classSeats + " Seats Open" + " : " + reservedForOthers + " seats reserved for others");
                }

                for (int k = 0; k < classTimes.size(); k++) {
                    String classTime = classTimes.get(k).getText();
                    String classSeats = openSeats.get(k).getText();
                    int classSeatsInt = Integer.parseInt(classSeats);

                    // Condition checks if user's class has open seats
                    if (classTime.equals(wantedTime + " ") && classSeatsInt > reservedForOthers) {
                        System.out.println("Seats are available for " + teacherName + " at " + classTime);


                        // plays music for open class
                        letsgo();
                        //                    playMusic("src/LetsGo.wav");
                        String filepath = "src/LetsGo.wav";

                        Music musicObject = new Music();
                        musicObject.playMusic(filepath);

                        // Ends loop when class has empty seats
                        //                    classOpen = true;


                        // Plays sound when class is open
                        //                    for (int q = 0; q < 10; q++) {
                        //                        try {
                        //                            music();
                        //                        } catch (LineUnavailableException e) {
                        //                            e.printStackTrace();
                        //                        }
                        //                    }

                    }
                }


                System.out.println("------------------------------------------------------------");

//                 Wait given milliseconds before rerunning program
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    // Plays byte sound
    public static void music() throws LineUnavailableException {
        byte[] buf = new byte[1];
        AudioFormat af = new AudioFormat((float) 44100, 8, 1, true, false);
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open();
        sdl.start();
        for (int i = 0; i < 1000 * (float) 44100 / 1000; i++) {
            double angle = i / ((float) 44100 / 440) * 2.0 * Math.PI;
            buf[0] = (byte) (Math.sin(angle) * 100);
            sdl.write(buf, 0, 1);
        }
        sdl.drain();
        sdl.stop();
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
