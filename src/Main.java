import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager InMemoryTaskManager = new InMemoryTaskManager(new InMemoryHistoryManager());

        Task task1 = InMemoryTaskManager.createTask(new Task("Задача 1", "Описание", TaskStatus.NEW));
        System.out.println("Создание задачи: " + task1);

        Task task2 = InMemoryTaskManager.createTask(new Task("Задача 2", "Описание", TaskStatus.NEW));
        System.out.println("Создание задачи: " + task2);

        Epic epic1 = InMemoryTaskManager.createEpic(new Epic("Эпик 1", "опис эпик 1", TaskStatus.IN_PROGRESS));
        System.out.println("Создание эпика: " + epic1);

        Subtask subtask1 = InMemoryTaskManager.createSubTask(new Subtask
                ("Подздч 1", "Опис пдздч 1", TaskStatus.NEW, 3));
        System.out.println("Создание подзадачи 1: " + subtask1);

        Subtask subtask2 = InMemoryTaskManager.createSubTask(new Subtask
                ("Подздч 2", "Опис пдздч 2", TaskStatus.IN_PROGRESS, 3));
        System.out.println("Создание подзадачи 2: " + subtask2);

        Epic epic2 = InMemoryTaskManager.createEpic(new Epic("Эпик 2", "опис эпик 2", TaskStatus.NEW));
        System.out.println("Создание эпика 2: " + epic2);

        Subtask subtask3 = InMemoryTaskManager.createSubTask(new Subtask
                ("Подздч 3", "Опис пдздч 3", TaskStatus.IN_PROGRESS, 6));
        System.out.println("Создание подзадачи 3: " + subtask3);

        System.out.println("Список всех эпиков:" + InMemoryTaskManager.getAllEpics());

        System.out.println("Список всех задач:" + InMemoryTaskManager.getAllTasks());

        System.out.println("Список всех подзадач:" + InMemoryTaskManager.getAllSubTasks());

        InMemoryTaskManager.updateTask(new Task
                ("нов имя зад 1", "нов. опис. зад. 1", TaskStatus.IN_PROGRESS));

        InMemoryTaskManager.updateEpic(new Epic
                ("нов. назв эп. 1", "нов. опис. эп. 1", TaskStatus.DONE));

        System.out.println("Список всех эпиков:" + InMemoryTaskManager.getAllEpics());

        System.out.println("Список всех задач:" + InMemoryTaskManager.getAllTasks());

        InMemoryTaskManager.updateSubTask(new Subtask
                ("Нов назв подздч 1", "нов опис пдздч1", TaskStatus.DONE, 3));

        InMemoryTaskManager.updateSubTask(new Subtask
                ("Нов назв подздч 2", "нов опис пдздч2", TaskStatus.DONE, 3));

        System.out.println("Список всех эпиков:" + InMemoryTaskManager.getAllEpics());

        InMemoryTaskManager.deleteTaskById(1);

       // taskManager.deleteEpicById(3);

        System.out.println("Список всех эпиков:" + InMemoryTaskManager.getAllEpics());


        System.out.println("Список всех задач:" + InMemoryTaskManager.getAllTasks());

        Subtask subtask7 = InMemoryTaskManager.createSubTask(new Subtask
                ("Подздч48", "Опис пдздч12", TaskStatus.NEW, 9));
        System.out.println("Создание подзадачи 45: " + subtask7);

        System.out.println("Список всех эпиков:" + InMemoryTaskManager.getAllEpics());
    }
}
