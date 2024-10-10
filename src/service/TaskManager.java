package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;

public interface TaskManager {

    Task createTask(Task task);

    void updateTask(Task task);

    Task getTaskById(int taskId);

    ArrayList<Task> getAllTasks();

    void deleteTaskById(int taskId);

    void deleteAllTasks();

    Epic createEpic(Epic epic);

    void updateEpic(Epic epic);

    Epic getEpicById(int epicId);

    ArrayList<Epic> getAllEpics();

    void deleteEpicById(int epicId);

    void deleteAllEpics();

    Subtask createSubTask(Subtask subTask);

    void updateSubTask(Subtask subTask);

    Task getSubTaskById(int subTaskId);

    ArrayList<Subtask> getAllSubTasks();

    void deleteSubTaskById(int subTaskId);

    void deleteAllSubTasks();

    ArrayList<Subtask> getAllSubTasksByEpicId(int epicId);

}
