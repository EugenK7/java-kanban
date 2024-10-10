package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Node first;
    Node last;

    @Override
    public void add(Task task) {
        if(task == null) return;
        Node node = history.get(task.getId());
        if(node != null) {
            removeNode(node);
        }
        linkLast(task);
    }

    @Override
    public ArrayList<Task> getTasks() {
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
        final Node oldLast = last;
        final Node newNode = new Node(oldLast, task, null);
        if(first == null) {
            first = newNode;
            history.put(task.getId(), newNode);
            return;
        }
        Node lastNode = first;
        while (lastNode.next != null) {
            lastNode = lastNode.next;
        }
        lastNode.next = newNode;
        newNode.prev = lastNode;
       history.put(task.getId(), newNode);
    }

    private void removeNode(Node node) {
        Node currentNode = first;
        if(currentNode != null && currentNode == node) {
            first = node.next;
            if(first != null) {
                node.prev = null;
            }
            return;
        }
        while (currentNode != null && currentNode != node) {
            currentNode = currentNode.next;
        }
        if(currentNode == null) {
            return;
        }
        if(currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        }
        if(currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        }
        history.remove(node.task.getId());
    }
}
