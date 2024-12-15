package com.pvt.enotes.controller;

import com.pvt.enotes.dto.TodoDto;
import com.pvt.enotes.repository.TodoRepository;
import com.pvt.enotes.service.TodoService;
import com.pvt.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")

public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception {
        Boolean saveTodo= todoService.saveTodo(todoDto);

        if(saveTodo){
            return CommonUtil.createBuilderResponseMessage("Todo saved success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Todo not saved",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodo(@PathVariable Integer id) throws Exception {
        TodoDto todo= todoService.getTodoById(id);

        return CommonUtil.createBuilderResponse(todo,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllTodoByUser() throws Exception {
        List<TodoDto> todoDtoList = todoService.getTodoByUser();
        if(CollectionUtils.isEmpty(todoDtoList)){
            return  ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(todoDtoList,HttpStatus.OK);

    }


}
