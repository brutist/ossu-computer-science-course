package jpsps.week2.Debugging;

public class Part2 {
    public void findAbc(String input){
        int index = input.indexOf("abc");
        while (true){
            if (index == -1 || index >= input.length() - 3){
                if (index == -1) {
                    System.out.println("index " + index);
                }
                else {
                    System.out.println("index after updating " + index);
                }
                break;
            }
            // System.out.println(index);
            String found = input.substring(index+1, index+4);
            System.out.println(found);
            index = input.indexOf("abc",index+4);
        }
    }
 
    public void test() {
        //findAbc("abcd");
        findAbc("abcabcabcabca");
    }
    public static void main(String[] args) {
        Part2 test = new Part2();
        test.test();
    }
}
