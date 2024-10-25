package com.models;

public abstract class Droid
{
    protected String name;
    protected int health;
    protected int damage;
    protected String type;

    public Droid(String name, int health, int damage, String type) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public int getDamage() {
        return this.damage;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public abstract int attack(Droid var1);

    public void takeRepair(int repair) {
        this.health += repair;
    }

    public String toString() {
        return this.name + " (Здоров'я: " + this.health + ", Атака: " + this.damage + ", Тип: " + this.type + ")";
    }
}
