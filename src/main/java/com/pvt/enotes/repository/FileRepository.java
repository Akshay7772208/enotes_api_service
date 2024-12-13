package com.pvt.enotes.repository;

import com.pvt.enotes.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDetails,Integer> {
}
