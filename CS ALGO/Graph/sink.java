import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.In;

public class Sink {
    public static void main(String[] args) {new Sink().run(); }
    public void run(){
        In in = new In("rosalind_gs.txt");
//        In in = new In("sink.txt");
        int n=in.readInt();
        for(int i=0;i<n;i++){
            solve(in);
        }
    }
    private void solve(In in) {
        Digraph g = readGraph(in);
        boolean flag=false;
        for(int i=1;i<g.V();i++){
            DirectedDFS P = new DirectedDFS(g,i);
            if(P.count()==(g.V()-1)){
                StdOut.print(i+" ");
                flag=true;
                break;
            }
        }
        if(!flag){
            StdOut.print("-1 ");
        }
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
