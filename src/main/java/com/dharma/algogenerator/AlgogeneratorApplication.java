package com.dharma.algogenerator;

import com.dharma.algogenerator.data.entity.CoreStock;
import com.dharma.algogenerator.data.repo.DataRepo;
import com.dharma.algogenerator.data.repo.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication

@EnableScheduling
@EnableCaching

public class AlgogeneratorApplication {
    @Autowired
    DataRepo datarepo;
    @Autowired
    StockRepo stock ;




    @Bean
    public ArrayList<String> allasxcodes(){
        ArrayList allcodes = new ArrayList<String>();

        System.out.println("--------allasxcodes--------- ");
        List<CoreStock> core =  stock.findAll();
        System.out.println("--------allasxcodes 2--------- ");
        core.forEach((a)->{
            allcodes.add(  a.getCode().substring(0 ,  a.getCode().indexOf(".")  )  );
        });

        System.out.println(" getAllCodeFileToRun codes " +allcodes.size() );
        return 	allcodes;



    }

    public static void main(String[] args) {
        SpringApplication.run(AlgogeneratorApplication.class, args);
    }
}
