package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    InMemoryTaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());

    @Test
    @DisplayName("Экземпляры класса Task равны друг другу, если равен их id")
    public void shouldEqualsTasksWithEqualsId() {
        Task taskActual = taskManager.createTask(new Task("Задача 1", "Описание 1", TaskStatus.NEW));
        Task taskExpected = taskManager.createTask(new Task("Задача 2", "Описание 2", TaskStatus.NEW));
        taskExpected.setId(taskActual.getId());
        assertEquals(taskExpected, taskActual, "Объекты Task не совпадают");
    }

    @Test
    @DisplayName("Экземпляры класса Epic равны друг другу, если равен их id")
    public void shouldEqualsEpicsWithEqualsId() {
        Epic epicActual = taskManager.createEpic(new Epic("Эпик 1", "Описание 1", TaskStatus.NEW));
        Epic epicExpected = taskManager.createEpic(new Epic("Эпик 2", "Описание 2", TaskStatus.NEW));
        epicExpected.setId(epicActual.getId());
        assertEquals(epicExpected, epicActual, "Объекты Epic не совпадают");
    }

    @Test
    @DisplayName("Экземпляры класса Subtask равны друг другу, если равен их id")
    public void shouldEqualsSubtaskWithEqualsId() {
        Epic epic = taskManager.createEpic(new Epic("Эпик 1", "Описание 1", TaskStatus.NEW));
        Subtask subtaskActual = taskManager.createSubTask(new Subtask("Подзадача 1", "Описание 1", TaskStatus.NEW, epic.getId()));
        Subtask subtaskExpected = taskManager.createSubTask(new Subtask("Подзадача 2", "Описание 3", TaskStatus.IN_PROGRESS, epic.getId()));
        subtaskExpected.setId(subtaskActual.getId());
        assertEquals(subtaskExpected, subtaskActual, "Объекты Subtask не совпадают");
    }

    @Test
    @DisplayName("Поля задачи не изменяются при добавлении в менеджер")
    public void shouldNotChangeTaskWhenAddingItToTheManager() {
        Task task = taskManager.createTask(new Task("Задача_1", "Описание_1", TaskStatus.NEW));

        assertEquals(task.getName(), "Задача_1", "Имя задачи изменилось");
        assertEquals(task.getDescription(), "Описание_1", "Описание задачи изменилось");
        assertEquals(task.getStatus(), TaskStatus.NEW, "Статус задачи изменился");
    }

}