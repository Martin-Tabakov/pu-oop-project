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
    private String symbols;

    public int getSpeed() {
        return speed;
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

    public boolean hasSamePos(Spot f) {
        return placement.hasEqualValues(f);
    }

    public boolean attack() {
        return true;
    }

    public boolean move(Spot moveToPos) {
        this.placement = moveToPos;
        return true;
    }

    public boolean heal(int healthRegen) {
        if(maxHealth == health) return false;
        this.health += healthRegen;
        if(health>maxHealth) health = maxHealth;
        return true;
    }


    @Override
    public String toString() {
        return String.format("%s row: %d, col: %d", symbols, placement.getHeight(), placement.getWidth());
    }
}