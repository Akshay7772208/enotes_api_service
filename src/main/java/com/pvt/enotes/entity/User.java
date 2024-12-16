package com.pvt.enotes.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private  String firstName;

    private String LastName;

    private String email;

    private String password;

    private String mobNo;

    @OneToMany(cascade= CascadeType.ALL)
    private List<Role> roles;

    @OneToOne(cascade= CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="status_id")
    private AccountStatus status;


}
