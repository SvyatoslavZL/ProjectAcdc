package com.javarush.khmelov.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_data")
public class UserData {

    @Id
    @Column(name = "user_id")
    Long id;

    String phone;

    String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
}
