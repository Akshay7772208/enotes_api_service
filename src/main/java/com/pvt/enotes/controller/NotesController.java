package com.pvt.enotes.controller;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.NotesDto;
import com.pvt.enotes.dto.NotesResponse;
import com.pvt.enotes.entity.FileDetails;
import com.pvt.enotes.service.NotesService;
import com.pvt.enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")

public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam (required =false) MultipartFile file) throws Exception {
        Boolean saveNotes= notesService.saveNotes(notes,file);

        if(saveNotes){
            return CommonUtil.createBuilderResponseMessage("Notes saved success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Note not saved",HttpStatus.INTERNAL_SERVER_ERROR);
   }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
        FileDetails fileDetails = notesService.getFileDetails(id);
        byte [] data= notesService.downloadFile(fileDetails);

        HttpHeaders headers = new HttpHeaders();
        String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
        return ResponseEntity.ok().headers(headers).body(data);
       
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notes=notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return  ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(notes,HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    public ResponseEntity<?> getAllNotesByUser(  @RequestParam (name="pageNo",defaultValue="0") Integer pageNo,
                                                 @RequestParam (name="pageSize",defaultValue="10") Integer pageSize){
        Integer userId=1;

        NotesResponse notes=notesService.getAllNotesByUser(userId,pageNo,pageSize);
//        if(CollectionUtils.isEmpty(notes)){
//            return  ResponseEntity.noContent().build();
//        }
        return CommonUtil.createBuilderResponse(notes,HttpStatus.OK);
    }

}
