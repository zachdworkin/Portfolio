import edu.princeton.cs.algs4.ResizingArrayQueue;

import java.util.ArrayList;

public class ConnectedComponents {

    private static ArrayList<Integer>[] neighbors;
    private static boolean[] visited;
    private static int components;

    public ConnectedComponents(int capacity) {
        neighbors = (ArrayList<Integer>[]) new ArrayList[capacity];
        visited = new boolean[capacity];
        for (int i = 0; i < capacity; i++) {
            neighbors[i] = new ArrayList<Integer>();
        }
    }

    public void addEdge(int i, int j) {
        neighbors[i].add(j);
        neighbors[j].add(i);
    }

    void traverseDepthFirst(int start) {
        if (!visited[start]) {
            visited[start] = true;
            //StdOut.println(start);
            for (int neighbor : neighbors[start]) {
                if (!visited[neighbor]) {
                    traverseDepthFirst(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) {
//        In in = new In("rosalind_cc.txt");
//        int v = in.readInt();
//        ConnectedComponents g = new ConnectedComponents(v+1);
//        in.readInt();//skip the number of edges
//        while(!in.isEmpty()) {
//            int p = in.readInt();
//            int q = in.readInt();
//            g.addEdge(p, q);
//        }
//        components=0;
//        for(int i=1;i<v+1;i++) {
//            if(!visited[i]) {
//                components++;
//                g.traverseDepthFirst(i);
//            }
//        }
//        In in = new In("Bipartiteness.txt");
        In in = new In("rosalind_bip.txt");
        int v = in.readInt();
        int total=0;
        for (int i = 0; i < v; i++) {
            boolean flag = true;
            int w = in.readInt();
            int y = in.readInt();//number of edges
            ConnectedComponents g = new ConnectedComponents(w + 1);
            for (int j = 0; j < y; j++) {//creates a graph
                int p = in.readInt();
                int q = in.readInt();
                g.addEdge(p, q);
            }
            components = 0;
            for (int z = 1; z < v + 1; z++) {
                if (!visited[z]) {
                    components++;
                    g.traverseDepthFirst(z);
                }
            }
            //StdOut.println(components);
            total+=components;
        }
        StdOut.println(total);
//        StdOut.println("there are "+components+" components");
    }
}
