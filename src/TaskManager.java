import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    int id = 0;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subTasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subTasks = new HashMap<>();
    }

    private int generateId() {
        return ++id;
    }

    public Task createTask(Task task) { // создание задачи
        task.setId(generateId()); // получаем новый id
        tasks.put(task.getId(), task); // записываем задачу в таблицу
        return task;
    }

    public void updateTask(Task task, int taskId) { // обновление задачи
        Task currentTask = tasks.get(taskId); // получаем задачу из таблицы по id
        if(currentTask == null) { // проверяем на пустую ссылку
            return;
        }
        currentTask.setName(task.getName()); // присваиваем имя из объекта с новой задачей
        currentTask.setDescription(task.getDescription()); // присваиваем описание из объекта с новой задачей
        currentTask.setStatus(task.getStatus()); // присваиваем статус из объекта с новой задачей
        System.out.println("Задача с ID " + currentTask.getId() + " обновлена.");
    }

    public Task getTaskById(int taskId) { // получение задачи по id
        System.out.println("Задача с ID " + taskId + ":");
        return tasks.get(taskId);
    }

    public void getAllTasks() { // печать списка всех задач
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }

    public void deleteTaskById(int taskId) { // удаление задачи по id
        tasks.remove(taskId);
        System.out.println("Задача с ID " + taskId + " удалена.");
    }

    public void deleteAllTasks() { // удаление всех задач
        tasks.clear();
        System.out.println("Все задачи удалены.");
    }

    public Epic createEpic(Epic epic) { // создание эпика
        epic.setId(generateId());
        epic.setStatus(TaskStatus.NEW); // устанавливаем статус new у нового эпика
        epic.clearSubTaskIds(); // очищаем список подзадач
        epics.put(epic.getId(), epic);
        return epic;
    }

    public void updateEpic(Epic epic, int epicId) { // обновление эпика
        Epic currentEpic = epics.get(epicId);
        if(currentEpic == null) {
            return;
        }
        currentEpic.setName(epic.getName());
        currentEpic.setDescription(epic.getDescription());
        System.out.println("Эпик с ID " + currentEpic.getId() + " обновлен.");
    }

    public Epic getEpicById(int epicId) { // получение эпика по id
        System.out.println("Эпик с ID " + epicId + ":");
        return epics.get(epicId);
    }

    public void getAllEpics() { // печать всех эпиков
        for (Epic epic : epics.values()) {
            System.out.println(epic);
        }
    }

    public void deleteEpicById(int epicId) { // удаление эпика по id
        epics.remove(epicId);
        System.out.println("Эпик с ID " + epicId + " удален.");
    }

    public void deleteAllEpics() { // удаление всех эпиков
        epics.clear();
        System.out.println("Все эпики удалены.");
    }

    public Subtask createSubTask(Subtask subTask) { // создание подзадачи
        subTask.setId(generateId());
        subTasks.put(subTask.getId(), subTask);
        Epic epic = epics.get(subTask.getEpicId()); // находим эпик по эпик-id
        ArrayList<Integer> subTaskIds = epic.getSubTaskIds(); // находим список id подзадач этого эпика
        subTaskIds.add(subTask.getId()); // добавляем id подзадачи в список к эпику
        calculateStatusEpic(epic); // рассчитываем статус эпика
        return subTask;
    }

    public void updateSubTask(Subtask subTask, int subTaskId) { // обновление подзадачи
        Task currentSubTask = subTasks.get(subTaskId);
        if(currentSubTask == null) {
            return;
        }
        currentSubTask.setName(subTask.getName());
        currentSubTask.setDescription(subTask.getDescription());
        currentSubTask.setStatus(subTask.getStatus());
        System.out.println("Подзадача с ID " + subTask.getId() + " обновлена.");
        Epic epic = epics.get(subTask.getEpicId());
        calculateStatusEpic(epic);
    }

    public Task getSubTaskById(int subTaskId) { // получение подзадачи по id
        System.out.println("Подзадача с ID " + subTaskId + ":");
        return subTasks.get(subTaskId);
    }

    public void getAllSubTasks() { // печать всех подзадач
        for (Subtask subTask : subTasks.values()) {
            System.out.println(subTask);
        }
    }

    public void deleteSubTaskById(int subTaskId) { // удаление подзадачи по id
        subTasks.remove(subTaskId);
        System.out.println("Подзадача с ID " + subTaskId + " удалена.");
    }

    public void deleteAllSubTasks() { // удаление всех подзадач
        subTasks.clear();
        System.out.println("Все подзадачи удалены.");
    }

    public void getAllSubTasksByEpicId(int epicId) { // получение списка всех подзадач по id эпика
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subTaskIds = epic.getSubTaskIds();
        for (Integer subTaskId : subTaskIds) {
            Subtask subtask = subTasks.get(subTaskId);
            System.out.println(subtask);
        }
    }

    private void calculateStatusEpic(Epic epic) { // расчет статуса эпика
        TaskStatus status = TaskStatus.NEW;
        boolean allNew = true;
        boolean allDone = true;
        boolean hasInProgressOrDone = false;
        ArrayList<Integer> subTaskIds = epic.getSubTaskIds();
        for (Integer subTaskId : subTaskIds) {
            Subtask subtask = subTasks.get(subTaskId);
            if (subtask.getStatus() != TaskStatus.NEW) { // найдена подзадача не в статусе новый
                allNew = false;
            }
            if (subtask.getStatus() != TaskStatus.DONE) { // найдена подзадача не в статусе выполнено
                allDone = false;
            }
            if (subtask.getStatus() == TaskStatus.IN_PROGRESS || subtask.getStatus() == TaskStatus.DONE) {
                hasInProgressOrDone = true; // найдена подзадача в статусе в прогрессе или выполненно
            }
        }
        if (allDone) {
            status = TaskStatus.DONE;
        } else if (hasInProgressOrDone) {
            status = TaskStatus.IN_PROGRESS;
        }
        epic.setStatus(status);
    }
}
