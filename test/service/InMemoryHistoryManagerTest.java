package service;

import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

//    InMemoryTaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
//    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();


    HistoryManager historyManager = new InMemoryHistoryManager();
    TaskManager taskManager = new InMemoryTaskManager(historyManager);

    @Test
    public void shouldAddTasksInHistory() {
        Task task1 = taskManager.createTask(new Task("Задача 1", "Описание", TaskStatus.NEW));
        Task task2 = taskManager.createTask(new Task("Задача 2", "Описание", TaskStatus.NEW));
        Task task3 = taskManager.createTask(new Task("Задача 3", "Описание", TaskStatus.NEW));
        Task task4 = taskManager.createTask(new Task("Задача 4", "Описание", TaskStatus.NEW));
        Task task5 = taskManager.createTask(new Task("Задача 5", "Описание", TaskStatus.NEW));

        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task5.getId());
        List<Task> listHistory = historyManager.getHistory();

        assertEquals(listHistory.size(), 3, "Длина листа истории не совпадает");
        assertEquals(listHistory.get(0), task1, "Задача 1 не на первой позиции в листе");
        assertEquals(listHistory.get(1), task3, "Задача 3 не на второй  позиции в листе");
        assertEquals(listHistory.get(2), task5, "Задача 1 не на третьей позиции в листе");
    }

    @Test
    public void shouldAdd10TasksInHistory() {
        Task task1 = taskManager.createTask(new Task("Задача 1", "Описание", TaskStatus.NEW));
        Task task2 = taskManager.createTask(new Task("Задача 2", "Описание", TaskStatus.NEW));
        Task task3 = taskManager.createTask(new Task("Задача 3", "Описание", TaskStatus.NEW));
        Task task4 = taskManager.createTask(new Task("Задача 4", "Описание", TaskStatus.NEW));
        Task task5 = taskManager.createTask(new Task("Задача 5", "Описание", TaskStatus.NEW));
        Task task6 = taskManager.createTask(new Task("Задача 6", "Описание", TaskStatus.NEW));
        Task task7 = taskManager.createTask(new Task("Задача 7", "Описание", TaskStatus.NEW));
        Task task8 = taskManager.createTask(new Task("Задача 8", "Описание", TaskStatus.NEW));
        Task task9 = taskManager.createTask(new Task("Задача 9", "Описание", TaskStatus.NEW));
        Task task10 = taskManager.createTask(new Task("Задача 10", "Описание", TaskStatus.NEW));
        Task task11 = taskManager.createTask(new Task("Задача 11", "Описание", TaskStatus.NEW));
        Task task12 = taskManager.createTask(new Task("Задача 12", "Описание", TaskStatus.NEW));

        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task4.getId());
        taskManager.getTaskById(task5.getId());
        taskManager.getTaskById(task6.getId());
        taskManager.getTaskById(task7.getId());
        taskManager.getTaskById(task8.getId());
        taskManager.getTaskById(task9.getId());
        taskManager.getTaskById(task10.getId());
        taskManager.getTaskById(task11.getId());
        taskManager.getTaskById(task12.getId());
        List<Task> listHistory = historyManager.getHistory();


        assertEquals(listHistory.size(), 10, "Длина листа истории больше 10");
        assertEquals(task3, listHistory.get(0), "На певой позиции не задача 3");
        assertEquals(task12, listHistory.get(9), "На последней позиции не задача 12");
    }

}