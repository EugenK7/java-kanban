import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.HistoryManager;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);

        Task task1 = taskManager.createTask(new Task("Задача 1", "Описание", TaskStatus.NEW));
        Task task2 = taskManager.createTask(new Task("Задача 2", "Описание", TaskStatus.NEW));
        Task task3 = taskManager.createTask(new Task("Задача 3", "Описание", TaskStatus.NEW));

        historyManager.add(task1);
        historyManager.add(task1);

        //System.out.println();
    }
}
