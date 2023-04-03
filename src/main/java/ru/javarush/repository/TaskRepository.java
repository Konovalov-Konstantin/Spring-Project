package ru.javarush.repository;

import ru.javarush.entity.Task;

import java.util.List;

public interface TaskRepository {
    public List<Task> getAllTasks(int offset, int limit);

    public Long getCountOfTasks();

    public Task getTask(int id);

    public void saveOrUpdate(Task task);

    public void delete(Task task);

}
