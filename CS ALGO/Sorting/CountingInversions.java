public class CountingInversions {

    static int getInvCount(int n, int[] arr) {
        int inv_count = 0;
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                if (arr[i] > arr[j])
                    inv_count++;

        return inv_count;
    }

    public static void main(String[] args) {
//        In in = new In("Couting Inversions.txt");
        In in = new In("rosalind_inv.txt");
        int x = in.readInt();//number of items in the array
        int arr[]=new int[x];
        int y=0;
        while(!in.isEmpty()) {
            int p = in.readInt();
            arr[y]=p;
            y++;
        }
        StdOut.print(getInvCount(arr.length,arr));
    }
}

