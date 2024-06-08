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
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        Double lowestHumidity = null;
        CSVRecord lowestHumidityRecord = null;
        for (CSVRecord record : parser) {
            boolean notInvalidHumidity = !record.get("Humidity").equals("N/A”");
           
            if (notInvalidHumidity && lowestHumidity == null) {
                lowestHumidity = Double.parseDouble(record.get("Humidity"));
                lowestHumidityRecord = record;
            }
            else if (notInvalidHumidity) {
                double currHumidity = Double.parseDouble(record.get("Humidity"));
                if (currHumidity < lowestHumidity) {
                    lowestHumidity = currHumidity;
                    lowestHumidityRecord = record;
                }
            }
        }
        return lowestHumidityRecord;
    }
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);

        System.out.println("Lowest Humidity " + csv.get("Humidity") + " " + csv.get("DateUTC") );
    }
    public CSVRecord lowestHumidityInManyFiles() {
        DirectoryResource dr = new DirectoryResource();
        Double lowestHumidityYet = null;
        CSVRecord lowestHumidityInFilesRecord = null;

        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currRecord = lowestHumidityInFile(fr.getCSVParser());
            boolean currRecordHasHumidity = currRecord != null;

            if (currRecordHasHumidity && (lowestHumidityYet == null)) {
                lowestHumidityYet = Double.parseDouble(currRecord.get("Humidity"));
                lowestHumidityInFilesRecord = currRecord;
            }
            else if (currRecordHasHumidity) {
                double currHumidity = Double.parseDouble(currRecord.get("Humidity"));
                if (currHumidity < lowestHumidityYet) {
                    lowestHumidityYet = currHumidity;
                    lowestHumidityInFilesRecord = currRecord;
                }
            }
        }
        return lowestHumidityInFilesRecord;
    }
    public void testLowestHumidityInManyFiles() {
        CSVRecord r = lowestHumidityInManyFiles();
        String humidity = r.get("Humidity");
        String date = r.get("DateUTC");
        System.out.println("Lowest Humidity was " + humidity + " " + "at " + date);
    }
    public double averageTemperatureInFile(CSVParser parser) {
        double sumOfTemps = 0.0;
        int noOfTemps = 0;
        for (CSVRecord record : parser) {
            sumOfTemps += Double.parseDouble(record.get("TemperatureF"));
            noOfTemps += 1;
        }
        return sumOfTemps/noOfTemps;
    }
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double average = averageTemperatureInFile(parser);

        System.out.println("Average temperature in file is " + average);
    }
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        double sumOfHighHumidityTemps = 0.0;
        int noOfHighHumidityTemps = 0;

        for (CSVRecord record : parser) {
            double currHumidity = Double.parseDouble(record.get("Humidity"));
            if (currHumidity >= value) {
                sumOfHighHumidityTemps += Double.parseDouble(record.get("TemperatureF"));
                noOfHighHumidityTemps += 1;
            }
        }
        return sumOfHighHumidityTemps/noOfHighHumidityTemps;
    }
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        Double average = averageTemperatureWithHighHumidityInFile(parser,80);

        if (average.isNaN()) {
            System.out.println("No temperatures with that humidity");
        }
        else {
            System.out.println("Average temp when high Humidity is " + average);
        }
        
    }
    public static void main(String[] args) {
        WeatherParser w = new WeatherParser();
        //w.testColdestHourInFile();
        //w.testFileWithColdestTemperature();
        //w.testLowestHumidityInFile();
        //w.testLowestHumidityInManyFiles();
        //w.testAverageTemperatureInFile();
        w.testAverageTemperatureWithHighHumidityInFile();
    }
}
