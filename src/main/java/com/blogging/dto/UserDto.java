package com.blogging.dto;

import com.blogging.Model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String id;

    @NotEmpty
    @Size(min = 4, message = "Username must be minimum of 4 characters")
    private String name;

    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 3, message = "Password must be minimum of 3 character")
    private String password;

    @NotNull
    private String about;

    private Role userRole;
}

