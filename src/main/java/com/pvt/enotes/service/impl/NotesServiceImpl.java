package com.pvt.enotes.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pvt.enotes.dto.NotesDto;
import com.pvt.enotes.dto.NotesDto.CategoryDto;

import com.pvt.enotes.entity.FileDetails;
import com.pvt.enotes.entity.Notes;
import com.pvt.enotes.exception.ResourceNotFoundException;
import com.pvt.enotes.repository.CategoryRepository;
import com.pvt.enotes.repository.FileRepository;
import com.pvt.enotes.repository.NotesRepository;
import com.pvt.enotes.service.NotesService;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private FileRepository filesRepo;

    @Autowired
    private ModelMapper mapper;

    @Value("${file.upload.path}")
    private String uploadpath;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto =ob.readValue(notes,NotesDto.class);

        //category validation
        checkCategoryExist(notesDto.getCategory());

        Notes notesMap =mapper.map(notesDto, Notes.class);

        FileDetails fileDtls= saveFileDetails(file);
        if(!ObjectUtils.isEmpty(fileDtls)){
           notesMap.setFileDetails(fileDtls);
        }else{
            notesMap.setFileDetails(null);
        }

        Notes saveNotes=notesRepo.save(notesMap);

        if(!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }
        return false;
    }

    private FileDetails saveFileDetails(MultipartFile file) throws Exception {

        if(!ObjectUtils.isEmpty(file) && !file.isEmpty()){
            String originalFileName = file.getOriginalFilename();
            String extension=FilenameUtils.getExtension(originalFileName);

            List<String> extensionsAlowed=Arrays.asList("pdf","xlsx","jpg","png");
            if(!extensionsAlowed.contains(extension)){
                throw new IllegalArgumentException("Invalid file format");
            }
            

            String rndString= UUID.randomUUID().toString();
//            String extension=FilenameUtils.getExtension(originalFileName);
            String uploadFileName= rndString+"."+extension;

            File saveFile = new File(uploadpath);
            if(!saveFile.exists()){
                saveFile.mkdir();
            }
            String storePath= uploadpath.concat(uploadFileName);


            long upload=Files.copy(file.getInputStream(), Paths.get(storePath));
            if(upload!=0){
                FileDetails fileDtls = new FileDetails();

                fileDtls.setOriginalFileName(originalFileName);

                fileDtls.setDisplayFileName(getDisplayName(originalFileName));
                fileDtls.setUploadFileName(uploadFileName);

                fileDtls.setFileSize(file.getSize());
                fileDtls.setPath(storePath);
                FileDetails saveFileDetails=filesRepo.save(fileDtls);
                return saveFileDetails;
            }
        }
        return null;
    }

    private String getDisplayName(String originalFileName) {
        //java programming.pdf
        String extension=FilenameUtils.getExtension(originalFileName);
        String fileName=FilenameUtils.removeExtension(originalFileName);

        if(fileName.length()>8){
            fileName=fileName.substring(0,7);
        }
        fileName=fileName+"."+extension;
        return fileName;
    }

    private void checkCategoryExist(CategoryDto category) throws Exception {
        categoryRepo.findById(category.getId()).orElseThrow(()-> new ResourceNotFoundException("Catgeory id invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {

        return notesRepo.findAll().stream().map(note -> mapper.map(note,NotesDto.class)).toList();
    }
}
