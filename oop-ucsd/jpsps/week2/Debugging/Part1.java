package jpsps.week2.Debugging;

public class Part1 {
    public void findAbc(String input) {
        int index = input.indexOf("abc");
        while (true) {
            if (index == -1) {
                break;
            }
            if (index > input.length() - 4) {
                break;
            }
            String found = input.substring(index+1, index+4);
            System.out.println(found);
            index = input.indexOf("abc", index+4);
        }
    }
    public void test() {
        //findAbc("abcd");
        findAbc("eusabc");
    }
    public static void main(String[] args) {
        Part1 test = new Part1();
        test.test();
        
    }
}
