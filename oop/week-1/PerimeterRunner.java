import edu.duke.FileResource;
import edu.duke.*;

public class PerimeterRunner {

    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0;
        // Start with prevPT = last point
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            double currDist = prevPt.distance(currPt);
            totalPerim += currDist;
            prevPt = currPt;
        }
        return totalPerim;   
    }
    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        System.out.println("perimeter = " + length);
    }
    public static void main(String[] args) {
        PerimeterRunner pr = new PerimeterRunner();
        pr.testPerimeter();
    }
}
