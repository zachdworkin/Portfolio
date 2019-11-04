import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Topological;

public class HamiltonianPath {
    public static void main(String[] args) {
        new HamiltonianPath().run();
    }
    private void run(){
//        In in = new In("HamiltonianPath.txt");
        In in = new In("rosalind_hdag.txt");
        int n = in.readInt();//number of graphs
        for (int i = 0;i < n; i++) {
            solve(in);
        }
    }

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

    private void solve(In in) {
        boolean flag=false;
        EdgeWeightedDigraph g = readGraph(in);
        Topological top = new Topological(g);
        int current = -1;
        int next = -1;
        for(int a : top.order()){
            current=next;
            next=a;
            if(a!=0) {
                if (!hasEdge(g, current, next)) {
                    flag = !flag;
                    break;
                }
            }
        }
        if(!flag){
            StdOut.print("1 ");
            for(int a : top.order()) {
                if(a!=0) {
                    StdOut.print(a + " ");
                }
            }
            StdOut.println();
        }else{
            StdOut.println("-1");
        }
    }
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
