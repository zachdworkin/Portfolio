import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Topological;

public class HamiltonianPath {

    /**
     * This code solves the Hamiltonian Path problem on rosalind.info
     * By Zach Dworkin
     */

    public static void main(String[] args) { new HamiltonianPath().run(); }

    /**
     * Runs the solve method for each graph provided in the data set
     */
    private void run(){
//        In in = new In("HamiltonianPath.txt");
        In in = new In("rosalind_hdag.txt");
        int n = in.readInt();//number of graphs
        for (int i = 0;i < n; i++) {
            StdOut.println(solve(in));
        }
    }

    /**
     * Determines if there is an edge between i->j
     * @param g graph being utilized
     * @param i start node
     * @param j end node
     * @return
     */
    public boolean hasEdge(EdgeWeightedDigraph g, int i, int j){
        if(i==-1){
            return true;
        }
        for(DirectedEdge k:g.adj(i)){
            if(k.to()==j){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param in the edgeweighted digraph being used to solve the problem
     * @return the answer in the form of a string
     */
    private String solve(In in) {
        EdgeWeightedDigraph g = readGraph(in);
        Topological top = new Topological(g);
        int current = -1;
        int next = -1;
        String finished = "1 ";
        for(int a : top.order()){
            current=next;
            next=a;
            if(a!=0) {
                if (!hasEdge(g, current, next)) {
                    return "-1";
                }else{
                    finished=finished+a+" ";
                }
            }
        }
        return finished;
    }

    /**
     * reads in a graph from the data set
     * @param in
     * @return
     */
    private EdgeWeightedDigraph readGraph(In in) {
        int v= in.readInt();
        int e=in.readInt();
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(v+1);
        for(int i=0; i<e; i++){
            int p=in.readInt();
            int q=in.readInt();
            g.addEdge( new DirectedEdge(p,q,1) );
        }
        return g;
    }

}
