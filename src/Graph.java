import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph
{

    static void addEdge(ArrayList<ArrayList<Integer>> adj, int u, int v)
    {
        if (adj.get(u) == null)
        {
            adj.set(u, new ArrayList<>());
        }
        if (adj.get(v) == null)
        {
            adj.set(v, new ArrayList<>());
        }
        adj.get(u).add(v);

        //adj.get(v).add(u); <- use the line undirected graph
        // for a directed graph with an edge pointing from u
        // to v, adj.get(u).add(v);
    }

    // A utility function to print the adjacency list
    // representation of graph
    static void printGraph(ArrayList<ArrayList<Integer>> adj) {
        for (int i = 0; i < adj.size(); i++) {
            if (adj.get(i) != null) {
                System.out.println("\nAdjacency list of vertex " + i);
                System.out.print("head" + "[" + i + "]");
                for (int j = 0; j < adj.get(i).size(); j++)
                {
                    System.out.print(" -> " + adj.get(i).get(j));
                }
                System.out.println();
            }
        }
    }

    static void printCycle(ArrayList<ArrayList<Integer>> adj) {
        ArrayList<Integer> visitedNodes = new ArrayList<>();
        int node = 0;

        for (int i = 0; i < adj.size(); i++) {
            if (adj.get(i) != null) {
                node = i;
                visitedNodes.add(node);
                node = adj.get(i).get(0);
                break;
            }
        }

        boolean cycleFound = false;
        while (!cycleFound) {
            if (visitedNodes.contains(node)) {
                visitedNodes.add(node);
                cycleFound = true;
            }else {
                visitedNodes.add(node);
                node = adj.get(node).get(0);
            }
        }

        System.out.println("Cycle : ");
        for (int i = visitedNodes.indexOf(node); i < visitedNodes.size(); i++) {
            System.out.print(visitedNodes.get(i) + " ");
        }
    }

    static boolean isEmpty(ArrayList<ArrayList<Integer>> adj) {
        boolean graphIsEmpty = true;
        for (int i = 0; i < adj.size(); i++) {
            if (adj.get(i) != null) {
                graphIsEmpty = false;
            }
        }
        return graphIsEmpty;
    }

    static int findSink(ArrayList<ArrayList<Integer>> adj) {
        int numVertices = adj.size();

        for (int i = 0; i < numVertices; i++) {
            if (adj.get(i) != null) {

                if (adj.get(i).isEmpty()) {
                    return i;
                }
            }
        }

        return -1; // No sink found
    }

    static void removeVertex(ArrayList<ArrayList<Integer>> adj, int vertex) {

        // Remove the vertex from adjacency lists of other vertices
        for (ArrayList<Integer> integers : adj)
        {
            if (integers != null)
            {
                integers.remove(Integer.valueOf(vertex));
            }
        }

        // Remove the vertex from the adjacency list
        adj.set(vertex, null);
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        List<int[]> edges = new ArrayList<>();

        //TASK3
        try {
            // Read the input file
            File inputFile = new File("input.txt");
            Scanner scanner = new Scanner(inputFile);

            // Determine the number of vertices
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] vertices = line.split(" ");
                int u = Integer.parseInt(vertices[0]);
                int v = Integer.parseInt(vertices[1]);
                edges.add(new int[]{u, v});
            }

            int max = -1;
            for (int[] edge : edges) {
                // Updating maxVertex with the larger value between the two vertices of each edge
                max = Math.max(max, Math.max(edge[0], edge[1]));
            }

            // Initialize the adjacency list
            for (int i = 0; i <= max; i++) {
                adj.add(null);
            }

            for (int[] edge : edges) {
                addEdge(adj, edge[0], edge[1]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        printGraph(adj);
        int sinkVertex = 1;
        while (sinkVertex != -1) {
            sinkVertex = findSink(adj);
            if (sinkVertex != -1) {
                System.out.println("");
                System.out.println("----------------------------");
                System.out.println("Sink vertex found: " + sinkVertex);
                removeVertex(adj, sinkVertex);
                System.out.println("Vertex " + sinkVertex + " removed.");
                System.out.println("----------------------------");
                printGraph(adj);
            } else {
                System.out.println("No sink vertex found.");
            }
        }




        System.out.println();
        System.out.println();
        if (isEmpty(adj)) {
            System.out.println("Graph is empty.");
            System.out.println("Yes, Graph is acyclic.");
        } else {
            System.out.println("Graph is not empty.");
            System.out.println("No, Graph is not acyclic.");
            printCycle(adj);
        }
    }
}