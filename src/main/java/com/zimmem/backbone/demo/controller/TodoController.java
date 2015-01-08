package com.zimmem.backbone.demo.controller;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zimmem.backbone.demo.entity.Todo;

/**
 * 
 */

/**
 * @author zhaowen.zhuang
 *
 */
@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public List<Todo> query(Todo todo) {
        List<? extends Todo> list =
                jdbcTemplate.queryForList(
                        "select * from todo where content like %" + todo.getContent() + "%",
                        todo.getClass());
        return (List<Todo>) list;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Todo createTodo(Todo todo) {
        todo.setId(UUID.randomUUID().toString());
        todo.setStatus("New");
        jdbcTemplate.update("insert into todo values(?,?,?)",
                new Object[] {todo.getId(), todo.getContent(), todo.getStatus()}, new int[] {
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
        return todo;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public Todo modifiyTodo(Todo todo) {
        jdbcTemplate.update("update todo set content = ? , status = ? where id = ?", new Object[] {
                todo.getContent(), todo.getStatus(), todo.getId()}, new int[] {Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR});
        return todo;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Todo deleteTodo(Todo todo) {
        jdbcTemplate.update("delete  todo where id = ?", new Object[] {todo.getId()},
                new int[] {Types.VARCHAR});
        return todo;
    }
}
