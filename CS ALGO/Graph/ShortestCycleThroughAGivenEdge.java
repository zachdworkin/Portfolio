import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

/** Rosalind.info problem:shortest cycle in a weighted digraph NOT going through a given edge.*/
public class ShortestCycleThroughAGivenEdge {

    /** Tip of edge in question*/
    private int tip;

    /** Tail of edge in question*/
    private int tail;

    /** Weight of tip to tail edge*/
    private int weight;

    public static void main(String[] args) {
        new ShortestCycleThroughAGivenEdge().run();
    }
    public void run(){
            In in = new In("rosalind_cte.txt");
//        In in = new In("CTE.txt");
        int n = in.readInt();//number of graphs
        for (int i = 0;i < n; i++) {
            solve(in);
        }
    }

    /**
     * Reads a weighted digraph from in and prints the solution
     * @param in
     */
    private void solve(In in) {
        EdgeWeightedDigraph g = readGraph(in);
        DijkstraSP shortest = new DijkstraSP(g,tip);
        double length = shortest.distTo(tail);
        if(length==Double.POSITIVE_INFINITY){
            StdOut.print("-1 ");
        }else{
            StdOut.print((int)length+weight + " ");
        }
    }

    private EdgeWeightedDigraph readGraph(In in) {
        int v= in.readInt();
        int e=in.readInt();
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(v+1);
        tail=in.readInt();
        tip=in.readInt();
        weight=in.readInt();
        for(int i=0; i<e-1; i++){
            g.addEdge( new DirectedEdge(in.readInt(),in.readInt(),in.readInt()) );

        }
        return g;
    }
}
