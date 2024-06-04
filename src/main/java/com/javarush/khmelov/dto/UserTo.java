package com.javarush.khmelov.dto;

import com.javarush.khmelov.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTo {

    Long id;

    String login;

    String image;

    Role role;

}