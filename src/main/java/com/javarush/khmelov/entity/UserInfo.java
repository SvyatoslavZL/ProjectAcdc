package com.javarush.khmelov.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "user_info")
@Cacheable
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
