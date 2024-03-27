package com.smu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: MingYun
 * @Date: 2024-02-28 16:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

        private Integer id;

        private String name;

        private LocalDate birthdate;

        private  Integer rating ;

        private String state;


}
