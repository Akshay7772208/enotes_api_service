package com.pvt.enotes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Notes extends BaseModel{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @ManyToOne
    private Category category;

    @ManyToOne
    private FileDetails fileDetails;

    private Boolean isDeleted;

    private LocalDateTime deletedOn;

}
