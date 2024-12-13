package com.pvt.enotes.controller;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.NotesDto;
import com.pvt.enotes.service.NotesService;
import com.pvt.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")

public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    public ResponseEntity<?> saveNotes(@RequestBody NotesDto notesDto) throws Exception {
        Boolean saveNotes= notesService.saveNotes(notesDto);

        if(saveNotes){
            return CommonUtil.createBuilderResponseMessage("Notes saved success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Note not saved",HttpStatus.INTERNAL_SERVER_ERROR);
   }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notes=notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return  ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(notes,HttpStatus.OK);
    }



}
