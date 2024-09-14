package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> listOfViewedTasks;

    public InMemoryHistoryManager() {
        this.listOfViewedTasks = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        listOfViewedTasks.add(task);
        if(listOfViewedTasks.size() >= 11) {
            listOfViewedTasks.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        return listOfViewedTasks;
    }
}
