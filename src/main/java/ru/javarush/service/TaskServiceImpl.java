package ru.javarush.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.entity.Status;
import ru.javarush.entity.Task;
import ru.javarush.repository.TaskRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(int offset, int limit) {
        return taskRepository.getAllTasks(offset,limit);
    }

    public Long getCountOfTasks() {
        return taskRepository.getCountOfTasks();
    }

    @Transactional
    public Task edit(int id, String description, Status status) {
        Task task = taskRepository.getTask(id);
        if(isNull(task)) {
            System.out.println("Not found");
        }
        task.setDescription(description);
        task.setStatus(status);
        taskRepository.saveOrUpdate(task);
        return task;
    }

    public Task create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskRepository.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = taskRepository.getTask(id);
        if(isNull(task)) {
            throw new RuntimeException("Not found");
        }
        taskRepository.delete(task);
    }

    public Task getTask(int id) {
        return taskRepository.getTask(id);
    }

}
