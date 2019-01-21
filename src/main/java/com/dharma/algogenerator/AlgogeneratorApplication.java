package com.dharma.algogenerator;

import com.dharma.algogenerator.data.entity.CoreStock;
import com.dharma.algogenerator.data.repo.DataRepo;
import com.dharma.algogenerator.data.repo.StockRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@SpringBootApplication

@EnableScheduling
@EnableCaching
@Slf4j
public class AlgogeneratorApplication {
    @Autowired
    DataRepo datarepo;
    @Autowired
    StockRepo stock ;


@Value("${holidays}") String holidays;

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

    @Bean
    public ArrayList<LocalDate> holidays() {
        ArrayList<LocalDate> arr = new ArrayList<>();
        System.out.println("---- HOLIDAYS  ------" + holidays );
        log.info("-----------------HOLIDAYS-------------- "+ holidays);

        StringTokenizer st = new StringTokenizer(holidays ,",");
        System.out.println("---- Split by space ------");

        while (st.hasMoreTokens()) {
           // System.out.println(st.nextElement());
            arr.add( (LocalDate.parse(st.nextToken() )));
        }
        return arr;

    }

        public static void main(String[] args) {
        SpringApplication.run(AlgogeneratorApplication.class, args);
    }
}
