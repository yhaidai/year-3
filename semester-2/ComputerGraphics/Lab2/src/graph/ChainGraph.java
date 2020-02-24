package graph;

import java.util.*;

public class ChainGraph extends Graph<WeightedNode> {
    List<Chain<WeightedNode>> chains;

    public ChainGraph(List<WeightedNode> nodes) {
        super(nodes);
        balance();
        buildChains();

        for (Chain chain : chains) {
            System.out.println(chain);
        }
    }

    public void locate(Vertex point) {
        binarySearch(point, 0, chains.size() - 1);
    }

    private int weightIn(Vertex vertex) {
        int weight = 0;

        for (WeightedNode node : nodes) {
            if (node.getEnd().equals(vertex)) {
                weight += node.getWeight();
            }
        }

        return weight;
    }

    private int weightOut(Vertex vertex) {
        int weight = 0;

        for (WeightedNode node : nodes) {
            if (node.getStart().equals(vertex)) {
                weight += node.getWeight();
            }
        }

        return weight;
    }

    private Vertex leftmostVertex(List<Vertex> vertices) {
        if (vertices.size() == 0) {
            return null;
        }

        Vertex leftmost = vertices.get(0);
        for (Vertex v : vertices) {
            if (v.getX() < leftmost.getX()) {
                leftmost = v;
            }
        }

        return leftmost;
    }

    private WeightedNode leftmostNodeOut(Vertex start) {
        List<Vertex> adjVerticesOut = this.adjacentVerticesOut(start);
        Vertex leftmost = leftmostVertex(adjVerticesOut);

        return this.getNode(start, leftmost);
    }

    private WeightedNode leftmostNodeIn(Vertex end) {
        List<Vertex> adjVerticesIn = this.adjacentVerticesIn(end);
        Vertex leftmost = leftmostVertex(adjVerticesIn);

        return this.getNode(leftmost, end);
    }

    private List<Vertex> sortedVertices() {
        Set<Vertex> vertexSet = this.vertices();
        List<Vertex> vertexList = new ArrayList<>(vertexSet);

        vertexList.sort(new Vertex.NumberComparator());
        return vertexList;
    }

    private void balance() {
        List<Vertex> vertices = sortedVertices();
        vertices.remove(0);
        vertices.remove(vertices.size() - 1);

        for (WeightedNode node : nodes) {
            node.setWeight(1);
        }

        ListIterator<Vertex> it = vertices.listIterator(0);
        while (it.hasNext()) {
            Vertex currentVertex = it.next();
            WeightedNode leftmostOut = leftmostNodeOut(currentVertex);

            int wIn = weightIn(currentVertex);
            int wOut = weightOut(currentVertex);
            if (wIn > wOut) {
                leftmostOut.setWeight(wIn - wOut + 1);
            }
        }

        it = vertices.listIterator(vertices.size());
        while (it.hasPrevious()) {
            Vertex currentVertex = it.previous();
            WeightedNode leftmostIn = leftmostNodeIn(currentVertex);
            int wIn = weightIn(currentVertex);
            int wOut = weightOut(currentVertex);
            if (wIn < wOut) {
                leftmostIn.setWeight(wOut - wIn + leftmostIn.getWeight());
            }
        }
    }

    private WeightedNode nextPositiveWeightNodeClockwise(Vertex start) {
        List<Vertex> adjVertices = this.adjacentVerticesOut(start);
        WeightedNode leftmostNode = leftmostNodeOut(start);
        Vertex leftmostEnd = leftmostNode.getEnd();
        Vertex nextVertex = null;
        adjVertices.remove(leftmostEnd);


        for (Vertex vertex : adjVertices) {
            if (start.orientation(leftmostEnd, vertex) == -1) {
                if ((nextVertex == null || start.orientation(nextVertex, vertex) == 1) &&
                        this.getNode(start, vertex).getWeight() > 0) {
                    nextVertex = vertex;
                }
            }
        }

        return this.getNode(start, nextVertex);
    }

    private WeightedNode leftmostPositiveWeightNode(Vertex start) {
        List<Vertex> adjVerticesOut = this.adjacentVerticesOut(start);
        Vertex leftmost = leftmostVertex(adjVerticesOut);
        WeightedNode result = this.getNode(start, leftmost);

        if (result.getWeight() == 0) {
            return nextPositiveWeightNodeClockwise(start);
        }

        return result;
    }

    private void buildChains() {
        chains = new ArrayList<>();
        List<Vertex> vertices = sortedVertices();
        Vertex first = vertices.get(0);
        Vertex last = vertices.get(vertices.size() - 1);
        int count = weightOut(first);

        for (int i = 0; i < count; i++) {
            Chain<WeightedNode> chain = new Chain<>();
            Vertex current = first;

            while (!current.equals(last)) {
                WeightedNode leftmost = leftmostPositiveWeightNode(current);
                chain.add(leftmost);
                leftmost.setWeight(leftmost.getWeight() - 1);
                current = leftmost.getEnd();
            }

            chains.add(chain);
        }
    }

    private WeightedNode nodeContainingYAxisProjection(Vertex point, Chain<WeightedNode> chain) {
        WeightedNode result = null;

        for (WeightedNode node : chain.getNodes()) {
            if (node.getStart().getY() < point.getY() && point.getY() < node.getEnd().getY() ||
                    node.getStart().getY() > point.getY() && point.getY() > node.getEnd().getY()) {
                result = node;
            }
        }

        return result;
    }

    private void binarySearch(Vertex point, int left, int right) {
        int mid = (left + right) / 2;
        WeightedNode node = nodeContainingYAxisProjection(point, chains.get(mid));

        if (node == null) {
            System.out.println("Point lies above or under the graph");
            return;
        }

        if (left == right - 1) {
            WeightedNode n1 = nodeContainingYAxisProjection(point, chains.get(left));
            WeightedNode n2 = nodeContainingYAxisProjection(point, chains.get(right));
            int o1 = point.orientation(n1.getStart(), n1.getEnd());
            int o2 = point.orientation(n2.getStart(), n2.getEnd());

            if (o1 == o2 && o1 == 1) {
                System.out.println("Point lies to the left from chain " + chains.get(left));
            } else if (o1 == o2 && o1 == -1) {
                System.out.println("Point lies to the right from chain " + chains.get(right));
            } else {
                System.out.println("Point lies between the chains " +
                        chains.get(left) + " and " + chains.get(right));
            }

            return;
        }

        if (point.orientation(node.getStart(), node.getEnd()) == 1) {
            binarySearch(point, left, mid);
        } else if (point.orientation(node.getStart(), node.getEnd()) == -1) {
            binarySearch(point, mid, right);
        } else {
            System.out.println("Point lies on the chain " + chains.get(mid));
        }
    }
}
