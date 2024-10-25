package com.loggs;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BattleLogger {
    private List<BattleLog> battleLog;
    private String logFilePath;

    public BattleLogger(String logFilePath) {
        this.battleLog = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    // Метод для запису дії в лог
    public void logAction(String action, String source, String target, int damageDealt, int healthRestored) {
        BattleLog logEntry = new BattleLog(action, source, target, damageDealt, healthRestored);
        battleLog.add(logEntry);
    }

    // Метод для логування смерті дроїда
    public void logDeath(String droidName) {
        BattleLog logEntry = new BattleLog("death", droidName, "", 0, 0);
        battleLog.add(logEntry);
    }

    // Метод для запису результату бою (перемога чи поразка)
    public void logBattleResult(String result) {
        BattleLog logEntry = new BattleLog("result", result, "", 0, 0);
        battleLog.add(logEntry);
    }

    // Метод для збереження логів у JSON-файл
    public void saveLogsToFile() {
        try (FileWriter writer = new FileWriter(logFilePath)) {
            Gson gson = new Gson();
            gson.toJson(battleLog, writer);
            System.out.println("Логи бою збережено у файл.");
        } catch (IOException e) {
            System.err.println("Помилка запису файлу: " + e.getMessage());
        }
    }

    // Метод для відтворення бою з логів у JSON-файлі
    public void replayLogs() {
        try (FileReader reader = new FileReader(logFilePath)) {
            Gson gson = new Gson();
            Type logListType = new TypeToken<List<BattleLog>>() {}.getType();
            List<BattleLog> battleLog = gson.fromJson(reader, logListType);
            
            System.out.println("Відтворення бою:");
            for (BattleLog logEntry : battleLog) {
                
                switch (logEntry.getAction()) {
                    case "attack" -> System.out.println(logEntry.getSource() + " атакує " + logEntry.getTarget() + " та завдає " + logEntry.getDamageDealt() + " шкоди.");
                    case "repair" -> System.out.println(logEntry.getSource() + " ремонтує " + logEntry.getTarget() + " та відновлює " + logEntry.getHealthRestored() + " здоров'я.");
                    case "death" -> System.out.println(logEntry.getSource() + " загинув.");
                    case "result" -> System.out.println("Результат бою: " + logEntry.getSource());
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }
}
