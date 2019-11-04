import java.util.ArrayList;

public class Graph {

    private static ArrayList<Integer>[] neighbors;

    public Graph(int capacity) {
        neighbors = (ArrayList<Integer>[])new ArrayList[capacity];
        for(int i=0;i<capacity;i++){
            neighbors[i]=new ArrayList<Integer>();
        }
    }

    public void addEdge(int i, int j){
        neighbors[i].add(j);
        neighbors[j].add(i);
    }

    public void traverseDepthFirst(int start) {
        traverseDepthFirst(start, new boolean[neighbors.length]);
    }

    void traverseDepthFirst(int start, boolean[] visited){
        if(!visited[start]){
            visited[start]=true;
            StdOut.println(start);
            for (int neighbor : neighbors[start]) {
                if(!visited[neighbor]) {
                    traverseDepthFirst(neighbor,visited);
                }
            }
        }
    }

    public static void main(String[] args){
        In in = new In("rosalind_ddeg.txt");
        int v = in.readInt();
        Graph g = new Graph(v+1);
        in.readInt();//skip the number of edges
        while(!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();
            g.addEdge(p, q);
        }
        g.traverseDepthFirst(1);

    }
}
