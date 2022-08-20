package edu.ncsu.doorbellclient;

import java.util.ArrayList;
import java.util.List;

// https://www.softwaretestinghelp.com/java-graph-tutorial/
//class to store edges of the weighted graph
class Edge {
    int src, dest, weight;
    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}
// node of adjacency list
class Node {
    int value, weight;
    Node(int value, int weight)  {
        this.value = value;
        this.weight = weight;
    }
}

// Graph class
public class Graph {
    // define adjacency list

    List<List<Node>> adj_list = new ArrayList<>();

    //Graph Constructor
    public Graph(List<Edge> edges, int graphSize)
    {
        // adjacency list memory allocation
        for (int i = 0; i < graphSize; i++)
            adj_list.add(i, new ArrayList<>());

        // add edges to the graph
        for (Edge e : edges)
        {
            // allocate new node in adjacency List from src to dest
            adj_list.get(e.src).add(new Node(e.dest, e.weight));
        }
    }
    // print adjacency list for the graph
    public static void printGraph(Graph graph)  {
        int src_vertex = 0;
        int list_size = graph.adj_list.size();

        System.out.println("The contents of the graph:");
        while (src_vertex < list_size) {
            //traverse through the adjacency list and print the edges
            for (Node edge : graph.adj_list.get(src_vertex)) {
                System.out.print("Vertex:" + src_vertex + " ==> " + edge.value +
                        " (" + edge.weight + ")\t");
            }

            System.out.println();
            src_vertex++;
        }
    }

    public List<Node> getItem(int position) {
        if (position < 0 || position >= adj_list.size())
            return new ArrayList<Node>();
        return adj_list.get(position);
    }
}