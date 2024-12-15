package com.pvt.enotes.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pvt.enotes.dto.FavouriteNoteDto;
import com.pvt.enotes.dto.NotesDto;
import com.pvt.enotes.dto.NotesDto.CategoryDto;

import com.pvt.enotes.dto.NotesResponse;
import com.pvt.enotes.entity.FavouriteNote;
import com.pvt.enotes.entity.FileDetails;
import com.pvt.enotes.entity.Notes;
import com.pvt.enotes.exception.ResourceNotFoundException;
import com.pvt.enotes.repository.CategoryRepository;
import com.pvt.enotes.repository.FavouriteNoteRepository;
import com.pvt.enotes.repository.FileRepository;
import com.pvt.enotes.repository.NotesRepository;
import com.pvt.enotes.service.NotesService;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
    private FavouriteNoteRepository favouriteNoteRepo;

    @Autowired
    private ModelMapper mapper;

    @Value("${file.upload.path}")
    private String uploadpath;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto =ob.readValue(notes,NotesDto.class);
        notesDto.setIsDeleted(false);
        notesDto.setDeletedOn(null);

        if(!ObjectUtils.isEmpty(notesDto.getId())){
            updateNotes(notesDto,file);
        }
        //category validation
        checkCategoryExist(notesDto.getCategory());

        Notes notesMap =mapper.map(notesDto, Notes.class);

        FileDetails fileDtls= saveFileDetails(file);
        if(!ObjectUtils.isEmpty(fileDtls)){
           notesMap.setFileDetails(fileDtls);
        }else{
            if(ObjectUtils.isEmpty(notesDto.getId())){
                notesMap.setFileDetails(null);
            }
        }
        Notes saveNotes=notesRepo.save(notesMap);

        if(!ObjectUtils.isEmpty(saveNotes)){
            return true;
        }
        return false;
    }

    private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception{

        Notes existNotes=notesRepo.findById(notesDto.getId()).orElseThrow(()-> new ResourceNotFoundException("Invalid id found"));
        if(ObjectUtils.isEmpty(file)){
            notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(), NotesDto.FilesDto.class));
        }

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

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws Exception {
        InputStream io= new FileInputStream(fileDetails.getPath());

        return StreamUtils.copyToByteArray(io);

    }

    @Override
    public FileDetails getFileDetails(Integer id) throws Exception{
        FileDetails fileDtls = filesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("File not available"));
        InputStream io= new FileInputStream(fileDtls.getPath());

        return fileDtls;
    }

    @Override
    public NotesResponse getAllNotesByUser(Integer userId,Integer pageNo,Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);

        Page<Notes> pageNotes=notesRepo.findByCreatedByAndIsDeletedFalse(userId,pageable);
        List<NotesDto> notesDto=pageNotes.get().map(n->mapper.map(n,NotesDto.class)).toList();

        NotesResponse notes= NotesResponse.builder().
                        notes(notesDto).
                        pageNo(pageNotes.getNumber()).
                        pageSize(pageNotes.getSize()).
                        totalElements(pageNotes.getTotalElements()).
                        totalPages(pageNotes.getTotalPages()).
                        isFirst(pageNotes.isFirst()).
                        isLast(pageNotes.isLast()).
                        build();
        return notes;
    }

    @Override
    public void softDeleteNotes(Integer id) throws Exception{
        Notes notes=notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("id invalid"));
        notes.setIsDeleted(true);
        notes.setDeletedOn(LocalDateTime.now());
        notesRepo.save(notes);
    }

    @Override
    public void restoreNotes(Integer id) throws Exception{
        Notes notes=notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("id invalid"));
        notes.setIsDeleted(false);
        notes.setDeletedOn(null);
        notesRepo.save(notes);
    }

    @Override
    public List<NotesDto> getUserRecycleBinNotes(Integer userId) throws Exception {
        List<Notes> recycleNotes=notesRepo.findByCreatedByAndIsDeletedTrue(userId);
        List<NotesDto> notesDtoList=recycleNotes.stream().map(note-> mapper.map(note,NotesDto.class)).toList();
        return notesDtoList;
    }

    @Override
    public void hardDeleteNotes(Integer id) throws Exception{
        Notes notes=notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notes not found"));
        if(notes.getIsDeleted()){
            notesRepo.delete(notes);
        }else{
            throw new IllegalArgumentException("Sorry you can't hard delete");
        }

    }

    @Override
    public void emptyRecycleBin(Integer userId) throws Exception {
        List<Notes> recycleNotes=notesRepo.findByCreatedByAndIsDeletedTrue(userId);
        if(!CollectionUtils.isEmpty(recycleNotes)){
            notesRepo.deleteAll(recycleNotes);
        }
    }

    @Override
    public void favouriteNotes(Integer noteId) throws Exception{
        int userId=1;
        Notes notes=notesRepo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Notes not found & Id invalid"));
        FavouriteNote favouriteNote= FavouriteNote.builder().
                note(notes).
                userId(userId).
                build();
        favouriteNoteRepo.save(favouriteNote);
    }

    @Override
    public void unFavouriteNotes(Integer favoriteNoteId) throws Exception{
        FavouriteNote notes=favouriteNoteRepo.findById(favoriteNoteId).orElseThrow(()-> new ResourceNotFoundException("Favourite Notes not found & Id invalid"));
        favouriteNoteRepo.delete(notes);
    }

    @Override
    public List<FavouriteNoteDto> getUserFavouriteNotes() {
        Integer userId=1;
        List<FavouriteNote> favouriteNotes=favouriteNoteRepo.findByUserId(userId);
        return favouriteNotes.stream().map(fn-> mapper.map(fn,FavouriteNoteDto.class)).toList();

    }

    @Override
    public Boolean copyNotes(Integer id) throws Exception{
        Notes notes=notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("id invalid"));
        Notes copyNotes= notes.builder().
                title(notes.getTitle()).
                description(notes.getDescription()).
                category(notes.getCategory()).
                isDeleted(false).
                fileDetails(null).
                build();
        Notes saveCopyNote=notesRepo.save(copyNotes);
        if(!ObjectUtils.isEmpty(saveCopyNote)){
            return true;
        }
        return false;
    }


}
