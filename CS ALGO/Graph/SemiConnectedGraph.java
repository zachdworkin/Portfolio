import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SemiConnetedGraph {
    public static void main(String[] args) {new SemiConnetedGraph().run(); }
    public void run(){
        In in = new In("rosalind_sc.txt");
//        In in = new In("SemiConnectedGraph.txt");
        int n=in.readInt();
        for(int i=0;i<n;i++){
            if(solve(in)){
                StdOut.print("1 ");
            }else{
                StdOut.print("-1 ");
            }
        }
    }
    private boolean solve(In in) {
        Digraph g = readGraph(in);
        for(int i=1;i<g.V();i++){
            DepthFirstDirectedPaths P = new DepthFirstDirectedPaths(g,i);
            for(int j=1;j<g.V();j++){
                DepthFirstDirectedPaths Q = new DepthFirstDirectedPaths(g,j);
                if(P.hasPathTo(j) || Q.hasPathTo(i)){
                    continue;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    private Digraph readGraph(In in) {
        Digraph g = new Digraph(in.readInt()+1);
        int e=in.readInt();
        for(int i=0; i<e; i++){
            g.addEdge(in.readInt() , in.readInt());
        }
        return g;
    }
}
