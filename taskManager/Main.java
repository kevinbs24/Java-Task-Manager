package taskManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Main {

	/*
	 * gson.toJson(tasks, writer) converts your entire list of Task objects No loops
	 * No formatting logic Pure data serialization
	 */
	public static void saveTasks(ArrayList<Task> tasks) {
		Gson gson = new Gson();

		try (FileWriter writer = new FileWriter("tasks.json")) {
			gson.toJson(tasks, writer);
			System.out.println("Tasks saved to JSON file.");
		} catch (IOException e) {
			System.out.println("Error saving tasks: " + e.getMessage());
		}
	}

	/*
	 * Java loses generic type info at runtime (type erasure). This line: new
	 * TypeToken<ArrayList<Task>>() {}.getType() Tells Gson: “This is a list of Task
	 * objects, not just a list.”
	 */
	public static void loadTasks(ArrayList<Task> tasks) {
		File file = new File("tasks.json");

		if (!file.exists()) {
			return;
		}

		Gson gson = new Gson();

		try (Scanner scanner = new Scanner(file)) {
			String json = scanner.useDelimiter("\\A").next();

			ArrayList<Task> loadedTasks = gson.fromJson(json, new TypeToken<ArrayList<Task>>() {
			}.getType());

			if (loadedTasks != null) {
				tasks.addAll(loadedTasks);
			}

			System.out.println("Tasks loaded from JSON file.");
		} catch (Exception e) {
			System.out.println("Error loading tasks: " + e.getMessage());
		}
	}

	public static void main(String[] args) {

		ArrayList<Task> tasks = new ArrayList<>();
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
				int id = tasks.size() + 1;
				tasks.add(new Task(id, newTask));
				saveTasks(tasks);
				System.out.println("Task added.");
				break;

			case 2:
				if (tasks.isEmpty()) {
					System.out.println("No tasks yet.");
				} else {
					System.out.println("Your Tasks:");
					for (int i = 0; i < tasks.size(); i++) {
						for (Task task : tasks) {
							System.out.println(task);
						}
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

					Task task = tasks.get(updateIndex);
					task.setDescription(updatedTask);

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
