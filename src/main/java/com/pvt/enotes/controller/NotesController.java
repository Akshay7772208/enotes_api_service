package com.pvt.enotes.controller;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.FavouriteNoteDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")

public class NotesController {

    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam (required =false) MultipartFile file) throws Exception {
        Boolean saveNotes= notesService.saveNotes(notes,file);

        if(saveNotes){
            return CommonUtil.createBuilderResponseMessage("Notes saved success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Note not saved",HttpStatus.INTERNAL_SERVER_ERROR);
   }

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes(){
        List<NotesDto> notes=notesService.getAllNotes();
        if(CollectionUtils.isEmpty(notes)){
            return  ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(notes,HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUser(  @RequestParam (name="pageNo",defaultValue="0") Integer pageNo,
                                                 @RequestParam (name="pageSize",defaultValue="10") Integer pageSize){

        NotesResponse notes=notesService.getAllNotesByUser(pageNo,pageSize);
//        if(CollectionUtils.isEmpty(notes)){
//            return  ResponseEntity.noContent().build();
//        }
        return CommonUtil.createBuilderResponse(notes,HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{

        notesService.softDeleteNotes(id);
        return CommonUtil.createBuilderResponseMessage("Delete success",HttpStatus.OK);

    }

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{

        notesService.restoreNotes(id);
        return CommonUtil.createBuilderResponseMessage("Notes Restore success",HttpStatus.OK);

    }

    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{
        List<NotesDto> notes=notesService.getUserRecycleBinNotes();
        if(CollectionUtils.isEmpty(notes)){
            return CommonUtil.createBuilderResponseMessage("Notes not found",HttpStatus.OK);
        }
        return CommonUtil.createBuilderResponse(notes,HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{

        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuilderResponseMessage("Hard Delete success",HttpStatus.OK);

    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> emptyUserRecycleBin() throws Exception{
        notesService.emptyRecycleBin();
        return CommonUtil.createBuilderResponseMessage("Empty Bin Success",HttpStatus.OK);

    }

    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId) throws Exception{

        notesService.favouriteNotes(noteId);
        return CommonUtil.createBuilderResponseMessage("Fav notes added",HttpStatus.CREATED);

    }

    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unFavouriteNotes(@PathVariable Integer favNoteId) throws Exception{

        notesService.unFavouriteNotes(favNoteId);
        return CommonUtil.createBuilderResponseMessage("Removed unfavourite",HttpStatus.OK);

    }

    @GetMapping("/fav-note")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserFavouriteNotes() throws Exception{

        List<FavouriteNoteDto> userFavouriteNotes=notesService.getUserFavouriteNotes();
        if(CollectionUtils.isEmpty(userFavouriteNotes)){
            return  ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuilderResponse(userFavouriteNotes,HttpStatus.OK);

    }

    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception{
        Boolean copyNotes=notesService.copyNotes(id);
        if(copyNotes){
            return CommonUtil.createBuilderResponseMessage("Copied Success",HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Copy Failed",HttpStatus.BAD_REQUEST);
    }

}
