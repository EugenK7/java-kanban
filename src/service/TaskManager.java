package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 0;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subTasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

    public Task createTask(Task task) { // создание задачи
        task.setId(generateId()); // получаем новый id
        tasks.put(task.getId(), task); // записываем задачу в таблицу
        return task;
    }

    public void updateTask(Task task) { // обновление задачи
        Task currentTask = tasks.get(task.getId()); // получаем задачу из таблицы по id
        if(currentTask == null) { // проверяем на пустую ссылку
            return;
        }
        currentTask.setName(task.getName()); // присваиваем имя из объекта с новой задачей
        currentTask.setDescription(task.getDescription()); // присваиваем описание из объекта с новой задачей
        currentTask.setStatus(task.getStatus()); // присваиваем статус из объекта с новой задачей
    }

    public Task getTaskById(int taskId) { // получение задачи по id
        return tasks.get(taskId);
    }

    public ArrayList<Task> getAllTasks() { // печать списка всех задач
        return new ArrayList<>(tasks.values());
    }

    public void deleteTaskById(int taskId) { // удаление задачи по id
        tasks.remove(taskId);
    }

    public void deleteAllTasks() { // удаление всех задач
        tasks.clear();
    }

    public Epic createEpic(Epic epic) { // создание эпика
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public void updateEpic(Epic epic) { // обновление эпика
        Epic currentEpic = epics.get(epic.getId());
        if(currentEpic == null) {
            return;
        }
        currentEpic.setName(epic.getName());
        currentEpic.setDescription(epic.getDescription());
    }

    public Epic getEpicById(int epicId) { // получение эпика по id
        return epics.get(epicId);
    }

    public ArrayList<Epic> getAllEpics() { // печать всех эпиков
        return new ArrayList<>(epics.values());
    }

    public void deleteEpicById(int epicId) { // удаление эпика по id
        Epic epic = epics.get(epicId); // находим эпик по id
        for(Integer id : epic.getSubTaskIds()) { // удаляем по списку id подзадач сами подзадачи
            subTasks.remove(id);
        }
        epics.remove(epicId); // удаляем эпик
    }

    public void deleteAllEpics() { // удаление всех эпиков
        subTasks.clear(); // удаляем все подзадачи
        epics.clear(); // удаляем все эпики
    }

    public Subtask createSubTask(Subtask subTask) { // создание подзадачи
        subTask.setId(generateId());
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId()); // находим эпик по эпик-id
        if(epic != null) {
            epic.getSubTaskIds().add(subTask.getId()); // добавляем id подзадачи в список к эпику
            calculateStatusEpic(epic); // рассчитываем статус эпика
            return subTask;
        }
        return null;
    }

    public void updateSubTask(Subtask subTask) { // обновление подзадачи
        Task currentSubTask = subTasks.get(subTask.getId());
        if(currentSubTask == null) {
            return;
        }
        currentSubTask.setName(subTask.getName());
        currentSubTask.setDescription(subTask.getDescription());
        currentSubTask.setStatus(subTask.getStatus());
        Epic epic = epics.get(subTask.getEpicId());
        calculateStatusEpic(epic);
    }

    public Task getSubTaskById(int subTaskId) { // получение подзадачи по id
        return subTasks.get(subTaskId);
    }

    public ArrayList<Subtask> getAllSubTasks() { // печать всех подзадач
        return new ArrayList<>(subTasks.values());
    }

    public void deleteSubTaskById(int subTaskId) { // удаление подзадачи по id
        Subtask subtask = subTasks.get(subTaskId); // находим подзадачу по id подзадачи
        Epic epic = epics.get(subtask.getEpicId()); // находим эпик по id эпика
        epic.getSubTaskIds().remove(subTaskId); // удаляем id подзадачи из списка в эпике
        subTasks.remove((Integer) subTaskId, subtask); // удаляем подзадачу
        calculateStatusEpic(epic); // обновляем статус эпика
    }

    public void deleteAllSubTasks() { // удаление всех подзадач
        for(Epic epic : epics.values()) { // очищаем списки id подзадач во всех эпиках
            epic.getSubTaskIds().clear();
            calculateStatusEpic(epic); // обновляем статус эпика
        }
        subTasks.clear(); // очищаем список подзадач
    }

    public ArrayList<Subtask> getAllSubTasksByEpicId(int epicId) { // получение списка всех подзадач по id эпика
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subTaskIds = epic.getSubTaskIds();
        ArrayList<Subtask> allSubtasks = new ArrayList<>();
        for (Integer subTaskId : subTaskIds) {
            Subtask subtask = subTasks.get(subTaskId);
            allSubtasks.add(subtask);
        }
        return allSubtasks;
    }

    private void calculateStatusEpic(Epic epic) { // расчет статуса эпика
        TaskStatus status = TaskStatus.NEW;
        boolean allNew = true;
        boolean allDone = true;
        ArrayList<Integer> subTaskIds = epic.getSubTaskIds();
        for (Integer subTaskId : subTaskIds) {
            Subtask subtask = subTasks.get(subTaskId);
            if (subtask.getStatus() != TaskStatus.NEW) { // найдена подзадача не в статусе новый
                allNew = false;
            }
            if (subtask.getStatus() != TaskStatus.DONE) { // найдена подзадача не в статусе выполнено
                allDone = false;
            }
        }
        if (allNew || subTasks.isEmpty()) {
            status = TaskStatus.NEW;
        } else if (allDone) {
            status = TaskStatus.DONE;
        } else {
            status = TaskStatus.IN_PROGRESS;
        }
        epic.setStatus(status);
    }

    private int generateId() {
        return ++id;
    }
}
