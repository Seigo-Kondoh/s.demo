package com.seigo_demo;

import com.seigo_demo.HomeController.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskListDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    TaskListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //    タスク追加のメソッド
    public void add(TaskItem taskItem) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(taskItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tasklist");
        insert.execute(param);
    }

    //    タスクを全て取り出しするメソッド
    public List<TaskItem> find() {
        String query = "SELECT * FROM tasklist";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<TaskItem> taskItems = result.stream()
                .map((Map<String, Object> row) -> new TaskItem(
                        row.get("id").toString(),
                        row.get("task").toString(),
                        row.get("deadline").toString(),
                        (Boolean) row.get("done")))
                .toList();
        return taskItems;
    }

    //    タスクを消すメソッド
    public void delete(String id) {
        int number = jdbcTemplate.update("DELETE FROM tasklist WHERE id = ?", id);
    }

    public int update_id(String id, String task, String deadline, Boolean done) {
        String update_id = id;
        int number = jdbcTemplate.update("SELECT * FROM TASKLIST WHERE id = ?", update_id);
        int number2 = jdbcTemplate.update("UPDATE TASKLIST SET TASK=?,DEADLINE=?,DONE=?", task, deadline, done);
        return number2;
    }

    //　　タスクを更新するメソッド
    public int updateTask(TaskItem taskItem) {
        String taskId = taskItem.id();
        int updateTask = jdbcTemplate.update("UPDATE TASKLIST SET TASK=?,DEADLINE=?,DONE=? WHERE ID = ?",
                taskItem.task(),
                taskItem.deadline(),
                taskItem.done(),
                taskItem.id());
        return updateTask;
    }

    //    テスト
    public int userAdd(Users u) {
        int insert = jdbcTemplate.update("INSERT INTO Account" + "(userId,userName,userAge) VALUES(?,?,?)", u.id(), u.name(), u.age());
        return insert;
    }

    public List<Users> findAll() {
        String sql = "SELECT * FROM Account";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        List<Users> users = result.stream()
                .map((Map<String, Object> row) -> new Users(
                        row.get("userId").toString(),
                        row.get("userName").toString(),
                        row.get("userAge").toString()))
                .toList();
        return users;
    }
}
