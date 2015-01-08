package com.zimmem.backbone.demo.controller;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zimmem.backbone.demo.entity.Todo;

/**
 * 
 */

/**
 * @author zhaowen.zhuang
 */
@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public List<Todo> query(Todo todo) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from todo where content like '%"
                                                                   + Strings.nullToEmpty(todo.getContent()) + "%'");
        List<Todo> todoList = Lists.transform(list, new Function<Map<String, Object>, Todo>() {

            public Todo apply(Map<String, Object> input) {
                Todo todo = new Todo();
                todo.setContent(String.valueOf(input.get("CONTENT")));
                todo.setId(String.valueOf(input.get("ID")));
                todo.setStatus(String.valueOf(input.get("STATUS")));
                return todo;
            }
        });
        return todoList;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Todo createTodo(@RequestBody Todo todo) {
        todo.setId(UUID.randomUUID().toString());
        todo.setStatus("New");
        jdbcTemplate.update("insert into todo(id,content, status) values(?,?,?)",
                            new Object[] { todo.getId(), todo.getContent(), todo.getStatus() }, new int[] {
                                    Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
        return todo;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Todo modifiyTodo(@RequestBody Todo todo) {
        jdbcTemplate.update("update todo set content = ? , status = ? where id = ?", new Object[] { todo.getContent(),
                todo.getStatus(), todo.getId() }, new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
        return todo;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public boolean deleteTodo(@PathVariable String id) {
        return jdbcTemplate.update("delete  todo where id = ?", new Object[] { id }, new int[] { Types.VARCHAR }) > 0;
    }
}
