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

    public Spot getPlacement() {
        return placement;
    }

    public int getHealth() {
        return health;
    }

    public void setPlacement(Spot placement) {
        this.placement = placement;
    }

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

    public boolean attack(int damageDealt) {
        this.health-= damageDealt;
        return health <= 0;
    }

    public boolean move(Spot moveToPos) {
        this.placement = moveToPos;
        return true;
    }

    public boolean heal(int healthRegen) {
        this.health += healthRegen;
        return true;
    }


    @Override
    public String toString() {
        return String.format("%s row: %d, col: %d", symbols, placement.getHeight(), placement.getWidth());
    }
}