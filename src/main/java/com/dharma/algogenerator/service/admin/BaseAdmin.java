package com.dharma.algogenerator.service.admin;

import com.dharma.algogenerator.data.repo.DataRepo;
import com.dharma.algogenerator.data.repo.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Component
public abstract class BaseAdmin {
    LocalDate date;
    @Autowired
    StockRepo repo;

    @Autowired
    DataRepo datarepo;
    @Autowired
    ArrayList<String > allasxcodes;

    BaseAdmin(LocalDate date){
        this.date=date;
    }
    BaseAdmin(){
    }


 public abstract void run();



}
