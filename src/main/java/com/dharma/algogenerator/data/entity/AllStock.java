package com.dharma.algogenerator.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
@Table(name = "allstocks")
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class AllStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String subcategory;
    private Long marketcap;
    private LocalDate date;

    public boolean equals(Object o) {
        String mycode = (String) o;
        System.out.println("----EQUALS-----" + this.getCode().equals(mycode));
        return this.getCode().equals(mycode);
    }

}
