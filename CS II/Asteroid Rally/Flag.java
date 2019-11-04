public class Flag {
    
    private Extent extent;
    private boolean hitByShip1;
    private boolean hitByShip2;
    /** constructs a flag*/
    public Flag(double a, double b){
        this.extent = new Extent(a,b,0.01);
    }


    public Extent getExtent() {
        return extent;
    }

    public boolean hasBeenHitByShip1() {
        return hitByShip1;
    }

    public boolean hasBeenHitByShip2() {
        return hitByShip2;
    }

    public void setHitByShip2() {
        hitByShip2=true;
    }

    public void setHitByShip1() {
        hitByShip1=true;
    }
}
