package com.battle;


import com.loggs.BattleLogger;
import com.models.Droid;
import com.models.RepairDroid;

public class OneVsOne extends Battle{
    private Droid firstDroid;
    private Droid secondDroid;


    public OneVsOne(Droid firstDroid, Droid secondDroid){
        this.firstDroid = firstDroid;
        this.secondDroid = secondDroid;
        this.battleLogger = new BattleLogger("battleLog.json");
    }

    public void start(){
        System.out.println("Бій починається!");

        while (true){
            battleLogger.logAction("attack", firstDroid.getName(), secondDroid.getName(), firstDroid.attack(secondDroid), 0);

            if (!secondDroid.isAlive()) {
                System.out.println(secondDroid.getName() + " загинув!");
                battleLogger.logDeath(secondDroid.getName());
                break;
            }

            battleLogger.logAction("attack", secondDroid.getName(), firstDroid.getName(), secondDroid.attack(firstDroid), 0);

            if (!firstDroid.isAlive()) {
                System.out.println(firstDroid.getName() + " загинув!");
                battleLogger.logDeath(firstDroid.getName());
                break;
            }


            if(firstDroid instanceof RepairDroid){
                ((RepairDroid) firstDroid).repair(firstDroid);
                battleLogger.logAction("repair", firstDroid.getName(),firstDroid.getName(), 0, ((RepairDroid) firstDroid).getREPAIR_POWER());
            }

            if(secondDroid instanceof RepairDroid){
                ((RepairDroid) secondDroid).repair(secondDroid);
                battleLogger.logAction("repair", secondDroid.getName(),secondDroid.getName(), 0, ((RepairDroid) secondDroid).getREPAIR_POWER());
            }
        }

        if (firstDroid.isAlive()){
            System.out.println(firstDroid.getName() + " переміг");
            battleLogger.logBattleResult(firstDroid.getName() + " переміг");
        }else {
            System.out.println(secondDroid.getName() + " переміг");
            battleLogger.logBattleResult(secondDroid.getName() + " переміг");
        }
    }
}
