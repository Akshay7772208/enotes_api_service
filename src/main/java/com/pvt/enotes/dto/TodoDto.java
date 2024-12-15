package com.pvt.enotes.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {

    private Integer id;

    private String title;

    private StatusDto status;

    private Integer createdBy;

    private Date createdOn;

    private Integer UpdatedBy;

    private Date UpdatedOn;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class StatusDto{
        private Integer id;

        private String name;
    }
}
