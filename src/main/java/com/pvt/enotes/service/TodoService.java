package com.pvt.enotes.service;

import com.pvt.enotes.dto.TodoDto;

import java.util.List;

public interface TodoService {

    public Boolean saveTodo(TodoDto todoDto) throws Exception;

    public TodoDto getTodoById(Integer id) throws Exception;

    public List<TodoDto> getTodoByUser() throws Exception;

}
