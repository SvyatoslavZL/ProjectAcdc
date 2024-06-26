package com.javarush.khmelov.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatistics {
    String login;
    long win;
    long lost;
    long play;
    long total;
}
