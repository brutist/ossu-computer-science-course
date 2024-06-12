import java.util.*;
import edu.duke.*;

public class LogAnalyzer {
    private ArrayList<LogEntry> records;

    public LogAnalyzer() {
        records = new ArrayList<LogEntry>();
    }
    public void readFile(String filename) {
        records.clear();
        FileResource fr = new FileResource(filename);
        for (String line : fr.lines()) {
            records.add(WebLogParser.parseEntry(line));
        }
    }
    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }
    public int countUniqueIPs() {
        int ipCount = 0;
        ArrayList<String> ipsFound = new ArrayList<String>();
        for (LogEntry e : records) {
            String currIP = e.getIpAddress();
            if (!ipsFound.contains(currIP)) {
                ipsFound.add(currIP);
                ipCount++;
            }
        }
        return ipCount;
    }
    public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
        ArrayList<String> ipVisitsOnDay = new ArrayList<String>();
        for (LogEntry e : records) {
            String accessDate = e.getAccessTime().toString();
            String[] dayInfo = accessDate.split(" ");
            String currDay = dayInfo[1] + " " + dayInfo[2];
            String currIP = e.getIpAddress();
            if (someday.equals(currDay) && (!ipVisitsOnDay.contains(currIP))) {
                ipVisitsOnDay.add(currIP);
            }
        }
        return ipVisitsOnDay;
    }
    public void printAllHigherThanNum(int num) {
        for (LogEntry e : records) {
            int currStatus = e.getStatusCode();
            if (currStatus > num) {
                System.out.println(e);
            }
        }
    }
    public int countUniqueIPsInRange(int low, int high) {
        int uniqueIpCount = 0;
        ArrayList<String> ipFound = new ArrayList<String>();
        for (int i = 0; i < records.size(); i++) {
            int currStatusCode = records.get(i).getStatusCode();
            String currIp = records.get(i).getIpAddress();
            boolean withinRange = low <= currStatusCode && currStatusCode <= high;
            if (withinRange && (!ipFound.contains(currIp))) {
                uniqueIpCount++;
                ipFound.add(currIp);
            }
        }
        return uniqueIpCount;
    }
}
