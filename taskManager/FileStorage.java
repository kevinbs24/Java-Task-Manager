package taskManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileStorage {

    private static final String FILE_NAME = "tasks.json";
    private final Gson gson = new Gson();

    public ArrayList<Task> loadTasks() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Scanner scanner = new Scanner(file)) {
            String json = scanner.useDelimiter("\\A").next();

            ArrayList<Task> tasks = gson.fromJson(
                json,
                new TypeToken<ArrayList<Task>>() {}.getType()
            );

            return tasks != null ? tasks : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveTasks(ArrayList<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}