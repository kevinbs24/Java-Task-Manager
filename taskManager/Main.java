package taskManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	// Save tasks to a file
	public static void saveTasks(ArrayList<String> tasks) {
	    try {
	        FileWriter writer = new FileWriter("tasks.txt");
	        for (String task : tasks) {
	            writer.write(task + "\n");
	        }
	        writer.close();
	        System.out.println("Tasks saved to file.");
	    } catch (IOException e) {
	        System.out.println("Error saving tasks: " + e.getMessage());
	    }
	}

	// Load tasks from a file
	public static void loadTasks(ArrayList<String> tasks) {
	    try {
	        File file = new File("tasks.txt");
	        if (file.exists()) {
	            Scanner fileReader = new Scanner(file);
	            while (fileReader.hasNextLine()) {
	                String task = fileReader.nextLine();
	                tasks.add(task);
	            }
	            fileReader.close();
	            System.out.println("Tasks loaded from file.");
	        }
	    } catch (IOException e) {
	        System.out.println("Error loading tasks: " + e.getMessage());
	    }
	}


	public static void main(String[] args) {
		
		ArrayList<String> tasks = new ArrayList<>();
		loadTasks(tasks); // Load from file on startup
		Scanner scanner = new Scanner(System.in);
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

			int selection = scanner.nextInt();
			scanner.nextLine(); // Clear leftover newline

			switch (selection) {
			case 1:
				System.out.print("Enter task description: ");
				String newTask = scanner.nextLine();
				tasks.add(newTask);
				saveTasks(tasks);
				System.out.println("Task added.");
				break;

			case 2:
				if (tasks.isEmpty()) {
					System.out.println("No tasks yet.");
				} else {
					System.out.println("Your Tasks:");
					for (int i = 0; i < tasks.size(); i++) {
						System.out.println(i + ": " + tasks.get(i));
					}
				}
				break;

			case 3:
				System.out.print("Enter task number to update: ");
				int updateIndex = scanner.nextInt();
				scanner.nextLine();
				if (updateIndex >= 0 && updateIndex < tasks.size()) {
					System.out.print("Enter new task description: ");
					String updatedTask = scanner.nextLine();
					tasks.set(updateIndex, updatedTask);
					saveTasks(tasks);
					System.out.println("Task updated.");
				} else {
					System.out.println("Invalid task number.");
				}
				break;

			case 4:
				System.out.print("Enter task number to delete: ");
				int deleteIndex = scanner.nextInt();
				scanner.nextLine();
				if (deleteIndex >= 0 && deleteIndex < tasks.size()) {
					tasks.remove(deleteIndex);
					saveTasks(tasks);
					System.out.println("Task deleted.");
				} else {
					System.out.println("Invalid task number.");
				}
				break;

			case 5:
				running = false;
				System.out.println("Goodbye!");
				break;

			default:
				System.out.println("Invalid option. Please choose 1–5.");
				break;
			}
		}

		scanner.close();
	}
}
