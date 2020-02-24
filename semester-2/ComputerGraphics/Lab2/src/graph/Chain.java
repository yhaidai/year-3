package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chain<T extends Node> {
    List<T> nodes;

    public Chain() {
        this.nodes = new ArrayList<>();
    }

    public List<T> getNodes() {
        return nodes;
    }

    public void add(T e) {
        nodes.add(e);
    }

    @Override
    public String toString() {
        String result = "{";

        for (T node : nodes) {
            result += node.toString() + ", ";
        }

        return result.substring(0, result.length() - 2) + "}";
    }
}
