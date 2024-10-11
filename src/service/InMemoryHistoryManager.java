package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    private static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Node prev, Task task, Node next) {
            this.task = task;
            this.prev = prev;
            this.next = next;
        }
    }

    private HashMap<Integer, Node> history = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void add(Task task) {
        if (task == null) return;
        Node node = history.get(task.getId());
        if (node != null) {
            removeNode(node);
        }
        linkLast(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> listTasks = new ArrayList<>();
        Node current = first;
        while (current != null) {
            listTasks.add(current.task);
            current = current.next;
        }
        return listTasks;
    }

    @Override
    public void remove(int id) {
        Node node = history.get(id);
        removeNode(node);
    }

    private void linkLast(Task task) {
        final Node newNode;
        if(history.isEmpty()) {
            newNode = new Node(null, task, null);
            first = newNode;
            last = newNode;
        } else {
            final Node oldLast = last;
            newNode = new Node(oldLast, task, null);
            last = newNode;
            oldLast.next = newNode;
            newNode.prev = oldLast;
        }
        history.put(task.getId(), newNode);
    }

    private void removeNode(Node node) {
        if (node == null) {
            return;
        }
        if (node.prev == null && node.next == null) {
            first = null;
            last = null;
        } else if (node.prev == null) {
            first = node.next;
            first.prev = null;
        } else if (node.next == null) {
            last = node.prev;
            last.next = null;
        } else if (node.prev != null && node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        history.remove(node.task.getId());
    }
}
