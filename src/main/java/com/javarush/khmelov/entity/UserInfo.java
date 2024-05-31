package com.javarush.khmelov.entity;

import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user_info")
public class UserInfo {

    @Id
    @Column(name = "user_id")
    Long userId;

    String address;

    String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    User user;
}
