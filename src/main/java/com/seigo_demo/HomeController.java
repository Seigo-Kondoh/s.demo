package com.seigo_demo;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {
    //    Daoクラスと連携
    private TaskListDao dao;

    @Autowired
    HomeController(TaskListDao dao) {
        this.dao = dao;
    }

    //タスクのレコード
    record TaskItem(String id, String task, String deadline, Boolean done) {
    }

//    private List<TaskItem> taskItems = new ArrayList<>();

    @GetMapping("/task")
    public String listItems(Model model) {
        List<TaskItem> taskItems = dao.find();
        model.addAttribute("taskList", taskItems);
        return "task";
    }

    @GetMapping("/add")
    public String addItem(@RequestParam("task") String task, @RequestParam("deadline") String deadline) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, task, deadline, false);
//        taskItems.add(item);
        dao.add(item);
        return "redirect:/task";
    }

    @GetMapping("/delete")
    public String deleteItem(@RequestParam("id") String id) {
        dao.delete(id);
        return "redirect:/task";
    }

    @GetMapping("/update")
    public String taskUpdate(@RequestParam("id") String id, @RequestParam("task") String task, @RequestParam("deadline") String deadline) {
        boolean done = false;
        TaskItem taskItem = new TaskItem(id, task, deadline, done);
        dao.updateTask(taskItem);
        return "redirect:/task";
    }

    record Users(String id, String name, String age) {
    }

    @GetMapping("/greeting")
    public String greeting(Model model) {
        List<Users> account = dao.findAll();
//        以下問題のコード
        model.addAttribute("users", account);
        return "greeting";
    }

    @GetMapping("/test")
    public String test(@RequestParam("name") String name, @RequestParam("age") String age, Model model) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        Users u = new Users(id, name, age);
        dao.userAdd(u);
        model.addAttribute("user_id", u.id());
        model.addAttribute("user_name", u.name());
        model.addAttribute("user_age", u.age());
        return "redirect:/greeting";
    }

    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
