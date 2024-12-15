package com.pvt.enotes.scheduler;

import com.pvt.enotes.entity.Notes;
import com.pvt.enotes.repository.NotesRepository;
import org.jetbrains.annotations.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesScheduler {

    @Autowired
    private NotesRepository notesRepo;

    //@Scheduled(cron = "* * * ? * *")
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteNotesScheduler() throws Exception{
        //System.out.println("Executed");
        LocalDateTime cutOffDate= LocalDateTime.now().minusDays(7);
        List<Notes> deletedNotes=notesRepo.findAllByIsDeletedAndDeletedOnBefore(true,cutOffDate);
        notesRepo.deleteAll(deletedNotes);
    }

}
