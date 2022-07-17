package edu.ncsu.doorbellclient;

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
        for (StringEdge stringEdge: stringEdges) {
            // map the string to an integer
            int src = ComputeMapping(stringEdge.getSrc());
            int dest = ComputeMapping(stringEdge.getDest());

            edges.add(new Edge(src, dest, 1));
        }
        this.graph = new Graph(edges);
    }
}
