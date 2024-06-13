import java.util.*;

public class Tester {
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
        String[] testFiles = new String[] { "logs/short-test_log",
                "logs/weblog-short_log" };
        int[] answers = new int[] { 4, 4 };
        boolean testResult = true;
        LogAnalyzer analyzer = new LogAnalyzer();
        int i = 0;
        for (; i < testFiles.length; i++) {
            analyzer.readFile(testFiles[i]);
            int currResult = analyzer.countUniqueIPs();
            int currAnswer = answers[i];
            if (currAnswer != currResult) {
                System.out.printf("countUniqueIps Test %d Failed \n", i + 1);
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
        String[][] answers = new String[][] { new String[] { "84.133.195.161",
                "177.4.40.87" },
                new String[] { "84.189.158.117",
                        "61.15.121.171",
                        "177.4.40.87" } };
        String[] testDates = new String[] { "Sep 14", "Sep 30" };
        int i = 0;
        boolean testResult = true;
        for (; i < testDates.length; i++) {
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.readFile("logs/weblog-short_log");
            ArrayList<String> currResult = analyzer.uniqueIPVisitsOnDay(testDates[i]);
            ArrayList<String> currAnswer = new ArrayList<>(Arrays.asList(answers[i]));
            if (!currResult.equals(currAnswer)) {
                System.out.printf("Test %d failed\n", i + 1);
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
        int[] input1 = new int[] { 200, 300 };
        int[] input2 = new int[] { 299, 399 };
        int[] answers = new int[] { 4, 2 };

        int i = 0;
        boolean testResult = true;
        for (; i < input1.length; i++) {
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.readFile("logs/short-test_log");
            int currResult = analyzer.countUniqueIPsInRange(input1[i], input2[i]);
            if (currResult != answers[i]) {
                System.out.printf("CountUniqueIPsInRange Test %d failed\n", i + 1);
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
        int currResult = analyzer.countUniqueIPsInRange(low, high);

        System.out.printf("No. of Unique Ip in Range: %d\n", currResult);

    }

    // simular test files for some functions
    private String[] TESTFILES = new String[] { "logs/short-test_log", "logs/weblog-short_log" };

    public void testCountVisitsPerIP() {
        HashMap<String, Integer> ans1 = new HashMap<String, Integer>();
        ans1.put("110.76.104.12", 1);
        ans1.put("152.3.135.44", 3);
        ans1.put("157.55.39.203", 1);
        ans1.put("152.3.135.63", 2);
        HashMap<String, Integer> ans2 = new HashMap<String, Integer>();
        ans2.put("84.189.158.117", 2);
        ans2.put("61.15.121.171", 3);
        ans2.put("84.133.195.161", 3);
        ans2.put("177.4.40.87", 4);
        ArrayList<HashMap<String, Integer>> answers = new ArrayList<HashMap<String, Integer>>();
        answers.add(ans1);
        answers.add(ans2);

        int i = 0;
        boolean testResult = true;
        for (; i < answers.size(); i++) {
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.readFile(TESTFILES[i]);
            HashMap<String, Integer> currResult = analyzer.countVisitsPerIP();
            if (!currResult.equals(answers.get(i))) {
                System.out.printf("testCountVisitsPerIP Test %d failed\n", i + 1);
                testResult = false;
            }
        }
        if (testResult) {
            System.out.printf("testCountVisitsPerIP %d tests successful \n", i);
        }
    }

    public void testMostNumberVisitsByIP() {
        int[] answers = new int[] { 3, 4 };

        int i = 0;
        boolean testResult = true;
        for (; i < answers.length; i++) {
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.readFile(TESTFILES[i]);
            int currResult = analyzer.mostNumberVisitsByIP(analyzer.countVisitsPerIP());
            if (currResult != answers[i]) {
                System.out.printf("testMostNumberVisitsByIP Test %d failed\n", i + 1);
                testResult = false;
            }
        }
        if (testResult) {
            System.out.printf("testMostNumberVisitsByIP %d tests successful \n", i);
        }
    }

    public void testIPsMostVisits() {
        String[] TESTFILES = new String[] { "logs/short-test_log",
                "logs/weblog-short_log",
                "logs/weblog3-short_log" };
        ArrayList<String> ans1 = new ArrayList<String>();
        ans1.add("152.3.135.44");
        ArrayList<String> ans2 = new ArrayList<String>();
        ans2.add("177.4.40.87");
        ArrayList<String> ans3 = new ArrayList<String>();
        ans3.add("61.15.121.171");
        ans3.add("84.133.195.161");
        ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();
        answers.add(ans1);
        answers.add(ans2);
        answers.add(ans3);

        int i = 0;
        boolean testResult = true;
        for (; i < answers.size(); i++) {
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.readFile(TESTFILES[i]);
            ArrayList<String> currResult = analyzer.iPsMostVisits(analyzer.countVisitsPerIP());
            if (!currResult.equals(answers.get(i))) {
                System.out.printf("testIPsMostVisits Test %d failed\n", i + 1);
                testResult = false;
            }
        }
        if (testResult) {
            System.out.printf("testIPsMostVisits %d tests successful \n", i);
        }
    }

    public void testIPsForDays() {
        ArrayList<String> ip1 = new ArrayList<String>();
        ip1.add("110.76.104.12");
        ip1.add("152.3.135.44");
        ip1.add("152.3.135.44");
        ip1.add("152.3.135.44");
        ip1.add("157.55.39.203");
        ip1.add("152.3.135.63");
        ip1.add("152.3.135.63");
        HashMap<String, ArrayList<String>> ans1 = new HashMap<String, ArrayList<String>>();
        ans1.put("Sep 30", ip1);

        ArrayList<String> ip2 = new ArrayList<String>();
        ip2.add("84.189.158.117");
        ip2.add("61.15.121.171");
        ip2.add("84.133.195.161");
        ip2.add("84.133.195.161");
        ip2.add("177.4.40.87");
        ArrayList<String> ip2a = new ArrayList<String>();
        ip2a.add("84.189.158.117");
        ip2a.add("61.15.121.171");
        ip2a.add("61.15.121.171");
        ip2a.add("177.4.40.87");
        ip2a.add("177.4.40.87");

        ArrayList<String> ip2b = new ArrayList<String>();
        ip2b.add("84.133.195.161");
        ip2b.add("177.4.40.87");

        HashMap<String, ArrayList<String>> ans2 = new HashMap<String, ArrayList<String>>();
        ans2.put("Sep 21", ip2);
        ans2.put("Sep 30", ip2a);
        ans2.put("Sep 14", ip2b);

        ArrayList<HashMap<String, ArrayList<String>>> answers = new ArrayList<HashMap<String, ArrayList<String>>>();
        answers.add(ans1);
        answers.add(ans2);

        int i = 0;
        boolean testResult = true;
        for (; i < answers.size(); i++) {
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.readFile(TESTFILES[i]);
            HashMap<String, ArrayList<String>> currResult = analyzer.iPsForDays();
            if (!currResult.equals(answers.get(i))) {
                System.out.printf("testIPsForDays Test %d failed\n", i + 1);
                testResult = false;
            }
        }
        if (testResult) {
            System.out.printf("testIPsForDays %d tests successful \n", i);
        }
    }

    public void testDayWithMostIPVisits() {
        String[] TESTFILES = new String[] {"logs/weblog3-short_log"};
        String[] answers = new String[] {"Sep 30"};

        int i = 0;
        boolean testResult = true;
        for (; i < answers.length; i++) {
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.readFile(TESTFILES[i]);
            HashMap<String, ArrayList<String>> currInput = analyzer.iPsForDays();
            String currResult = analyzer.dayWithMostIPVisits(currInput);
            if (!currResult.equals(answers[i])) {
                System.out.printf("testDayWithMostIPVisits Test %d failed\n", i + 1);
                testResult = false;
            }
        }
        if (testResult) {
            System.out.printf("testDayWithMostIPVisits %d tests successful \n", i);
        }
    }
    
    public void testIPswithMostVisitsOnDay() {
        String[] TESTFILES = new String[] {"logs/weblog3-short_log"};
        String[] DAYS = new String[] {"Sep 30"};
        ArrayList<String> ans1 = new ArrayList<String>();
        ans1.add("61.15.121.171");
        ans1.add("177.4.40.87");
        ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();
        answers.add(ans1);

        int i = 0;
        boolean testResult = true;
        for (; i < answers.size(); i++) {
            LogAnalyzer analyzer = new LogAnalyzer();
            analyzer.readFile(TESTFILES[i]);
            HashMap<String, ArrayList<String>> currInput1 = analyzer.iPsForDays();
            String currInput2 = DAYS[i];
            ArrayList<String> currResult = analyzer.iPsWithMostVisitsOnDay(currInput1,currInput2);
            if (!currResult.equals(answers.get(i))) {
                System.out.printf("testIPswithMostVisitsOnDay Test %d failed\n", i + 1);
                testResult = false;
            }
        }
        if (testResult) {
            System.out.printf("testIPswithMostVisitsOnDay %d tests successful \n", i);
        }

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
        //t.testCountUniqueIPsInRange(200,299);
        //t.testCountVisitsPerIP();
        //t.testMostNumberVisitsByIP();
        //t.testIPsMostVisits();
        //t.testIPsForDays();
        //t.testDayWithMostIPVisits();
        //t.testIPswithMostVisitsOnDay();
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("logs/weblog1_log");
        //HashMap<String, Integer> map = analyzer.countVisitsPerIP();
        //System.out.printf("Most visits IP: %s \n", analyzer.iPsMostVisits(map));

        HashMap<String, ArrayList<String>> ipPerDayMap = analyzer.iPsForDays();
        //System.out.printf("Day with most IP visits: %s \n", analyzer.dayWithMostIPVisits(ipPerDayMap));
        System.out.printf("Day with most IP visits: %s \n", analyzer.iPsWithMostVisitsOnDay(ipPerDayMap, "Mar 17"));
    
    }
}
