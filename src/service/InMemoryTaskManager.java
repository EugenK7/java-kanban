package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subTasks;
    private List<Task> listOfViewedTasks;

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.listOfViewedTasks = new ArrayList<>(10);
    }

    @Override
    public Task createTask(Task task) { // создание задачи
        task.setId(generateId()); // получаем новый id
        tasks.put(task.getId(), task); // записываем задачу в таблицу
        return task;
    }

    @Override
    public void updateTask(Task task) { // обновление задачи
        Task currentTask = tasks.get(task.getId()); // получаем задачу из таблицы по id
        if(currentTask == null) { // проверяем на пустую ссылку
            return;
        }
        currentTask.setName(task.getName()); // присваиваем имя из объекта с новой задачей
        currentTask.setDescription(task.getDescription()); // присваиваем описание из объекта с новой задачей
        currentTask.setStatus(task.getStatus()); // присваиваем статус из объекта с новой задачей
    }

    @Override
    public Task getTaskById(int taskId) {// получение задачи по id
        listOfViewedTasks.add(tasks.get(taskId));
        return tasks.get(taskId);
    }

    @Override
    public ArrayList<Task> getAllTasks() { // печать списка всех задач
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteTaskById(int taskId) { // удаление задачи по id
        tasks.remove(taskId);
    }

    @Override
    public void deleteAllTasks() { // удаление всех задач
        tasks.clear();
    }

    @Override
    public Epic createEpic(Epic epic) { // создание эпика
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public void updateEpic(Epic epic) { // обновление эпика
        Epic currentEpic = epics.get(epic.getId());
        if(currentEpic == null) {
            return;
        }
        currentEpic.setName(epic.getName());
        currentEpic.setDescription(epic.getDescription());
    }

    @Override
    public Epic getEpicById(int epicId) { // получение эпика по id
        listOfViewedTasks.add(epics.get(epicId));
        return epics.get(epicId);
    }

    @Override
    public ArrayList<Epic> getAllEpics() { // печать всех эпиков
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteEpicById(int epicId) { // удаление эпика по id
        Epic epic = epics.get(epicId); // находим эпик по id
        for(Integer id : epic.getSubTaskIds()) { // удаляем по списку id подзадач сами подзадачи
            subTasks.remove(id);
        }
        epics.remove(epicId); // удаляем эпик
    }

    @Override
    public void deleteAllEpics() { // удаление всех эпиков
        subTasks.clear(); // удаляем все подзадачи
        epics.clear(); // удаляем все эпики
    }

    @Override
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

    @Override
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

    @Override
    public Task getSubTaskById(int subTaskId) { // получение подзадачи по id
        listOfViewedTasks.add(subTasks.get(subTaskId));
        return subTasks.get(subTaskId);
    }

    @Override
    public ArrayList<Subtask> getAllSubTasks() { // печать всех подзадач
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteSubTaskById(int subTaskId) { // удаление подзадачи по id
        Subtask subtask = subTasks.get(subTaskId); // находим подзадачу по id подзадачи
        Epic epic = epics.get(subtask.getEpicId()); // находим эпик по id эпика
        epic.getSubTaskIds().remove(Integer.valueOf(subTaskId)); // удаляем подзадачу из списка в эпике
        subTasks.remove(subTaskId); // удаляем подзадачу из таблицы
        calculateStatusEpic(epic); // обновляем статус эпика
    }

    @Override
    public void deleteAllSubTasks() { // удаление всех подзадач
        for(Epic epic : epics.values()) { // очищаем списки id подзадач во всех эпиках
            epic.getSubTaskIds().clear();
            calculateStatusEpic(epic); // обновляем статус эпика
        }
        subTasks.clear(); // очищаем список подзадач
    }

    @Override
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

    @Override
    public List<Task> getHistory() {
        return listOfViewedTasks;
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