import edu.princeton.cs.algs4.StdDraw;
import java.util.Random;

public class all {
    private double rx, ry;              // position
    private double vx, vy;              // velocity
    private final double radius;  // radius

    public Ball() {
        Random rand = new Random();
        rx = rand.nextDouble();
        ry = rand.nextDouble();
        vx = rand.nextDouble();
        vy = rand.nextDouble();
        radius = rand.nextDouble();
    }

    public Ball(double dx, double dy, double sx, double sy, double r) {
        rx = dx;
        ry = dy;
        vx = sx;
        vy = sy;
        radius = r;
    }

    public void move(double dt) {
        if ((rx + vx*dt < radius) || (rx + vx*dt > 1.0 - radius))   { vx = -vx; }
        if ((ry + vy*dt < radius) || (ry + vy*dt > 1.0 - radius))   { vy = -vy; }
        rx = rx + vx*dt;
        ry = ry + vy*dt;
    }

    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }
}
