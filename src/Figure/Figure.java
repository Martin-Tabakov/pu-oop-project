package Figure;

import Utility.Spot;

public abstract class Figure {
    private final int attackDmg;
    private final int attackRange;
    private int health;
    private final int maxHealth;
    private final int speed;
    private final int armor;
    private Spot placement;
    private final String symbols;

    public int getSpeed() {
        return speed;
    }

    public int getAttackDmg() {
        return attackDmg;
    }

    public int getArmor() {
        return armor;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public String getSymbols() {
        return symbols;
    }

    public Spot getPlace() {
        return placement;
    }

    public int getHealth() {
        return health;
    }

    public void setPlacement(Spot placement) {
        this.placement = placement;
    }

    /**
     * Constructor for a figure
     * @param attackDmg Damage that the figure deals on an attack
     * @param armor The amount of armor a figure has
     * @param health Current amount of health
     * @param attackRange The number of tiles from itself where the figure can execute ana attack
     * @param speed The amount of tiles the figure can move at once
     * @param placement The position of the figure on the board
     * @param symbols The symbols that distinguish the type of the figure
     */
    protected Figure(int attackDmg, int armor, int health, int attackRange, int speed, Spot placement, String symbols) {
        this.attackDmg = attackDmg;
        this.armor = armor;
        this.health = health;
        this.maxHealth = health;
        this.attackRange = attackRange;
        this.speed = speed;
        this.placement = placement;
        this.symbols = symbols;
    }

    /**
     * Suffers damage when attacked.
     * @param damageDealt The amount of damage tha fugire suffers
     * @return true if it is dead
     */
    public boolean attack(int damageDealt) {
        this.health-= damageDealt;
        return health <= 0;
    }

    /**
     * Changes the position of the figure
     * @param moveToPos the new position where the figure is placed
     * @return true
     */
    public boolean move(Spot moveToPos) {
        this.placement = moveToPos;
        return true;
    }

    /**
     * Heals the figure for a certain amount of hp, until it reaches maxHP
     * @param healthRegen amount of hp to recover
     * @return true
     */
    public boolean heal(int healthRegen) {
        this.health += healthRegen;
        return true;
    }


    @Override
    public String toString() {
        return String.format("%s row: %d, col: %d", symbols, placement.getHeight(), placement.getWidth());
    }
}