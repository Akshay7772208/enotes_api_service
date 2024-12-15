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

public class FavouriteNote {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Notes note;

    private Integer userId;

}
