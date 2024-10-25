package com.battle;

import com.loggs.BattleLogger;
import com.models.Droid;
import com.models.RepairDroid;

import java.util.ArrayList;
import java.util.Random;

public class TeamVsTeam extends Battle{
    private ArrayList<Droid> firstTeam;
    private ArrayList<Droid> secndTeam;
    private Random random;

    public TeamVsTeam(ArrayList<Droid> team1,ArrayList<Droid> team2){
        this.firstTeam = team1;
        this.secndTeam = team2;
        battleLogger = new BattleLogger("log.json");
        this.random = new Random();
    }



    public void start(){
        System.out.println("Бій починається!");
        boolean flag = true;
        while (!firstTeam.isEmpty() && !secndTeam.isEmpty()){
            if(flag){
                Bot1Turn();
            }else{
                Bot2Turn();
            }
            flag = !flag;
        }

        if (firstTeam.isEmpty()) {
            System.out.println("Перемога першої команди!");
            battleLogger.logBattleResult("Перемога першої команди!");
        } else {
            System.out.println("Перемога другої команди!");
            battleLogger.logBattleResult("Перемога другої команди!");
        }
    }

    private void Bot1Turn(){
        Droid bot1Droid = getRandomDroid(firstTeam);

        if(bot1Droid instanceof RepairDroid && random.nextBoolean()){
            Droid ally = getRandomDroid(firstTeam);
            ((RepairDroid) bot1Droid).repair(ally);

            battleLogger.logAction("repair", bot1Droid.getName(), ally.getName(), 0, ((RepairDroid) bot1Droid).getREPAIR_POWER());
        }else{
            Droid bot2Droid = getRandomDroid(secndTeam);
            battleLogger.logAction("attack", bot1Droid.getName(), bot2Droid.getName(), bot1Droid.attack(bot2Droid), 0);

            if(!bot2Droid.isAlive()){
                System.out.println(bot2Droid.getName() + "з другої команди загинув");
                battleLogger.logDeath(bot2Droid.getName());
                secndTeam.remove(bot2Droid);
            }
        }
    }

    private void Bot2Turn(){
        Droid bot2Droid = getRandomDroid(secndTeam);

        if(bot2Droid instanceof RepairDroid && random.nextBoolean()){
            Droid ally = getRandomDroid(firstTeam);
            ((RepairDroid) bot2Droid).repair(ally);

            battleLogger.logAction("repair", bot2Droid.getName(), ally.getName(), 0, ((RepairDroid) bot2Droid).getREPAIR_POWER());
        }else{
            Droid bot1Droid = getRandomDroid(secndTeam);
            battleLogger.logAction("attack", bot2Droid.getName(), bot2Droid.getName(), bot2Droid.attack(bot1Droid), 0);

            if(!bot1Droid.isAlive()){
                System.out.println(bot1Droid.getName() + "з першої команди загинув");
                battleLogger.logDeath(bot1Droid.getName());
                firstTeam.remove(bot1Droid);
            }
        }
    }

    private Droid getRandomDroid(ArrayList<Droid> list){
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
