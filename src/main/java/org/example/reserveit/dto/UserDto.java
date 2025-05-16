package org.example.reserveit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.User;
import org.example.reserveit.models.UserType;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Long phoneNumber;
    private UserType type;


    public static UserDto from(User user) {

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .type(user.getType())
                .build();

    }

    public static List<UserDto> from(List<User> users) {
        return users.stream().map(UserDto::from).toList();
    }
}
