package com.pvt.enotes.service;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.NotesDto;
import com.pvt.enotes.dto.NotesResponse;
import com.pvt.enotes.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(String notes, MultipartFile file) throws Exception;

    public List<NotesDto> getAllNotes();

    public byte[] downloadFile(FileDetails fileDetails) throws Exception;

    public FileDetails getFileDetails(Integer id) throws Exception;

    public NotesResponse getAllNotesByUser(Integer userId,Integer pageNo,Integer pageSize);
}
