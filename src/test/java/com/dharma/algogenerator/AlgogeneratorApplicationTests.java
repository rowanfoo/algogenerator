package com.dharma.algogenerator;

import com.dharma.algogenerator.controller.admin.AsxMetaStockImport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlgogeneratorApplicationTests {
@Autowired
    ArrayList<LocalDate> holidays;
@Autowired
    AsxMetaStockImport asxMetaStockImport;

    @Test
    public void contextLoads() {

//        holidays.forEach(a-> System.out.println("---mydate--"+a + "=======" + LocalDate.now().equals(a) ));
//
//        System.out.println("----GOT-------"+holidays.contains(LocalDate.now()));


        asxMetaStockImport.importAllData();


    }

}
