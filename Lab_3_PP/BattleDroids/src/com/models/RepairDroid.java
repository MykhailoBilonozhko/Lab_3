package com.models;

public class RepairDroid extends Droid{
    public final int REPAIR_POWER = 10;

    public RepairDroid(String name){super(name, 170, 15, "Repair");}

    public int attack(Droid target){
        System.out.println(name + " атакує " + target.getName() + " і завдає " + damage + " шкоди.");
        target.takeDamage(damage);
        return damage;
    }

    public void repair(Droid target){
        System.out.println(name + " ремонтує " + target.getName() + " на " + REPAIR_POWER + " очок здоров'я.");
        target.takeRepair(REPAIR_POWER);
    }

    public int getREPAIR_POWER() {
        return REPAIR_POWER;
    }
}
