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
    public HashMap<String, Integer> countVisitsPerIP() {
        HashMap<String, Integer> visitPerIP = new HashMap<String, Integer>();

        for (LogEntry e : records) {
            String currIP = e.getIpAddress();
            if (visitPerIP.containsKey(currIP)) {
                int ipVisits = visitPerIP.get(currIP);
                visitPerIP.put(currIP,ipVisits+1);
            }
            else {
                visitPerIP.put(currIP,1);
            }
        }
        return visitPerIP;
    }
    public int mostNumberVisitsByIP(HashMap<String, Integer> ipVisitRecord) {
        int maxVisits = 0;

        for (String key : ipVisitRecord.keySet()) {
            int currIPVisits = ipVisitRecord.get(key);
            if (currIPVisits > maxVisits) {
                maxVisits = currIPVisits;
            }
        }
        return maxVisits;
    }
    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> ipVisitRecord) {
        ArrayList<String> ipsWithMaxVisit = new ArrayList<String>();
        int maxVisits = mostNumberVisitsByIP(ipVisitRecord);
        
        for (String ip: ipVisitRecord.keySet()) {
            int currVisits = ipVisitRecord.get(ip);
            if (currVisits == maxVisits && (!ipVisitRecord.containsKey(ip))) {
                ipsWithMaxVisit.add(ip);
            }
        }
        return ipsWithMaxVisit;
    }
    public HashMap<String, ArrayList<String>> iPsForDays() {
        HashMap<String, ArrayList<String>> ipDayVisitsMap = new HashMap<String, ArrayList<String>>();
        for (LogEntry e : records) {
            String accessDate = e.getAccessTime().toString();
            String[] dayInfo = accessDate.split(" ");
            String currDay = dayInfo[1] + " " + dayInfo[2];
            if (ipDayVisitsMap.containsKey(currDay)) {
                ArrayList<String> currDayIPVisits = ipDayVisitsMap.get(currDay);
                currDayIPVisits.add(e.getIpAddress());
                ipDayVisitsMap.put(currDay, currDayIPVisits);
            }
            else {
                ArrayList<String> currDayIPVisits = new ArrayList<String>();
                currDayIPVisits.add(e.getIpAddress());
                ipDayVisitsMap.put(currDay, currDayIPVisits);
            }
        }
        return ipDayVisitsMap;
    }
    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> ipDaysVisitsMap) {
        int maxVisits = 0;
        String maxVisitedIP = null;

        for (String key : ipDaysVisitsMap.keySet()) {
            int currVisits = ipDaysVisitsMap.get(key).size();
            if (currVisits > maxVisits) {
                maxVisits = currVisits;
                maxVisitedIP = key;
            }
        }
        return maxVisitedIP;
    }
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> ipDaysVisitsMap, String day) {
        HashMap<String, Integer> visitsPerIP = new HashMap<String, Integer>();
        ArrayList<String> visitorIPs = ipDaysVisitsMap.get(day);
        for (String ip : visitorIPs) {
            if (visitsPerIP.containsKey(ip)) {
                int ipTimesVisited = visitsPerIP.get(ip);
                visitsPerIP.put(ip,ipTimesVisited+1);
            }
            else {
                visitsPerIP.put(ip,1);
            }
        }

        int maxVisit = mostNumberVisitsByIP(visitsPerIP);
        ArrayList<String> maxVisitsIP = new ArrayList<String>();
        for (String ip : visitsPerIP.keySet()) {
            int keyVisits = visitsPerIP.get(ip);
            if (keyVisits == maxVisit) {
                maxVisitsIP.add(ip);
            }
        }
        return maxVisitsIP;
    }
}
