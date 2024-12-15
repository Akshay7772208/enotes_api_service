package com.pvt.enotes.repository;

import com.pvt.enotes.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
   public Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pageable);

   public List<Notes> findByCreatedBy(Integer userId) throws Exception;

   List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId) throws Exception;

   List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate) throws Exception;
}
