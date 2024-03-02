package com.smu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: MingYun
 * @Date: 2024-02-28 16:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Matches {

    private String hostID;
    private String guestID;
    private LocalDateTime  start;
    private LocalDateTime  end;
    private Integer hostWin;
    private Integer preRatingHost;
    private Integer preRatingGuest;
    private Integer postRatingHost;
    private Integer postRatingGuest;
}

