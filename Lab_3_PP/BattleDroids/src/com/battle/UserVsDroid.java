package com.battle;

import com.models.*;
import com.loggs.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UserVsDroid extends Battle{
    private ArrayList<Droid> userTeam;
    private ArrayList<Droid> enemyTeam;
    private Random random;
    private Scanner scanner;

    public UserVsDroid(ArrayList<Droid> userTeam, ArrayList<Droid> enemyTeam) {
        this.userTeam = userTeam;
        this.enemyTeam = enemyTeam;
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.battleLogger = new BattleLogger("log.json");  // Ініціалізуємо логгер з шляхом до файлу
    }

    public void start() {
        System.out.println("Бій починається!");
        boolean userTurn = true; // Прапорець для чергування ходів

        while (!userTeam.isEmpty() && !enemyTeam.isEmpty()) {
            if (userTurn) {
                userTurn();  // Хід гравця
            } else {
                enemyTurn();  // Хід комп'ютера
            }
            userTurn = !userTurn;  // Чергуємо хід
        }

        if (userTeam.isEmpty()) {
            System.out.println("Ви програли!");
            battleLogger.logBattleResult("Поразка команди 1");
        } else {
            System.out.println("Ви перемогли!");
            battleLogger.logBattleResult("Перемога команди 1");
        }

        // Записуємо лог бою у файл після завершення бою
        battleLogger.saveLogsToFile();
    }

    // Хід гравця
    private void userTurn() {
        System.out.println("Ваш хід. Оберіть дроїда, яким хочете походити:");
        showDroids(userTeam);

        int userDroidIndex = scanner.nextInt() - 1;
        Droid userDroid = userTeam.get(userDroidIndex);

        if (userDroid instanceof RepairDroid) {
            System.out.println("Цей дроїд може ремонтувати. Оберіть дію: 1 - Атака, 2 - Ремонт.");
            int action = scanner.nextInt();

            if (action == 2) {
                // Лікування союзного дроїда
                System.out.println("Оберіть союзного дроїда для ремонту:");
                showDroids(userTeam);

                int allyIndex = scanner.nextInt() - 1;
                Droid ally = userTeam.get(allyIndex);
                ((RepairDroid) userDroid).repair(ally);

                battleLogger.logAction("repair", userDroid.getName(), ally.getName(), 0, ((RepairDroid) userDroid).getREPAIR_POWER());
                return;  // Закінчуємо хід
            }
        }

        // Атака на дроїда супротивника
        System.out.println("Оберіть дроїда супротивника, якого хочете атакувати:");
        showDroids(enemyTeam);

        int enemyDroidIndex = scanner.nextInt() - 1;
        Droid enemyDroid = enemyTeam.get(enemyDroidIndex);

        userDroid.attack(enemyDroid);
        battleLogger.logAction("attack", userDroid.getName(), enemyDroid.getName(), userDroid.getDamage(), 0);

        if (!enemyDroid.isAlive()) {
            System.out.println(enemyDroid.getName() + " з команди супротивника загинув.");
            battleLogger.logDeath(enemyDroid.getName());
            enemyTeam.remove(enemyDroid);
        }
    }

    // Хід комп'ютера
    private void enemyTurn() {
        System.out.println("Хід команди супротивника.");

        Droid enemyDroid = getRandomDroid(enemyTeam);  // Випадковий дроїд з команди супротивника

        if (enemyDroid instanceof RepairDroid && random.nextBoolean()) {
            // Лікування союзного дроїда
            Droid ally = getRandomDroid(enemyTeam);
            ((RepairDroid) enemyDroid).repair(ally);

            battleLogger.logAction("repair", enemyDroid.getName(), ally.getName(), 0, ((RepairDroid) enemyDroid).getREPAIR_POWER());
        } else {
            // Атака на гравця
            Droid userDroid = getRandomDroid(userTeam);
            enemyDroid.attack(userDroid);
            battleLogger.logAction("attack", enemyDroid.getName(), userDroid.getName(), userDroid.getDamage(), 0);

            if (!userDroid.isAlive()) {
                System.out.println(userDroid.getName() + " з вашої команди загинув.");
                battleLogger.logDeath(userDroid.getName());
                userTeam.remove(userDroid);
            }
        }

    }

    // Вибір випадкового дроїда з команди
    private Droid getRandomDroid(List<Droid> team) {
        int randomIndex = random.nextInt(team.size());
        return team.get(randomIndex);
    }

    // Показати список дроїдів у команді
    private void showDroids(List<Droid> team) {
        for (int i = 0; i < team.size(); i++) {
            Droid droid = team.get(i);
            System.out.println((i + 1) + ". " + droid);
        }
    }
}
