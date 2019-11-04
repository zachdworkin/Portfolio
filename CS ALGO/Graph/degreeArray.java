import edu.princeton.cs.algs4.ResizingArrayQueue;

import java.util.ArrayList;

public class degreeArray {
    static private boolean[][] neighbors;
    static private int[] degrees;

    public degreeArray(int vertices) {
        degrees=new int[vertices];
        neighbors=new boolean[vertices][vertices];
        for(int i=0;i<vertices;i++){
            degrees[i]=0;
            for (int j=0; j<vertices;j++){
                neighbors[i][j]=false;
            }
        }
    }

    public void addEdge(int i, int j){
        neighbors[i][j]=true;
        neighbors[j][i]=true;
    }

    public void countDegrees() {
        for(int i=0;i<degrees.length;i++){
            for(int j=0;j<neighbors[i].length;j++){
                if(neighbors[i][j]==true){
                    degrees[i]++;
                }
            }
        }
    }

    public static void main(String[] args){
        In in = new In("rosalind_deg.txt");
        int v = in.readInt();
        degreeArray g = new degreeArray(v+1);
        in.readInt();
        while(!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();
            g.addEdge(p, q);
        }
        g.countDegrees();
        for(int i=1;i<degrees.length;i++){
            StdOut.print(degrees[i]+" ");
        }
    }
}
