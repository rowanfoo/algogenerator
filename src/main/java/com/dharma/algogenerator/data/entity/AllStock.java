package com.dharma.algogenerator.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Data
@Table(name = "allstock")
public class AllStock {
    @Id
    private Long id;
    private String code;
    private String name;
    private String subcategory;
    private Integer marketcap;
}
