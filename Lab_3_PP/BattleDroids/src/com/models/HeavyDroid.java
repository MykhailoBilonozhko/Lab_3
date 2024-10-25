package com.models;

public class HeavyDroid extends Droid {
    public HeavyDroid(String name){super(name, 250, 20, "Heavy");}

    public int attack(Droid target){
        System.out.println(name + " атакує " + target.getName() + " і завдає " + damage + " шкоди.");
        target.takeDamage(damage);
        return damage;
    }

}
