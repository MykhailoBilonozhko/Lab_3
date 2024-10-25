package com.loggs;



public class BattleLog {
    private String action;    // "attack", "repair", "death", або "result"
    private String source;    // Хто виконує дію
    private String target;    // На кого спрямована дія (ворог або союзник)
    private int damageDealt;  // Урон (для атаки)
    private int healthRestored;  // Відновлене здоров'я (для лікування)

    public BattleLog(String action, String source, String target, int damageDealt, int healthRestored) {
        this.action = action;
        this.source = source;
        this.target = target;
        this.damageDealt = damageDealt;
        this.healthRestored = healthRestored;
    }

    // Геттери
    public String getAction() {
        return action;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public int getHealthRestored() {
        return healthRestored;
    }
}
