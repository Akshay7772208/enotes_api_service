package com.pvt.enotes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class AccountStatus {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Boolean isActive;

    private String verificationCode;

}
