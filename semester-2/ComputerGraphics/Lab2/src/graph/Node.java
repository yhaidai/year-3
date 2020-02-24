package graph;

public class Node {
    int edge;
    Vertex start;
    Vertex end;

    public Node(int edge, Vertex start, Vertex end) {
        this.edge = edge;
        this.start = start;
        this.end = end;

    }
    public int getEdge() {
        return edge;
    }

    public void setEdge(int edge) {
        this.edge = edge;
    }

    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "" + edge;
    }
}
