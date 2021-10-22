import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataReader {
    public static ArrayList<TrackingClass> readData(){

        ArrayList<TrackingClass> str = new ArrayList<>();

        try {

            String fileLocation = "src/ClassData.csv";
            // find the file with the vehicle data.
            File CSVLocation = new File(fileLocation);

            // use a scanner that will read from the file.
            Scanner inventoryScanner = new Scanner(CSVLocation);

            while(inventoryScanner.hasNextLine()) {
                // read a line from the file.
                String nextLine = inventoryScanner.nextLine();

                // split the file into parts.
                String[] vehicleComponents = nextLine.split(",");

                // get the components of our vehicle.
                String classAbb = vehicleComponents[0];
                String classNumber = vehicleComponents[1];
                String teacherName = vehicleComponents[2];
                String wantedTime = vehicleComponents[3];
                String reservedForOthers = vehicleComponents[4];
                int reservedForOthersInt = Integer.parseInt(reservedForOthers);


                str.add(new TrackingClass(classAbb, classNumber, teacherName, wantedTime, reservedForOthersInt));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return str;
    }
}
