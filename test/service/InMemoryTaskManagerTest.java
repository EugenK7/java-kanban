package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class InMemoryTaskManagerTest {

    InMemoryTaskManager taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());

    @Test
    @DisplayName("Создание задачи")
    public void shouldBeCreatedTask() {
        Task task = taskManager.createTask(new Task("Задача тест", "Описание", TaskStatus.NEW));

        assertEquals(task, taskManager.getTaskById(task.getId()), "Задача не добавлена");
    }

    @Test
    @DisplayName("Получение задачи по ID")
    public void shouldGetTaskById () {
        Task task = taskManager.createTask(new Task("Задача тест", "Описание", TaskStatus.NEW));

        Task taskExpected = taskManager.getTaskById(task.getId());

        assertEquals(taskExpected, task, "Задача не получена по ID");
    }

    @Test
    @DisplayName("Получение списка задач")
    public void shouldGetAllTasks() {
        Task task1 = taskManager.createTask(new Task("Задача 1", "Описание1", TaskStatus.NEW));
        Task task2 = taskManager.createTask(new Task("Задача 2", "Описание2", TaskStatus.NEW));

        ArrayList<Task> taskListActual = new ArrayList<>();
        taskListActual.add(task1);
        taskListActual.add(task2);
        ArrayList<Task> taskListExpected = taskManager.getAllTasks();

        assertEquals(taskListActual.get(0).getId(), taskListExpected.get(0).getId(),
                "ID задач не сопадают");
        assertEquals(taskListActual.get(0).getName(), taskListExpected.get(0).getName(),
                "Имена задач не сопадают");
        assertEquals(taskListActual.get(0).getDescription(), taskListExpected.get(0).getDescription(),
                "Описания задач не сопадают");
        assertEquals(taskListActual.get(0).getDescription(), taskListExpected.get(0).getDescription(),
                "Статусы задач не сопадают");

        assertEquals(taskListActual.get(1).getId(), taskListExpected.get(1).getId(),
                "ID задач не сопадают");
        assertEquals(taskListActual.get(1).getName(), taskListExpected.get(1).getName(),
                "Имена задач не сопадают");
        assertEquals(taskListActual.get(1).getDescription(), taskListExpected.get(1).getDescription(),
                "Описания задач не сопадают");
        assertEquals(taskListActual.get(1).getDescription(), taskListExpected.get(1).getDescription(),
                "Статусы задач не сопадают");
    }

    @Test
    @DisplayName("Удаление задачи по ID")
    public void shouldDeleteTaskById() {
        Task task1 = taskManager.createTask(new Task("Задача-1", "Описание_1", TaskStatus.NEW));
        taskManager.deleteTaskById(task1.getId());
        assertNull(taskManager.getTaskById(task1.getId()), "Задача не удалена");
    }

    @Test
    @DisplayName("Удаление всех задач")
    public void shouldDeleteAllTasks() {
        Task task1 = taskManager.createTask(new Task("Задача 1", "Описание1", TaskStatus.NEW));
        Task task2 = taskManager.createTask(new Task("Задача 2", "Описание2", TaskStatus.NEW));

        taskManager.deleteAllTasks();

        assertNull(taskManager.getTaskById(task1.getId()), "Задача 1 не удалена");
        assertNull(taskManager.getTaskById(task2.getId()), "Задача 2 не удалена");
    }

    @Test
    @DisplayName("Создание эпика")
    public void shouldBeCreatedEpic() {
        Epic epic = taskManager.createEpic(new Epic("Эпик тест", "Назв эпика", TaskStatus.NEW));

        assertEquals(epic, taskManager.getEpicById(epic.getId()), "Эпик не добавлен");
    }

    @Test
    @DisplayName("Получение эпика по ID")
    public void shouldGetEpicById () {
        Epic epic = taskManager.createEpic(new Epic("Эпик", "Назв эпик", TaskStatus.NEW));

        Epic epicExpected = taskManager.getEpicById(epic.getId());

        assertEquals(epicExpected, epic, "Эпик не получен по ID");
    }

    @Test
    @DisplayName("Удаление эпика по ID")
    public void deleteEpicByIdAndItsSubtasks() {
        Epic epic = taskManager.createEpic(new Epic("Эпик тест", "Назв эпика", TaskStatus.NEW));
        Subtask subtask1 = taskManager.createSubTask(new Subtask("Назв пдзч1", "Опис пдзч1", TaskStatus.NEW, 1));
        Subtask subtask2 = taskManager.createSubTask(new Subtask("Назв пдзч2", "Опис пдзч2", TaskStatus.NEW, 1));

        taskManager.deleteEpicById(epic.getId());

        assertNull(taskManager.getEpicById(epic.getId()), "Эпик не удален");
        assertNull(taskManager.getSubTaskById(subtask1.getId()), "Подзадача 1 не удалена");
        assertNull(taskManager.getSubTaskById(subtask2.getId()), "Подзадача 2 не удалена");
    }

    @Test
    @DisplayName("Удаление всех эпиков")
    public void shouldDeleteAllEpicsAndAllItsSubtasks() {
        Epic epic = taskManager.createEpic(new Epic("Эпик тест", "Назв эпика", TaskStatus.NEW));
        Subtask subtask1 = taskManager.createSubTask(new Subtask("Назв пдзч1", "Опис пдзч1", TaskStatus.NEW, 1));
        Subtask subtask2 = taskManager.createSubTask(new Subtask("Назв пдзч2", "Опис пдзч2", TaskStatus.NEW, 1));
        Epic epic2 = taskManager.createEpic(new Epic("Эпик тест", "Назв эпика", TaskStatus.NEW));
        Subtask subtask3 = taskManager.createSubTask(new Subtask("Назв пдзч1", "Опис пдзч1", TaskStatus.NEW, 4));
        Subtask subtask4 = taskManager.createSubTask(new Subtask("Назв пдзч2", "Опис пдзч2", TaskStatus.NEW, 4));

        taskManager.deleteAllEpics();

        assertNull(taskManager.getEpicById(epic.getId()), "Эпик 1 не удален");
        assertNull(taskManager.getSubTaskById(subtask1.getId()), "Подзадача 1 не удалена");
        assertNull(taskManager.getSubTaskById(subtask2.getId()), "Подзадача 2 не удалена");
        assertNull(taskManager.getEpicById(epic2.getId()), "Эпик 2 не удален");
        assertNull(taskManager.getSubTaskById(subtask3.getId()), "Подзадача 3 не удалена");
        assertNull(taskManager.getSubTaskById(subtask4.getId()), "Подзадача 4 не удалена");
    }

    @Test
    @DisplayName("Создание подзадачи")
    public void shouldBeCreatedSubtask() {
        Epic epic = taskManager.createEpic(new Epic("Эпик тест", "Назв эпика", TaskStatus.NEW));
        Subtask subtask = taskManager.createSubTask(new Subtask("Назв пдзч", "Опис пдзч", TaskStatus.NEW, 1));

        assertEquals(subtask, taskManager.getSubTaskById(subtask.getId()), "Подзадача не добавлена");
    }

    @Test
    @DisplayName("Полчение подзадачи по ID")
    public void shouldGetSubtaskById () {
        Epic epic = taskManager.createEpic(new Epic("Эпик тест", "Назв эпика", TaskStatus.NEW));
        Subtask subtask = taskManager.createSubTask(new Subtask("Назв пдзч", "Опис пдзч", TaskStatus.NEW, 1));

        Task subtaskExpected = taskManager.getSubTaskById(subtask.getId());

        assertEquals(subtask, subtaskExpected, "Подзадача не получена по ID");
    }

    @Test
    @DisplayName("Обновление статуса эпика когда обновляются его подзадачи")
    public void shouldСalculateEpicStatusWhenUpdatingSubtasks() {
        Epic epic = taskManager.createEpic(new Epic("Эпик тест", "Назв эпика", TaskStatus.NEW));
        Subtask subtask1 = taskManager.createSubTask(new Subtask("Назв пдзч1", "Опис пдзч1", TaskStatus.NEW, 1));
        Subtask subtask2 = taskManager.createSubTask(new Subtask("Назв пдзч2", "Опис пдзч2", TaskStatus.NEW, 1));
        Subtask updateSubtask1 = taskManager.createSubTask(new Subtask("Нов пдзч1", "Опис пдзч1", TaskStatus.NEW, 1));
        Subtask updateSubtask2 = taskManager.createSubTask(new Subtask("Нов пдзч2", "Опис пдзч2", TaskStatus.NEW, 1));

        updateSubtask1.setId(subtask1.getId());
        updateSubtask1.setStatus(TaskStatus.DONE);
        updateSubtask2.setId(subtask2.getId());
        updateSubtask2.setStatus(TaskStatus.DONE);
        taskManager.updateSubTask(updateSubtask1);
        taskManager.updateSubTask(updateSubtask2);

        assertEquals(epic.getStatus(), TaskStatus.DONE, "Статус эпика не обновился");
    }
}