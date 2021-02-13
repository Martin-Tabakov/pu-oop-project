package Figure;

import Utility.Place;

public abstract class Figure {
    private final int attackDmg;
    private final int attackRange;
    private int health;
    private final int speed;
    private final int armor;
    private Place placement;
    private String symbols;

    protected Figure(int attackDmg, int armor, int health, int attackRange, int speed, Place placement, String symbols) {
        this.attackDmg = attackDmg;
        this.armor = armor;
        this.health = health;
        this.attackRange = attackRange;
        this.speed = speed;
        this.placement = placement;
        this.symbols = symbols;
    }
}