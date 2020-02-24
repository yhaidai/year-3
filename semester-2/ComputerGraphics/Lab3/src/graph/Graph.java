package graph;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.util.*;

public class Graph<N extends Node> extends Path {
    List<N> nodes;

    public Graph(List<N> nodes) {
        this.nodes = nodes;
        draw();
    }

    public N getNode(int edge) {
        return nodes.get(edge);
    }

    public N getNode(Vertex start, Vertex end) {
        for (N node : nodes) {
            if (node.getStart().equals(start) && node.getEnd().equals(end)) {
                return node;
            }
        }

        return null;
    }

    public List<N> getNodes() {
        return nodes;
    }

    public List<N> sortedNodesStartingAt(Vertex start) {
        ArrayList<N> result = new ArrayList<>();

        for (N node : nodes) {
            if (start.equals(node.getStart())) {
                ListIterator<N> it = result.listIterator(0);
                while (it.hasNext() && node.getEnd().getX() > result.get(it.nextIndex()).getEnd().getX()) {
                    it.next();
                }
                it.add(node);
            }
        }

        return result;
    }

    public List<Vertex> adjacentVerticesOut(Vertex vertex) {
        ArrayList<Vertex> vertices = new ArrayList<>();

        for (N node : nodes) {
            if (node.getStart().equals(vertex)) {
                vertices.add(node.getEnd());
            }
        }

        return vertices;
    }

    public List<Vertex> adjacentVerticesIn(Vertex vertex) {
        ArrayList<Vertex> vertices = new ArrayList<>();

        for (N node : nodes) {
            if (node.getEnd().equals(vertex)) {
                vertices.add(node.getStart());
            }
        }

        return vertices;
    }

    public void draw() {
        for (N node : nodes) {
            this.getElements().add(new MoveTo(node.getStart().getX(), node.getStart().getY()));
            this.getElements().add(new LineTo(node.getEnd().getX(), node.getEnd().getY()));
        }
    }

    public Set<Vertex> vertices() {
        HashSet<Vertex> vertices = new HashSet<>();

        for (N node : nodes) {
            Vertex start = node.getStart();
            Vertex end = node.getEnd();
            if (!vertices.contains(start)) {
                vertices.add(start);
            }
            if (!vertices.contains(end)) {
                vertices.add(end);
            }
        }

        return vertices;
    }
}
