import java.util.ArrayList;
public class Room {

    private String description;

    private ArrayList<String> exits;

    private Monster monster;

    private String name;

    private ArrayList<Room> neighbors;

    private Treasure treasure;

    private Weapon weapon;

    public Room(String name, String description) {
        //sets the private variables for a specific room object
        this.name = name;
        this.description = description;
        exits = new ArrayList();
        neighbors = new ArrayList();
        //initialize all the other private thingies
    }
    /** Returns a description of this room. */
    public String getDescription() {
        return description;
    }

    /** Returns this room's name. */
    public String getName() {
        return name;
    }

    /** adds neighboring rooms */
    public void addNeighbor(String direction, Room neighbor) {
        exits.add(direction);
        neighbors.add(neighbor);
    }

    /** returns the exits of the room*/
    public String listExits(){
        return exits.toString();
    }

    //** gets the neighboring rooms */
    public Room getNeighbor(String direction){
        for (int i = 0; i < exits.size(); i++) {
            if ((exits.get(i)).equals(direction)) {
                return neighbors.get(i);
            }
        }
        return null;
    }

    /** sets monster in the room */
    public void setMonster(Monster monster){
        this.monster=monster;
    }

    /** sets treasure in the room */
    public void setTreasure(Treasure treasure){
        this.treasure=treasure;
    }

    /** sets weapon in the room */
    public void setWeapon(Weapon weapon){
        this.weapon=weapon;
    }

    /** returns the monster in the room */
    public Monster getMonster(){ return monster; }

    /** returns the treasure in the room */
    public Treasure getTreasure(){
        return treasure;
    }

    /** returns the weapon in the room */
    public Weapon getWeapon(){
        return weapon;
    }

}
