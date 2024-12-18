package com.pvt.enotes.service.impl;

import com.pvt.enotes.dto.NotesDto;
import com.pvt.enotes.dto.TodoDto;
import com.pvt.enotes.dto.TodoDto.StatusDto;
import com.pvt.enotes.entity.Notes;
import com.pvt.enotes.entity.Todo;
import com.pvt.enotes.enums.TodoStatus;
import com.pvt.enotes.exception.ResourceNotFoundException;
import com.pvt.enotes.repository.TodoRepository;
import com.pvt.enotes.service.TodoService;
import com.pvt.enotes.util.CommonUtil;
import com.pvt.enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepo;

    @Autowired
    Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveTodo(TodoDto todoDto) throws Exception{
        //validation
        validation.todoValidation(todoDto);

        Todo todo=mapper.map(todoDto, Todo.class);
        //System.out.println(todo.getStatusId());
        todo.setStatusId(todoDto.getStatus().getId());
        Todo saveTodo= todoRepo.save(todo);
        if(!ObjectUtils.isEmpty(saveTodo)){
            return true;
        }
        return false;
    }

    @Override
    public TodoDto getTodoById(Integer id) throws Exception{
        Todo todo = todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid id found"));
        TodoDto todoDto = mapper.map(todo, TodoDto.class);
        setStatus(todoDto,todo);
        return todoDto;
    }

    public void setStatus(TodoDto todoDto, Todo todo) {
        for(TodoStatus st: TodoStatus.values()){
            if(st.getId().equals(todo.getStatusId())){
                StatusDto statusDto= StatusDto.builder().
                        id(st.getId()).
                        name(st.getName()).
                        build();
                todoDto.setStatus(statusDto);
            }
        }

    }

    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId= CommonUtil.getLoggedInUser().getId();

        List<Todo> todos=todoRepo.findByCreatedBy(userId);
        return todos.stream().map(td-> mapper.map(td,TodoDto.class)).toList();
    }
}
