public class majorityElement {
    private static int[][] data;

    public majorityElement(int r, int c){
        data = new int[r][c];
    }

    public static int count(int row, int col){
        int k=-1;
        int[] frequency=new int[100001];
        for (int j = 0; j < data[row].length; j++) {
                frequency[data[row][j]]++;
        }
        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] > col/2) {
                k = i;
            }
        }
        return k;
    }

    public static void main(String[] args){
        In in = new In("rosalind_maj.txt");
        int r = in.readInt();
        int c = in.readInt();
        majorityElement m = new majorityElement(r,c);
        int row=0;int col=0;
        while(row<r) {
            int p = in.readInt();
            data[row][col]=p;
            col++;
            if(col==(c-1)){
                row++;
                col=0;
            }
        }
//        int r=4;
//        int c=8;
//        data= new int[][]{{5, 5, 5, 5, 5, 5, 5, 5}, {8, 7, 7, 7, 1, 7, 3, 7}, {7, 1, 6, 5, 10, 100, 1000, 1}, {5, 1, 6, 7, 1, 1, 10, 1}};
//        int row=0;int col=0;
        for(int i=0;i<r;i++){
            StdOut.print(count(i,c)+" ");
        }
    }
}
