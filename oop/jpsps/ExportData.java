package jpsps;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.duke.FileResource;

public class ExportData {
    public void tester() {
        FileResource fr = new FileResource();

        CSVParser parser = fr.getCSVParser();
        String info = countryInfo(parser, "Germany");
        System.out.println(info);
        
        parser = fr.getCSVParser();
        listExportersTwoProducts(parser, "gold", "diamonds");

        parser = fr.getCSVParser();
        int result = numberOfExporters(parser, "gold");
        if (result != 3) { System.out.println("numberOfExporters test failed"); }

        parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999");
    }
    public String countryInfo(CSVParser parser, String country) {
        for (CSVRecord record : parser) {
            String currCountry = record.get("Country");
            if (currCountry.equals(country)) {
                String exports = record.get("Exports");
                String value = record.get("Value (dollars)");

                return country + ": " + exports + ": " + value;
            }
        }
        return "NOT FOUND";
    }
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if ((exports.contains(exportItem1)) && (exports.contains(exportItem2))) {
                System.out.println(record.get("Country"));
            }
        }
    }
    public int numberOfExporters(CSVParser parser, String exportItem) {
        int result = 0;
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportItem)) {
                result += 1;
            }
        }
        return result;
    }
    public void bigExporters(CSVParser parser, String amount) {
        int amountDigits = amount.length();
        for (CSVRecord record : parser) {
            String currAmount = record.get("Value (dollars)");
            int currAmountDigits = currAmount.length();
            if (currAmountDigits > amountDigits) {
                String country = record.get("Country");
                String value = record.get("Value (dollars)");
                System.out.println(country + " " + value);
            }
        }
    }
    public static void main(String[] args) {
        ExportData e = new ExportData();
        e.tester();
    }
}
