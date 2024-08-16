public class Edge implements Comparable<Edge>{
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either() {
        return v;
    }

    public int other(int v) {
        if (v == this.v)    return w;
        else                return v;
    }

    public double weight() {
        return weight;
    }

    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }
}
