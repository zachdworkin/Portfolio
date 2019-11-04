public class twoSum {
    //This solves the 2SUM problem in rosalind. By: Zach Dworkin
    private static int[][] data;
    private static int[] posIndecies;
    private static int[] negIndecies;

    public twoSum(int r, int c){
        data=new int[r][c];
    }

    /**
     *
     * @param row finds the 2SUM for a particular row of the data array
     */
    public static void find(int row) {
        boolean flag=true;
        posIndecies=new int[100001];
        negIndecies=new int[100001];
        for(int i=1;i<data[row].length;i++){//goes through every column in a row and checks if that element is positive or negative
            int p=data[row][i];
            if(p==0){
                if(posIndecies[0]==0){
                    posIndecies[0]=i;
                }
                if(negIndecies[0]==0 && posIndecies[0]!=i){
                    negIndecies[0]=i;
                }
            }
            if(p>0){//if it is positive
                if(posIndecies[p]==0){//check to see if an index hasnt been saved yet
                    posIndecies[p]=i;//save the index
                }
            }
            if(data[row][i]<0){//if it is negative
                p=p*-1;
                if(negIndecies[p]==0){//check to see if an index hasnt been saved yet
                    negIndecies[p]=i;//save that index
                }
            }
        }
        for(int i=0;i<100001;i++){
            if(posIndecies[i]!=0 && negIndecies[i]!=0){//determines whether or not there is a 2SUM
                //saves the values so it I don't have to go back and check each array at the index over and over again for the print statements
                int p=posIndecies[i];
                int q=negIndecies[i];
                //decides which index to print first
                if(p<q){
                    StdOut.println(p+" "+q);//+" : "+data[row][p] +"," +data[row][q]);
                    flag=false;
                    break;
                }else {
                    StdOut.println(q + " " + p);//+" : "+data[row][q] +"," +data[row][p]);
                    flag=false;
                    break;
                }
            }
        }
        if(flag) {
            StdOut.println("-1");
        }
    }

    public static void main(String[] args){
        In in = new In("rosalind_2sum.txt");//creates the data matrix to solve the problem
        //add 1 to rows and columns to avoid issues with row and column 0 in rosalind
        int r = in.readInt()+1;
        int c = in.readInt()+1;
        new twoSum(r,c);
        for(int i=1;i<r;i++){
            for(int j=1;j<c;j++){
                int p=in.readInt();
                data[i][j]=p;
            }
        }
        for(int i=1;i<r;i++){//goes through each row and prints the 2SUM for the lowest absolute value of a number in that row assuming there is one
            find(i);
        }
    }
}
