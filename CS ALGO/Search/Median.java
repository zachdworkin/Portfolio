import edu.princeton.cs.algs4.Quick;

public class Median {
    public Comparable[] arr;

    public Median(int i) {
        arr=new Comparable[i];
    }

    public static void main(String[] args) {
//        In in = new In("Median.txt");
        In in = new In("rosalind_med.txt");
        int i=in.readInt();
        Median a= new Median(i);
        for(int j=0;j<i;j++) {
            a.arr[j]=in.readInt();
        }
        Quick.sort(a.arr);
        StdOut.print(a.arr[in.readInt()-1]);
    }
}
