public class SquareInAGraph {
    public static int[][] graph;
    public static int[][] squareGraph;

    public SquareInAGraph(int v) {
        graph=new int[v][v];
        squareGraph=new int[v][v];
    }

    public void addEdge(int p,int q){
        graph[p][q]=1;
        graph[q][p]=1;
    }

    /**
     * Prints a Matrix - specify which matrix in the print statement
     * @param m number of rows
     * @param n number of columns
     */
    public void printMat(int m, int n){
        for(int r=1;r<m;r++){
            for(int c=1;c<n;c++){
                StdOut.printf(" %2d ", squareGraph[r][c]);
            }
            StdOut.println();
        }
    }

    /**
     * Multiplies a matrix by itself and saves it into a new matrix
     * @param m number of rows
     * @param n number of columns
     */
    public void matMult(int m, int n){
        int total=0;
        for(int i=1;i<m;i++){
            for(int j=1;j<m;j++){//each item in a row
                for(int k=1;k<n;k++){//each item in a column
                    total+=graph[i][k]*graph[k][j];
                }
                squareGraph[i][j]=total;
                total=0;
            }
        }
    }

    /**
     * Checks till it finds a value not on the main diagonal of a square matrix that is greater than 1
     * if such a value is found, there is a square in the graph because the resulting matrix from the
     * multiplication would possess numbers that represented how many paths could be taken to get back
     * to a given node
     * @param m number of rows
     * @param n number of columns
     * @return true if there is a square in the graph
     */
    public static boolean checkSquare(int m, int n){
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                if(i!=j){
                    if(squareGraph[i][j]>1){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String args[]){
        In in = new In("SquareInAGraph.txt");
//        In in = new In("rosalind_sq.txt");
        int i = in.readInt();//number of graphs
        for(int j=0; j<i;j++) {
            int v = in.readInt();//number of vertices in the graph
            SquareInAGraph g = new SquareInAGraph(v + 1);
            int e = in.readInt();// number of edges
            for(int k=0;k<e;k++){
                int p = in.readInt();
                int q = in.readInt();
                g.addEdge(p, q);
            }
            g.matMult(v + 1, v + 1);
//            g.printMat(v+1,v+1);
            if (checkSquare(v + 1, v + 1)) {
                StdOut.print("1 ");
            } else {
                StdOut.print("-1 ");
            }
//            StdOut.println();
        }
    }
}
