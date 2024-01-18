package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    static final String FILE_TASKS= "tasks.csv";
    static String[][] tab; //tablica do wczytywania zadan z pliku
    public static void main(String[] args) {
        tab = loadData(FILE_TASKS);
        panel();
        selectOption();

    }
    public static void panel() {
        String[] panelList = {"add","remove","list","exit"};
            System.out.println(ConsoleColors.BLUE + "Please select an option");
        for (String a : panelList) {
            System.out.println(ConsoleColors.RESET + a);
        }

    }
    public static String[][] loadData(String fileName){
        File file = new File(FILE_TASKS);
        int numberOfTasks = 0;
        String line = new String();
        StringBuilder sb = new StringBuilder();
        String[][] tasks = null;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                numberOfTasks++;
                line = scanner.nextLine();
                sb.append(line).append("##");
            }
            sb.delete(sb.length()-2,sb.length());
            String everyLine = sb.toString();
            String[] rows = everyLine.split("##");
            tasks = new String[numberOfTasks][line.split(",").length];
            for (int i = 0; i < rows.length; i++) {
               String[] split = rows[i].split(",");
                for (int j = 0; j < split.length; j++) {
                    tasks[i][j] = split[j];
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("File doesn't exist");
            System.exit(0);
        }
        return tasks;

    }
    public static void selectOption(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
            switch (input) {
                case "add":
                    System.out.println("add");
                    addTask();
                    break;
                case "remove":
                    System.out.println("remove");
                    removeTask();
                    break;
                case "list":
                    System.out.println("list");
                    listTask(tab);
                    panel();
                    selectOption();
                    break;
                case "exit":
                    System.out.println("exit");
                    saveToFile(FILE_TASKS);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.out.println(ConsoleColors.RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select correct option");
                    panel();
                    selectOption();
            }


    }
    public static void addTask(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String taskdescription = scanner.nextLine();
        System.out.println("Please add task due date");
        String date = (" " +scanner.nextLine());
        System.out.println("Is your task important: true/false");
        String importance = (" " + scanner.nextLine());
        tab = Arrays.copyOf(tab,tab.length +1);
        tab[tab.length - 1] = new String[3];
        tab[tab.length - 1][0] = taskdescription;
        tab[tab.length - 1][1] = date;
        tab[tab.length - 1][2] = importance;
        panel();
        selectOption();

    }
    public static void removeTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, select number to remove");
        String input = scanner.nextLine();
        if (NumberUtils.isParsable(input)) {
            try {
                tab = ArrayUtils.remove(tab, Integer.parseInt(input));
                System.out.println("Task was successfully deleted");
                panel();
                selectOption();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Incorrect argument,argument should be from 0 to " + (tab.length - 1));
            }
        } else {
            System.out.println("Incorrect argument, this is not a number");
        }
        removeTask();

    }
    public static void listTask(String[][] args) {

        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j]);
            }
            System.out.println();
        }


    }
    public static void saveToFile(String fileName) {
        try (PrintWriter printWriter = new PrintWriter(FILE_TASKS)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tab.length; i++) {
                for (int j = 0; j < tab[i].length; j++) {
                    sb.append(tab[i][j]).append(",");
                }
                sb.deleteCharAt(sb.length() -1).append("\n");
            }
            printWriter.append(sb.toString());

        }catch (FileNotFoundException e) {
            System.out.println("Something went wrong, write error");
        }


    }
}
