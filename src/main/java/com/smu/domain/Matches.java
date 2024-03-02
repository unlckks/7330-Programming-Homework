package com.smu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: MingYun
 * @Date: 2024-02-28 16:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Matches {

    private String hostID; // Assuming char(8) maps to a String in Java
    private String guestID; // Assuming char(8) maps to a String in Java
    private LocalDateTime start; // datetime maps to LocalDateTime
    private LocalDateTime end; // datetime maps to LocalDateTime
    private Integer hostWin; // tinyint(1) typically maps to int, but can also be boolean
    private Integer preRatingHost; // int maps to int
    private Integer preRatingGuest; // int maps to int
    private Integer postRatingHost; // int maps to int
    private Integer postRatingGuest;
}

