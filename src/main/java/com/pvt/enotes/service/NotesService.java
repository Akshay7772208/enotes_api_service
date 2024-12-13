package com.pvt.enotes.service;

import com.pvt.enotes.dto.CategoryDto;
import com.pvt.enotes.dto.NotesDto;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(NotesDto notesDto) throws Exception;

    public List<NotesDto> getAllNotes();

}
