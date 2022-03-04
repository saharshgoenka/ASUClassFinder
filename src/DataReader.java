import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataReader {
    public static ArrayList<classInfoObject> readData(){

        ArrayList<classInfoObject> str = new ArrayList<>();

        try {

            String fileLocation = "src/ClassData.csv";
            // find the file with the class data.
            File CSVFileLocation = new File(fileLocation);

            // use a scanner that will read from the file.
            Scanner lineScanner = new Scanner(CSVFileLocation);

            while(lineScanner.hasNextLine()) {
                // read a line from the file.
                String nextLine = lineScanner.nextLine();

                // split the file into parts.
                String[] classInfoComponents = nextLine.split(",");

                // get the components of our vehicle.
                String classAbb = classInfoComponents[0];
                String classNumber = classInfoComponents[1];
                String teacherName = classInfoComponents[2];
                String wantedTime = classInfoComponents[3];
                String classID = classInfoComponents[5];
                String reservedForOthers = classInfoComponents[4];

                int reservedForOthersInt = Integer.parseInt(reservedForOthers);

                str.add(new classInfoObject(classAbb, classNumber, teacherName, wantedTime, reservedForOthersInt, classID));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return str;
    }
}
