package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindAllTrips {
    public static void main(String[] args) {
        // represent data statically
        List<Vertex> vertexes = new ArrayList<>();
        Vertex nyc = new Vertex("nyc", 0);
        Vertex chicago = new Vertex("chicago", 1);
        Vertex kansasCity = new Vertex("kansas city", 2);
        Vertex phoenix = new Vertex("phoenix", 3);
        vertexes.add(nyc);
        vertexes.add(chicago);
        vertexes.add(kansasCity);
        vertexes.add(phoenix);

        Digraph graph = new Digraph(vertexes.size());

        graph.addEdge(new DirectedEdge(nyc, chicago));
        graph.addEdge(new DirectedEdge(chicago, kansasCity));
        graph.addEdge(new DirectedEdge(chicago, phoenix));
        graph.addEdge(new DirectedEdge(kansasCity, nyc));
        graph.addEdge(new DirectedEdge(nyc, kansasCity));

        DirectedDFS dfs = new DirectedDFS(graph);

        //
        dfs.allTrips("nyc", "kansas city");
        dfs.allTrips("nyc", "phoenix");
    }
}

class Digraph {
    private final int V;
    private List<Integer>[] adj;
    private Map<Integer, Vertex> indexToVertex;
    private Map<String, Vertex> nameToVertex;

    public Digraph(int V) {
        // Number of vertices
        this.V = V;

        adj = (List<Integer>[]) new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
        indexToVertex = new HashMap<>();
        nameToVertex = new HashMap<>();
    }

    public int V() {
        return V;
    }

    public List<Integer> adj(Vertex v) {
        return adj[v.getIndex()];
    }

    public void addEdge(DirectedEdge edge) {
        Vertex src = edge.src();
        Vertex dst = edge.dst();
        adj[src.getIndex()].add(dst.getIndex());

        indexToVertex.put(src.getIndex(), src);
        indexToVertex.put(dst.getIndex(), dst);

        nameToVertex.put(src.getName(), src);
        nameToVertex.put(dst.getName(), dst);
    }

    public Vertex getVertexByIndex(Integer index) {
        return indexToVertex.get(index);
    }

    public Vertex getVertexByName(String name) {
        return nameToVertex.get(name);
    }
}

class DirectedEdge {
    private final Vertex src;
    private final Vertex dst;

    public DirectedEdge(Vertex src, Vertex dst) {
        this.src = src;
        this.dst = dst;
    }

    public Vertex src() {
        return this.src;
    }

    public Vertex dst() {
        return this.dst;
    }
}

class Vertex {
    private final String name;
    private final int index;

    public Vertex(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vertex)) {
            return false;
        }
        return ((Vertex) obj).index == index;
    }
}

class DirectedDFS {
    private boolean [] marked;
    private int [] path;
    private Digraph graph;
    private List<List<String>> listOfTrips;

    public DirectedDFS(Digraph graph) {
        this.graph = graph;
    }

    public Iterable<List<String>> allTrips(String src, String dst) {
        return allTrips(graph.getVertexByName(src), graph.getVertexByName(dst));
    }

    public Iterable<List<String>> allTrips(Vertex src, Vertex dst) {
        marked = new boolean[graph.V()];
        path = new int[graph.V()];
        listOfTrips = new ArrayList<>();

        dfs(src, dst, 0);
        return listOfTrips;
    }

    private void dfs(Vertex src, Vertex dst, int pathIndex) {
        marked[src.getIndex()] = true;
        path[pathIndex] = src.getIndex();
        pathIndex++;

        if (src.equals(dst)) {
            List<String> trip = new ArrayList<>();
            for (int i = 0; i < pathIndex; i++) {
                trip.add(graph.getVertexByIndex(path[i]).getName());
            }
            listOfTrips.add(trip);
        } else {
            List<Integer> adjList = graph.adj(src);
            for (Integer w : adjList) {
                if (!marked[w]) {
                    dfs(graph.getVertexByIndex(w), dst, pathIndex);
                }
            }
        }

        marked[src.getIndex()] = false;
    }
}

