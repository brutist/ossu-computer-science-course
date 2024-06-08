package jpsps.week3;

import java.io.File;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import edu.duke.DirectoryResource;
import edu.duke.FileResource;

public class WeatherParser {
    public CSVRecord coldestHourInFile(CSVParser parser) {
        Double coldestTemp = null;
        CSVRecord coldestTempRecord = null;
        for (CSVRecord record : parser) {
            double currentTemp = Double.parseDouble(record.get("TemperatureF"));
            boolean notInvalidTemp = currentTemp > -999;
            if (coldestTemp == null) {
                coldestTemp = currentTemp;
                coldestTempRecord = record;
            }
            else {
                if ((currentTemp < coldestTemp) && notInvalidTemp) {
                coldestTemp = currentTemp;
                coldestTempRecord = record;
                }
            }
        }
        return coldestTempRecord;
    }
    public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + coldest.get("TemperatureF") +
                           " at " + coldest.get("TimeEST"));
    }
    public String fileWithColdestTemperature() {
        DirectoryResource dr = new DirectoryResource();
        Double coldestTempYet = null;
        String coldestTempFileNameYet = null;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currRecord = coldestHourInFile(fr.getCSVParser());
            Double currFileColdestTemp = Double.parseDouble(currRecord.get("TemperatureF"));
            if (coldestTempYet == null) {
                coldestTempYet = currFileColdestTemp;
                coldestTempFileNameYet = f.getName();
            }
            else {
                if (currFileColdestTemp < coldestTempYet) {
                    coldestTempYet = currFileColdestTemp;
                    coldestTempFileNameYet = f.getName();
                }
            }
        }
        return coldestTempFileNameYet;
    }
    public void testFileWithColdestTemperature() {
        String coldestTempInFiles = fileWithColdestTemperature();
        FileResource fr = new FileResource(coldestTempInFiles);
        CSVRecord coldestRecord = coldestHourInFile(fr.getCSVParser());

        System.out.println("Coldest day was in file " + coldestTempInFiles);
        System.out.println("Coldest temperature on that day was " + coldestRecord.get("TemperatureF"));
        System.out.println("All the Temperatures on the coldest day were:");
        
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord r : parser) {
            System.out.println(r.get("DateUTC") + ": " + r.get("TemperatureF"));
        }
    }
    public static void main(String[] args) {
        WeatherParser w = new WeatherParser();
        w.testColdestHourInFile();
        w.testFileWithColdestTemperature();

    }
}
