package taskManager;

import java.util.ArrayList;

public class TaskService {

    private final ArrayList<Task> tasks;
    private final FileStorage storage;

    public TaskService(FileStorage storage) {
        this.storage = storage;
        this.tasks = storage.loadTasks();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public void addTask(String description) {
        int id = generateNextId();
        tasks.add(new Task(id, description));
        storage.saveTasks(tasks);
    }

    public boolean updateTask(int id, String newDescription) {
        Task task = findById(id);
        if (task == null) return false;

        task.setDescription(newDescription);
        storage.saveTasks(tasks);
        return true;
    }

    public boolean deleteTask(int id) {
        Task task = findById(id);
        if (task == null) return false;

        tasks.remove(task);
        storage.saveTasks(tasks);
        return true;
    }

    private Task findById(int id) {
        return tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int generateNextId() {
        return tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    }
}