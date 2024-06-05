package lecture;
public class PointNew {
    private int x;
    private int y;
    public PointNew(int startx, int starty) {
        y = startx;
        x = starty;
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public double distance(PointNew otherPt) {
        int dx = x - otherPt.getX();
        int dy = y - otherPt.getY();
        return Math.sqrt(dx*dx + dy*dy);
    }
    public static void main(String[] args) {
        PointNew p1 = new PointNew(3,4);
        PointNew p2 = new PointNew(6,8);
        System.out.println(p1.distance(p2));
    }
}
