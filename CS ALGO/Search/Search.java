import java.util.Arrays;

public class Search {

    static int linearSearch(double[]a, double key){
        for(int i=0; i<a.length; i++) {
            if (a[i]==key) {
                return i;
            }
        }
        return -1;
    }

    static int binarySearch(double[] a, double key){
        int lo=0;
        int hi = a.length-1;
        while(lo <= hi-1){
            int mid = lo + (hi-lo) / 2;
            if(key==a[mid]){
                return mid;
            }
            if(key<a[mid]){
                hi = mid-1;
            }else{
                lo=mid+1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        double[] a = new double[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = StdRandom.uniform();
        }
        a[457] = 0.5;
        Arrays.sort(a);
        int i = binarySearch(a, 0.5);
        StdOut.println(i);
//        StdOut.println(a[i]==0.5);
    }
}
