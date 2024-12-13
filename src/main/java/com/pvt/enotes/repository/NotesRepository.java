package com.pvt.enotes.repository;

import com.pvt.enotes.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
   public Page<Notes> findByCreatedBy(Integer userId, Pageable pageable);
}
