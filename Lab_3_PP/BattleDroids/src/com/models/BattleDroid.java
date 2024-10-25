package com.models;

import java.util.Random;

public class BattleDroid extends Droid {
    private final int CRITICAL_CHANCE = 20;
    private final int CRITICAL_BOOST = 10;
    public BattleDroid(String name){super(name, 120, 25, "Battle");}

    public int attack(Droid target){
        Random random = new Random();
        int chance = random.nextInt(100);
        int finalDamage = damage;
        if(chance < CRITICAL_CHANCE) {
            finalDamage += CRITICAL_BOOST;
        }
        System.out.println(name + " атакує " + target.getName() + " і завдає " + finalDamage + " шкоди.");
        target.takeDamage(finalDamage);
        return finalDamage;
    }
}
