package com.pvt.enotes.dto;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {

    private Integer id;

    private  String firstName;

    private String LastName;

    private String email;

    private StatusDto status;

    private String mobNo;

    private List<RoleDto> roles;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class RoleDto{
        private Integer id;

        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class StatusDto{
        private Integer id;

        private Boolean isActive;
    }
}
