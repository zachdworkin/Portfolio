import edu.princeton.cs.algs4.Quick;

public class PartialSort {
    public Comparable[] arr;

    public PartialSort(int i) {
        arr=new Comparable[i];
    }

    public static void main(String[] args) {
//        In in = new In("PartialSort.txt");
        In in = new In("rosalind_ps.txt");
        int i=in.readInt();
        Median a= new Median(i);
        for(int j=0;j<i;j++) {
            a.arr[j]=in.readInt();
        }
        Quick.sort(a.arr);
        int k=in.readInt();
        for(int l=0;l<k;l++){
            StdOut.print(a.arr[l]+" ");
        }
    }
}
