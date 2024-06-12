import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("logs/short-test_log");
        analyzer.printAll();
    }
    public void testCountUniqueIps() {
        String[] testFiles = new String[] {"logs/short-test_log",
                                           "logs/weblog-short_log"};
        int[] answers = new int[] {4,4};
        boolean testResult = true;
        LogAnalyzer analyzer = new LogAnalyzer();
        int i = 0;
        for (; i < testFiles.length; i++) {
            analyzer.readFile(testFiles[i]);
            int currResult = analyzer.countUniqueIPs();
            int currAnswer = answers[i];
            if (currAnswer != currResult) {
                System.out.printf("countUniqueIps Test %d Failed \n",i+1);
                System.out.printf("Result should be %s but got %s instead \n", currAnswer, currResult);
                testResult = false;
            }
        }
        if (testResult) {
            System.out.printf("countUniqueIps %d tests successful \n", i);
        }
    }
    public void testPrintAllHigherThanNum() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("logs/weblog1_log");
        analyzer.printAllHigherThanNum(400);
    }
    public void testUniqueIPVisitsOnDay() {
        String[][] answers = new String[][] {new String[] {"84.133.195.161","177.4.40.87"},
                                             new String[] {"84.189.158.117", "61.15.121.171","177.4.40.87"}};
        String[] testDates = new String[] {"Sep 14", "Sep 30"};
        int i = 0;
        boolean testResult = true;
        for (; i < testDates.length; i++) {
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.readFile("logs/weblog-short_log");
            ArrayList<String> currResult = analyzer.uniqueIPVisitsOnDay(testDates[i]);
            ArrayList<String> currAnswer = new ArrayList<>(Arrays.asList(answers[i]));
            if (!currResult.equals(currAnswer)) {
                System.out.printf("Test %d failed\n", i+1);
                testResult = false;
            }
        }
        if (testResult) {
            System.out.printf("UniqueIPVisitsOnDay %d tests successful \n", i);
        }
    }
    public void testUniqueIPVisitsOnDay(String date) {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("logs/weblog1_log");
        ArrayList<String> currResult = analyzer.uniqueIPVisitsOnDay(date);
        System.out.printf("No. of unique visits on the day: %d \n", currResult.size());

    }
    public void testCountUniqueIPsInRange() {
        int[] input1 = new int[] {200,300};
        int[] input2 = new int[] {299,399};
        int[] answers = new int[] {4,2};

        int i = 0;
        boolean testResult = true;
        for (; i < input1.length; i++) {
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.readFile("logs/short-test_log");
            int currResult = analyzer.countUniqueIPsInRange(input1[i],input2[i]);
            if (currResult != answers[i]) {
                System.out.printf("CountUniqueIPsInRange Test %d failed\n", i+1);
                testResult = false;
            } 
        }
        if (testResult) {
            System.out.printf("CountUniqueIPsInRange %d tests successful \n", i);
        }
    }
    public void testCountUniqueIPsInRange(int low, int high) {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("logs/weblog1_log");
        int currResult = analyzer.countUniqueIPsInRange(low,high);

        System.out.printf("No. of Unique Ip in Range: %d\n", currResult);
    }  
    public static void main(String[] args) {
        Tester t = new Tester();
        //t.testLogEntry();
        //t.testLogAnalyzer();
        //t.testCountUniqueIps();
        //t.testPrintAllHigherThanNum();
        //t.testUniqueIPVisitsOnDay();
        //t.testUniqueIPVisitsOnDay("Mar 17");
        //t.testCountUniqueIPsInRange();
        t.testCountUniqueIPsInRange(200,299);
    }
}
