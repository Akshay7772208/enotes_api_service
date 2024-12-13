package com.pvt.enotes.dto;

import com.pvt.enotes.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NotesDto {

    private Integer id;

    private String title;

    private String description;

    private CategoryDto category;

    private Integer createdBy;

    private Date createdOn;

    private Integer UpdatedBy;

    private Date UpdatedOn;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDto{

        private Integer id;

        private String name;
    }
}
