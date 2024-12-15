package com.pvt.enotes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Todo extends BaseModel{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(name="status")
    private Integer statusId;
}
