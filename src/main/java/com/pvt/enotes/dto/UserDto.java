package com.pvt.enotes.dto;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private Integer id;

    private  String firstName;

    private String LastName;

    private String email;

    private String password;

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
}
