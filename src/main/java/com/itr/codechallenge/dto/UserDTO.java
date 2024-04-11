package com.itr.codechallenge.dto;

import com.itr.codechallenge.entities.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int id;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;

}
