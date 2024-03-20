package com.smu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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

    private Integer hostID;
    private Integer guestID;
    private LocalDateTime  start;
    private LocalDateTime  end;
    private boolean hostWin;
    private Integer preRatingHost;
    private Integer preRatingGuest;
    private Integer postRatingHost;
    private Integer postRatingGuest;

    public boolean getHostWin() {
        return hostWin;
    }

    public void setHostWin(boolean hostWin) {
        this.hostWin = hostWin;
    }

}

