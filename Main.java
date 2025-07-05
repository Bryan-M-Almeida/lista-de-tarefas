import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<String> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadTasksFromFile();

        int option;
        do {
            showMenu();
            option = getOption();

            switch (option) {
                case 1 -> {
                    addTask();
                    saveTasksToFile();
                }
                case 2 -> listTasks();
                case 3 -> {
                    removeTask();
                    saveTasksToFile();
                }
                case 0 -> System.out.println("Saindo... Até logo!");
                default -> System.out.println("Opção inválida, tenta de novo.");
            }
        } while (option != 0);
    }

    private static void showMenu() {
        System.out.println("\n=== TODO LIST ===");
        System.out.println("1 - Adicionar tarefa");
        System.out.println("2 - Listar tarefas");
        System.out.println("3 - Remover tarefa");
        System.out.println("0 - Sair");
        System.out.println("Escolha: ");
    }

    private static int getOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // opção inválida
        }
    }

    private static void addTask() {
        System.out.print("Digite a tarefa: ");
        String task = scanner.nextLine().trim();
        if (!task.isEmpty()) {
            tasks.add(task);
            System.out.println("Tarefa adicionada!");
        } else {
            System.out.println("Tarefa vazia, não adicionada.");
        }
    }

    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa para mostrar.");
        } else {
            System.out.println("Tarefas:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf("%d - %s\n", i + 1, tasks.get(i));
            }
        }
    }

    private static void removeTask() {
        listTasks();
        if (!tasks.isEmpty()) {
            System.out.print("Número da tarefa pra remover: ");
            int index = getOption();
            if (index > 0 && index <= tasks.size()) {
                tasks.remove(index - 1);
                System.out.println("Tarefa removida.");
            } else {
                System.out.println("Número inválido.");
            }
        }
    }

    private static void saveTasksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (String task : tasks) {
                writer.write(task);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    private static void loadTasksFromFile() {
        File file = new File("tasks.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    tasks.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar tarefas: " + e.getMessage());
        }
    }
}
