package graph;

public class WeightedNode extends Node {
    int weight;

    public WeightedNode(int edge, Vertex start, Vertex end, int weight) {
        super(edge, start, end);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
