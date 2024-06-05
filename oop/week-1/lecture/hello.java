package lecture;
import edu.duke.FileResource;

public class hello {
    public void runHello () {
        FileResource res = new FileResource("hello_unicode.txt");
        for (String line : res.lines()) {
            System.out.println(line);
        }
    }

    public static void main(String[] args){
        hello hw = new hello();
        hw.runHello();
    }
}