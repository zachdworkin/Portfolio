import java.util.Arrays;
import java.util.HashMap;

public class threeSum {
    private static int[][] data;

    public threeSum(int r, int c){
        data=new int[r][c];
    }

    private static void printOrder(int a, int b, int c) {
        int m=0;
        if(a>b) {
            m = a;
            a = b;
            b = m;
        }

        if(a>c){
            m = a;
            a = c;
            c = m;
        }

        if(b>c){
            m = b;
            b = c;
            c = m;
        }

        StdOut.println(a + " " + b +" " +c);
    }
    public static void check(int[] row){
        int len = row.length;
        HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

        //put every element and it's index into hashMap:
        for(int i=0; i<len; i++){
            indexMap.put(row[i], i);
        }

        //sort the array:
        Arrays.sort(row);

        for(int i=0; i<len-2; i++){

            int p2 = i+1;
            int p3 = len-1;

            while(p2!=p3){
                int sum = row[p2] + row[p3];

                if(row[i] == -sum){
                    printOrder((indexMap.get(row[i])+1), (indexMap.get(row[p2])+1), (indexMap.get(row[p3]) +1));

                    return;

                } else if(row[i] < -sum){
                    p2++;

                } else {
                    p3--;
                }
            }

        }//end for i<len-2 loop;

        StdOut.println("-1");

    }

    public static void main(String[] args){
//        In in = new In("3SUMexample.txt");
        In in = new In("rosalind_3sum.txt");//creates the data matrix to solve the problem
        int r = in.readInt();
        int c = in.readInt();
        new threeSum(r,c);
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                int p=in.readInt();
                data[i][j]=p;
            }
        }

        for(int i=0;i<r;i++){
            check(data[i]);
        }

    }
}
