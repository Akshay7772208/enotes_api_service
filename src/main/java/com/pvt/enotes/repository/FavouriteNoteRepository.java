package com.pvt.enotes.repository;

import com.pvt.enotes.entity.FavouriteNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteNoteRepository extends JpaRepository<FavouriteNote,Integer> {
    List<FavouriteNote> findByUserId(Integer userId);
}
