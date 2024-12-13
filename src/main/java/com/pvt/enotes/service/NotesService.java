package com.pvt.enotes.service;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.NotesDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(String notes, MultipartFile file) throws Exception;

    public List<NotesDto> getAllNotes();


}
