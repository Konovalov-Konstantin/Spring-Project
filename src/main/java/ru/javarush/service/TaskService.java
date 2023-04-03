package ru.javarush.service;

import ru.javarush.entity.Status;
import ru.javarush.entity.Task;

import java.util.List;

public interface TaskService {

    public List<Task> getAllTasks(int offset, int limit);

    public Long getCountOfTasks();

    public Task edit(int id, String description, Status status);
    public Task create(String description, Status status);

    public void delete(int id);

    public Task getTask(int id);

}
