package edu.ncsu.doorbellclient;

import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersRelation {
    private static int edgeCnt = 0;
    private HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
    private HashMap<Integer, String> integerStringHashMap = new HashMap<>();
    private Graph graph;

    private int ComputeMapping(String str) {
        int ret;
        if (!stringIntegerHashMap.containsKey(str)) {
            // if the key is not present, do the appropriate mappings
            stringIntegerHashMap.put(str, edgeCnt);
            integerStringHashMap.put(edgeCnt, str);
            edgeCnt++;
        }
        ret = stringIntegerHashMap.get(str);
        return ret;
    }

    public UsersRelation(List<StringEdge> stringEdges) {
        List<Edge> edges = new ArrayList<>();
        stringIntegerHashMap.clear();
        integerStringHashMap.clear();
        edgeCnt = 0;
        for (StringEdge stringEdge: stringEdges) {
            // map the string to an integer
            int src = ComputeMapping(stringEdge.getSrc());
            Log.d("shimul", stringEdge.getSrc() + " " + String.valueOf(src));
            int dest = ComputeMapping(stringEdge.getDest());
            Log.d("shimul", stringEdge.getDest() + " " + String.valueOf(dest));

            edges.add(new Edge(src, dest, 1));
            Log.d("shimul", "Adding edge: " + String.valueOf(src) + " " + String.valueOf(dest));
        }
        this.graph = new Graph(edges, stringIntegerHashMap.size());
    }

    public UsersRelation() {
        // empty constructor
        // required for firebase.
    }

    public List<String> getUserList(String user) {
        List<String> list = new ArrayList<>();
        if (!stringIntegerHashMap.containsKey(user))
            return list;

        final int fromUsedId = stringIntegerHashMap.get(user);
        List<Node> nodeList = graph.getItem(fromUsedId);
        for (Node node: nodeList) {
            String toUserId = integerStringHashMap.get(node.value);
            list.add(toUserId);
        }
        return list;
    }

    public String reverseUserMapping(int position) {
        if (!integerStringHashMap.containsKey(position))
            return "";
        return integerStringHashMap.get(position);
    }

    public int getGraphSize() {
        if (graph == null) return 0;
        else return graph.adj_list.size();
    }
}
