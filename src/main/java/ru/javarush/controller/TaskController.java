package ru.javarush.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javarush.entity.Status;
import ru.javarush.entity.Task;
import ru.javarush.service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TaskController {

    private final TaskService service;

    static List<Status> statusList;

    static {
        statusList = new ArrayList<>();
        statusList.add(Status.PAUSED);
        statusList.add(Status.DONE);
        statusList.add(Status.IN_PROGRESS);
    }

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String showAlTasks(Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "limit", required = false, defaultValue = "6") int limit) {
        List<Task> tasks = service.getAllTasks((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);

        int totalPages = (int) Math.ceil(1.0 * service.getCountOfTasks() / limit); // подсчет кол-ва страниц
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        model.addAttribute("current_page", page);

        return "tasks";
    }

    /*добавление новой task */
    @GetMapping("/addTask")
    public String addTask(Model model, @ModelAttribute("newTask") Task newTask) {
        model.addAttribute("statusList", statusList);
        return "taskInfo";
    }

    /* сохранение новой task */
    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute("newTask") Task newTask) {
        service.create(newTask.getDescription(), newTask.getStatus());
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editTask(Model model, @PathVariable("id") int id) {
        Task editTask = service.getTask(id);
        model.addAttribute("editTask", editTask);
        model.addAttribute("statusList", statusList);
        return "editTask";
    }

    @PatchMapping("/{id}")
    public String updateTask(@ModelAttribute("editTask") Task task,
                             @PathVariable("id") int id) {
        service.edit(id, task.getDescription(), task.getStatus());
        return "redirect:/";
    }


    @DeleteMapping("/{id}/delete")
    public String deleteTask (@PathVariable("id") int id) {
        service.delete(id);
        return "redirect:/";
    }

}
