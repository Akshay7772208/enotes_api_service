package com.pvt.enotes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Notes extends BaseModel{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @ManyToOne
    private Category category;
}
