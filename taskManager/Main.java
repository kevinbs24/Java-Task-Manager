package taskManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TaskService taskService = new TaskService(new FileStorage());
        boolean running = true;

        while (running) {
            System.out.println("\n-----------------");
            System.out.println("Task Manager");
            System.out.println("1. Create Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.println("-----------------");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    taskService.addTask(scanner.nextLine());
                    System.out.println("Task added.");
                    break;

                case 2:
                    taskService.getAllTasks()
                            .forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Enter task ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter new description: ");
                    String desc = scanner.nextLine();

                    if (!taskService.updateTask(updateId, desc)) {
                        System.out.println("Task not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter task ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();

                    if (!taskService.deleteTask(deleteId)) {
                        System.out.println("Task not found.");
                    }
                    break;

                case 5:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}
