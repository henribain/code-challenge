package com.itr.codechallenge.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phones")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Phone {

    @Id
    @GeneratedValue
    private int id;
    private String number;
    private String city_code;
    private String country_code;

    @JsonBackReference
    @ManyToOne(cascade= { CascadeType.ALL})
    @JoinColumn(name="user_id")
    private User user;

}
