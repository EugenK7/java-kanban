package service;

import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

   //Managers managers = new Managers();

    @Test
    @DisplayName("Создание менеджера задач и истории")
    public void shouldCreateTaskManager() {

        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(taskManager, "Менеджер задач не создан");
        assertNotNull(historyManager, "Менеджер истории не создан");
    }

}