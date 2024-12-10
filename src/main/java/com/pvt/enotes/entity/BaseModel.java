package com.pvt.enotes.entity;



import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.Date;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor

//@Entity
@MappedSuperclass
public class BaseModel {

    private Boolean isActive;

    private Boolean isDeleted;

    private Integer createdBy;

    private Date createdOn;

    private Integer UpdatedBy;

    private Date UpdatedOn;


}
