public class doubleDegreeArray {
    static private int[][] neighbors;
    static private int[] degrees;
    static private int[] doubleDegrees;

    public doubleDegreeArray(int vertices) {
        degrees=new int[vertices];
        doubleDegrees=new int[vertices];
        neighbors=new int[vertices][vertices];
        for(int i=0;i<vertices;i++){
            degrees[i]=0;
            doubleDegrees[i]=0;
            for (int j=0; j<vertices;j++){
                neighbors[i][j]=0;
            }
        }
    }

    public void addEdge(int i, int j){
        neighbors[i][j]=1;
        neighbors[j][i]=1;

    }

    public void countDegrees() {
        for(int i=0;i<degrees.length;i++){
            for(int j=0;j<neighbors[i].length;j++){
                if(neighbors[i][j]==1){
                    degrees[i]++;
                }
            }
        }
    }

    public void countDoubleDegrees(){
        for(int i=0;i<doubleDegrees.length;i++){
            int sum=0;
            for(int j=0;j<neighbors[i].length;j++){
                if(neighbors[i][j]!=0){
                    sum=sum+degrees[j];
                }
            }
            doubleDegrees[i]=sum;
        }
    }

    public static void main(String[] args){
//        doubleDegreeArray g = new doubleDegreeArray(6);
//        g.addEdge(1,2);
//        g.addEdge(2,3);
//        g.addEdge(4,3);
//        g.addEdge(2,4);
        In in = new In("rosalind_ddeg.txt");
        int v = in.readInt();
        doubleDegreeArray g = new doubleDegreeArray(v+1);
        in.readInt();
        while(!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();
            g.addEdge(p, q);
        }
        g.countDegrees();
        g.countDoubleDegrees();
        for(int i=1;i<doubleDegrees.length;i++){
            StdOut.print(doubleDegrees[i]+" ");
        }
    }
}
