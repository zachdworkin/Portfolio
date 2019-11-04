public class BloomFilter<T> {
    public long[] filter;

    public BloomFilter() {
        this.filter = new long[1024];
    }


    public boolean mightContain(String url) {
        int hashed = url.hashCode();

        boolean firstindex = false;
        int highorder = hashed >>> 16;
        if ((filter[(highorder/64)] & (1L << (highorder % 64))) == (1L << (highorder % 64))){
            firstindex = true;
        }

        boolean secondindex = false;
        int loworder = (hashed << 16) >>> 16;
        if ((filter[(loworder/64)] & (1L << (loworder % 64))) == (1L << (loworder %64))){
            secondindex = true;
        }

        if (firstindex && secondindex){
            return true;
        }
        return false;
    }


    public void add(String url) {
        int hashed = url.hashCode();

        int highorder = hashed >>> 16;
        int index=highorder/64;
        filter[index] = filter[index] | (1L << (highorder % 64));

        int loworder = (hashed << 16) >>> 16;
        int index2=loworder/64;
        filter[index2] = filter[index2] | (1L << (loworder % 64));


    }


    public int trueBits() {
        int sum=0;
        for(int i=0;i<1024;i++){
            long mask=1;
            if(filter[i]==0){continue;}
            for(int j=0;j<64;j++){
                long check=filter[i]&mask;
                if(check != 0){
                    sum++;
                }
                mask=mask<<1;
            }
        }
        return sum;
    }
}
