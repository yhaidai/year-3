package graph;

import java.util.ArrayList;
import java.util.List;

public class StripGraph extends Graph<Node> {
    List<Vertex> vertices;

    public StripGraph(List nodes) {
        super(nodes);
        vertices = new ArrayList<>(this.vertices());
        vertices.sort(new Vertex.YAxisComparator());
    }

    public void locate(Vertex point) {
        printVertices();

        // find stripe
        if (point.getY() > vertices.get(0).getY()) {
            System.out.println("Point lies under the graph");
        } else if (point.getY() < vertices.get(vertices.size() - 1).getY()) {
            System.out.println("Point lies above the graph");
        } else {
            int stripIndex = binarySearchStrip(point, 0, vertices.size() - 1);

            // find trapeze
            List<Node> edges = nodesContainingYAxisProjection(point);
            edges.sort(new Node.XAxisComparator());

            printStripEdges(edges, stripIndex);

            if (point.getX() < Math.min(edges.get(0).getStart().getX(), edges.get(0).getEnd().getX())) {
                System.out.println("Point lies to the left from the graph");
            } else if (point.getX() > Math.max(edges.get(edges.size() - 1).getStart().getX(),
                    edges.get(edges.size() - 1).getEnd().getX())) {
                System.out.println("Point lies to the right from the graph");
            } else {
                int trapezeIndex = binarySearchTrapeze(point, edges, 0, edges.size() - 1);
                printPointLocation(edges, stripIndex, trapezeIndex);
            }
        }
    }

    private int binarySearchStrip(Vertex point, int left, int right) {
        int mid = (left + right) / 2;

        if (left == right - 1) {
            return left;
        }

        if (point.getY() > vertices.get(mid).getY()) {
            return binarySearchStrip(point, left, mid);
        } else {
            return binarySearchStrip(point, mid, right);
        }
    }

    private List<Node> nodesContainingYAxisProjection(Vertex point) {
        ArrayList<Node> result = new ArrayList();

        for (Node node : nodes) {
            if (node.getStart().getY() < point.getY() && point.getY() <= node.getEnd().getY() ||
                    node.getStart().getY() >= point.getY() && point.getY() > node.getEnd().getY()) {
                result.add(node);
            }
        }

        return result;
    }

    private int binarySearchTrapeze(Vertex point, List<Node> edges, int left, int right) {
        int mid = (left + right) / 2;

        if (left == right - 1) {
            return left;
        }

        if (point.getX() < edges.get(mid).getStart().getX()) {
            return binarySearchTrapeze(point, edges, left, mid);
        } else {
            return binarySearchTrapeze(point, edges, mid, right);
        }
    }

    private void printVertices() {
        System.out.println("Vertices: ");
        for (Vertex v : vertices) {
            System.out.println(v.getY());
        }
    }

    private void printStripEdges(List<Node> edges, int stripIndex) {
        System.out.println("Strip: (" + vertices.get(stripIndex).getNumber() +
                ", " + vertices.get(stripIndex + 1).getNumber() + ")");
        System.out.println("Edges: ");
        for (Node edge : edges) {
            System.out.println(edge.getStart().getNumber() + "--->" + edge.getEnd().getNumber());
        }
    }

    private void printPointLocation(List<Node> edges, int stripIndex, int trapezeIndex) {
        double down = vertices.get(stripIndex + 1).getY();
        double up = vertices.get(stripIndex).getY();
        double left = edges.get(trapezeIndex).getStart().getX();
        double right = edges.get(trapezeIndex + 1).getStart().getX();
        System.out.println("Point lies in between: ");
        System.out.println("X: " + left + " --- " + right + "(trapeze #" + trapezeIndex + ")");
        System.out.println("Y: " + down + " --- " + up + "(strip #" + stripIndex + ")");
    }
}
