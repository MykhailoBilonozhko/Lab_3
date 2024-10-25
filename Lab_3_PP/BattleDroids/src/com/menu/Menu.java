package com.menu;

import com.battle.Battle;
import com.battle.OneVsOne;
import com.battle.TeamVsTeam;
import com.battle.UserVsDroid;
import com.models.BattleDroid;
import com.models.Droid;
import com.models.HeavyDroid;
import com.models.RepairDroid;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private ArrayList<Droid> droids = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private Battle battle;

    public void showMenu(){
        while (true){
            System.out.println("1. Створити дроїда");
            System.out.println("2. Показати список дроїдів");
            System.out.println("3. Бій 1 на 1");
            System.out.println("4. Командний бій");
            System.out.println("5. Гравець проти дроіда");
            System.out.println("6. Записати бій у файл");
            System.out.println("7. Відтворити бій із файлу");
            System.out.println("8. Вийти з програми");

            int choice = scanner.nextInt(); scanner.nextLine();
            switch (choice){
                case 1 -> createDroid();
                case 2 -> showDroids();
                case 3 -> startOneVsOneBattle();
                case 4 -> startTeamVsTeamBattle();
                case 5 -> startUserVsBot();
                case 6 -> logBattle();
                case 7 -> replayBattle();
                case 8 -> System.exit(0);
            }
        }
    }

    private void createDroid() {
        System.out.println("Оберіть дроіда, щоб створити: ");
        System.out.println("1. Battle Droid");
        System.out.println("2. Repair Droid");
        System.out.println("3. Heavy Droid");

        int type = scanner.nextInt(); scanner.nextLine();
        System.out.println("Введіть ім'я дроїда:");
        String name = scanner.nextLine();

        Droid droid;
        switch (type){
            case 1:
                droid = new BattleDroid(name);
                break;
            case 2:
                droid = new RepairDroid(name);
                break;
            case 3:
                droid = new HeavyDroid(name);
                break;
            default:
                System.out.println("Не правильний тип дроіда. Дроід не створено.");
                return;
        }

        droids.add(droid);
        System.out.println("Створений дроід: " + droid);
    }

    private void showDroids() {
        if (droids.isEmpty()) {
            System.out.println("Список дроїдів порожній.");
        } else {
            for (int i = 0; i < droids.size(); i++) {
                System.out.println((i+1) + " " +droids.get(i).getName());
            }
        }
    }

    private void startOneVsOneBattle(){
        if (droids.size() < 2){
            System.out.println("Недостатньо дроідів для бою");
            return;
        }

        Droid FirstDroid;
        Droid SecondDroid;

        System.out.println("Оберіть першого дроіда:");
        showDroids();
        int droidIndex = scanner.nextInt(); scanner.nextLine();
        FirstDroid = droids.remove(droidIndex - 1);

        System.out.println("Оберіть другого дроіда:");
        showDroids();
        droidIndex = scanner.nextInt(); scanner.nextLine();
        SecondDroid = droids.remove(droidIndex - 1);

        battle = new OneVsOne(FirstDroid, SecondDroid);
        battle.start();

        droids.add(FirstDroid);
        droids.add(SecondDroid);
    }

    private void startTeamVsTeamBattle(){
        if(droids.size() < 4){
            System.out.println("Недостатньо дроідів для бою");
            return;
        }

        System.out.print("Скільки дроідів в одній команді?: ");
        int teamSize = scanner.nextInt(); scanner.nextLine();

        if (teamSize * 2 > droids.size()) {
            System.out.println("Недостатньо дроїдів для формування двох команд такого розміру.");
            return;
        }

        ArrayList<Droid> team1 = new ArrayList<>();
        ArrayList<Droid> team2 = new ArrayList<>();

        CreateTeams(team1, team2, teamSize);

        battle = new TeamVsTeam(team1, team2);
        battle.start();

        droids.addAll(team1);
        droids.addAll(team2);
    }

    private void startUserVsBot(){
        if(droids.size() < 4){
            System.out.println("Недостатньо дроідів для бою");
            return;
        }

        System.out.print("Скільки дроідів в одній команді?: ");
        int teamSize = scanner.nextInt(); scanner.nextLine();

        if (teamSize * 2 > droids.size()) {
            System.out.println("Недостатньо дроїдів для формування двох команд такого розміру.");
            return;
        }

        ArrayList<Droid> team1 = new ArrayList<>();
        ArrayList<Droid> team2 = new ArrayList<>();

        CreateTeams(team1, team2, teamSize);

        battle = new UserVsDroid(team1, team2);
        battle.start();

        droids.addAll(team1);
        droids.addAll(team2);
    }

    private void CreateTeams(ArrayList<Droid> team1, ArrayList<Droid> team2, int teamSize){
        System.out.println("Оберіть дроїдів для першої команди:");
        for (int i = 0; i < teamSize; i++) {
            showDroids();
            int droidIndex = scanner.nextInt() - 1; scanner.nextLine();
            team1.add(droids.remove(droidIndex));
        }

        // Формуємо другу команду
        System.out.println("Оберіть дроїдів для другої команди:");
        for (int i = 0; i < teamSize; i++) {
            showDroids();
            int droidIndex = scanner.nextInt() - 1; scanner.nextLine();
            team2.add(droids.remove(droidIndex));
        }
    }

    private void logBattle() {
        battle.getBattleLogger().saveLogsToFile();
    }

    private void replayBattle() {
        battle.getBattleLogger().replayLogs();
    }
}

