import java.util.ArrayList;

public class Acyclic {
    private static ArrayList<Integer>[] neighbors;

    public Acyclic(int capacity) {
        neighbors = (ArrayList<Integer>[])new ArrayList[capacity];
        for(int i=0;i<capacity;i++){
            neighbors[i]=new ArrayList<Integer>();
        }
    }

    public void addEdge(int i, int j){
        neighbors[i].add(j);
    }

    public boolean traverseDepthFirst(int origin, int start) {
        return traverseDepthFirst(origin, start, new boolean[neighbors.length]);
    }

    boolean traverseDepthFirst(int origin, int start, boolean[] visited){
        if(!visited[start]){
            visited[start]=true;
            //StdOut.println(start);
            for (int neighbor : neighbors[start]) {
                if(neighbor==origin){
                    return false;
                }
                if(!visited[neighbor]) {
                    return traverseDepthFirst(origin,neighbor,visited);
                }
            }
        }
        return true;
    }

    public static void main(String[] args){
//        In in = new In("Testing Acyclicity.txt");
        In in = new In("rosalind_dag.txt");
        int x = in.readInt();//number of graphs
        for(int i=0;i<x;i++) {
            int v = in.readInt();//number of vertices in the first graph
            int e = in.readInt();//number of edges in the first graph.
            Acyclic g = new Acyclic(v + 1);
            for (int j = 0; j < e; j++) {//read all the edge pairs until we get to the next graph
                int p = in.readInt();
                int q = in.readInt();
                g.addEdge(p, q);
            }
            boolean flag=true;
            for(int k=1;k<v+1;k++) {
                for(int neighbor:neighbors[k]) {
                    flag = g.traverseDepthFirst(k, neighbor);
                }
                if(!flag){
                    break;
                }
            }
            if(flag){
                StdOut.print("1 ");
            }else{
                StdOut.print("-1 ");
            }
        }
    }
}
