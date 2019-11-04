public class Weapon {

    /** A description of this weapon. */
    private String description;

    /** This weapon's name. */
    private String name;

    /** The damage of this weapon. */
    private int damage;

    /**
     * @param name
     *            This weapon's name.
     * @param damage
     *            The damage of this weapon.
     * @param description
     *            A description of this weapon.
     */

    public Weapon(String name, int damage, String description) {
        //sets the private variables for the weapon
        this.name = name;
        this.damage = damage;
        this.description = description;
    }

    /** returns the damage of the weapon*/
    public int getDamage(){
        return damage;
    }

    /** returns the name of the weapon*/
    public String getName(){
        return name;
    }

    /** returns the description of the weapon*/
    public String getDescription(){
        return description;
    }

}
