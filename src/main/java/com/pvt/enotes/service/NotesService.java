package com.pvt.enotes.service;

import com.pvt.enotes.dto.FavouriteNoteDto;
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

    public NotesResponse getAllNotesByUser(Integer pageNo,Integer pageSize);

    public void softDeleteNotes(Integer id) throws Exception;

    public void restoreNotes(Integer id) throws Exception;

    public List<NotesDto> getUserRecycleBinNotes() throws Exception;

    void hardDeleteNotes(Integer id) throws Exception;

    void emptyRecycleBin() throws Exception;

    public void favouriteNotes(Integer noteId) throws Exception;

    public void unFavouriteNotes(Integer noteId) throws Exception;

    public List<FavouriteNoteDto> getUserFavouriteNotes() throws Exception;

    public Boolean copyNotes(Integer id) throws Exception;
}
