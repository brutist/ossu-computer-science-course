package jpsps.week1.perimeterQuiz;
import edu.duke.*;
import java.io.File;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        // Put code here
        int totalPoints = 0;
        for (Point pt : s.getPoints()) {
            totalPoints += 1;
        }
        return totalPoints;
    }

    public double getAverageLength(Shape s) {
        // Put code here
        double perimeter = getPerimeter(s);
        int vertices = getNumPoints(s);
        return perimeter/vertices;
    }

    public double getLargestSide(Shape s) {
        // Put code here
        Point prevPt = s.getLastPoint();
        double largestSide = 0;
        for (Point pt : s.getPoints()) {
            double currSide = prevPt.distance(pt);
            if (currSide > largestSide) {
                largestSide = currSide;
            } 
        }
        return largestSide;
    }

    public double getLargestX(Shape s) {
        // Put code here
        double largestX = 0;
        for (Point pt : s.getPoints()) {
            double currX = pt.getX();
            if (currX > largestX) {
                largestX = currX;
            }
        }
        return largestX;
    }

    public double getLargestPerimeterMultipleFiles() {
        // Put code here
        double largestPerim = 0;
        DirectoryResource files = new DirectoryResource();
        for (File f : files.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double currPerim = getPerimeter(s);
            if (currPerim > largestPerim) {
                largestPerim = currPerim;
            }
        }
        return largestPerim;
    }

    public String getFileWithLargestPerimeter() {
        // Put code here
        String largestPerimFileName = "";
        double largestPerim = 0;
        DirectoryResource files = new DirectoryResource();
        for (File f : files.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double currPerim = getPerimeter(s);
            if (currPerim > largestPerim) {
                largestPerim = currPerim;
                largestPerimFileName = f.getName();
            }
        }
        return largestPerimFileName;
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        int vertices = getNumPoints(s);
        double averageLength = getAverageLength(s);
        double largestSide = getLargestSide(s);
        double largestX = getLargestX(s);
        System.out.println("perimeter = " + length);
        System.out.println("no. of points = " + vertices);
        System.out.println("average size length = " + averageLength);
        System.out.println("largest side = " + largestSide);
        System.out.println("largest X = " + largestX);
    }
    
    public void testPerimeterMultipleFiles() {
        // Put code here
        double largestPerim = getLargestPerimeterMultipleFiles();
        System.out.println("largest perimeter = " + largestPerim);
    }

    public void testFileWithLargestPerimeter() {
        // Put code here
        String largestPerimFileName = getFileWithLargestPerimeter();
        System.out.println("largest perimeter filename = " + largestPerimFileName);
    }

    // This method creates a `le that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        pr.testFileWithLargestPerimeter();
    }
}
