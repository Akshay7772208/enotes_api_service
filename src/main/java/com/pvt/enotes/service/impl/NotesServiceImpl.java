package com.pvt.enotes.service.impl;

import com.pvt.enotes.dto.NotesDto;
import com.pvt.enotes.dto.NotesDto.CategoryDto;

import com.pvt.enotes.entity.Notes;
import com.pvt.enotes.exception.ResourceNotFoundException;
import com.pvt.enotes.repository.CategoryRepository;
import com.pvt.enotes.repository.NotesRepository;
import com.pvt.enotes.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean saveNotes(NotesDto notesDto) throws Exception {
        //category validation
        checkCategoryExist(notesDto.getCategory());

        Notes notes =mapper.map(notesDto, Notes.class);
        Notes saveNotes=notesRepo.save(notes);

        if(!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }
        return false;
    }

    private void checkCategoryExist(CategoryDto category) throws Exception {
        categoryRepo.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Catgeory id invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {

        return notesRepo.findAll().stream().map(note -> mapper.map(note,NotesDto.class)).toList();
    }
}
