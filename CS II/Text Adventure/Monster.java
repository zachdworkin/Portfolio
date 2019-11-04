public class Monster {

    /** A description of this monster. */
    private String description;

    /** This monster's name. */
    private String name;

    /** The health of this monster. */
    private int health;
    /**
     * @param name
     *            This monster's name.
     * @param health
     *            The health of this monster.
     * @param description
     *            A description of this monster.
     */
    public Monster(String name, int health, String description) {
        //sets the private variables for the monster
        this.name = name;
        this.health = health;
        this.description = description;
    }

    /** returns the name of the monster*/
    public String getName(){
        return name;
    }

    /** returns the health of the monster*/
    public int getArmor(){
        return health;
    }

    /** returns the description of the monster*/
    public String getDescription(){
        return description;
    }


}
