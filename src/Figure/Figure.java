package Figure;

import Utility.Spot;

public abstract class Figure {
    private final int attackDmg;
    private final int attackRange;
    private int health;
    private final int speed;
    private final int armor;
    private Spot placement;
    private String symbols;

    public String getSymbols() {
        return symbols;
    }

    public Spot getPlacement() {
        return placement;
    }

    public void setPlacement(Spot placement) {
        this.placement = placement;
    }

    protected Figure(int attackDmg, int armor, int health, int attackRange, int speed, Spot placement, String symbols) {
        this.attackDmg = attackDmg;
        this.armor = armor;
        this.health = health;
        this.attackRange = attackRange;
        this.speed = speed;
        this.placement = placement;
        this.symbols = symbols;
    }

    public boolean hasSamePos(Spot f){
        return this.placement.getWidth() == f.getWidth() &&
                this.placement.getHeight() == f.getHeight();
    }


    @Override
    public String toString() {
        return String.format("%s row: %d, col: %d",symbols,placement.getHeight(),placement.getWidth());
    }
}