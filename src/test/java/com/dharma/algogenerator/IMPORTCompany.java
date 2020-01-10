package com.dharma.algogenerator;

import com.dharma.algogenerator.data.entity.AllStock;
import com.dharma.algogenerator.data.entity.CoreData;
import com.dharma.algogenerator.data.entity.CoreStock;
import com.dharma.algogenerator.data.entity.QAllStock;
import com.dharma.algogenerator.data.repo.AllStockRepo;
import com.dharma.algogenerator.data.repo.DataRepo;
import com.dharma.algogenerator.data.repo.StockRepo;
import com.dharma.algogenerator.service.Algo.AlgoAdminDaily;
import com.dharma.algogenerator.service.admin.CalcAverage;
import com.dharma.algogenerator.service.admin.CalcChangePercent;
import com.dharma.algogenerator.service.admin.CalcRSI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class IMPORTCompany {

    @Autowired
    AlgoAdminDaily algo;

    @Autowired
    DataRepo datarepo;
    @Autowired
    CalcAverage calcAverage;
    @Autowired
    CalcRSI calcRSI;

    @Autowired
    CalcChangePercent calcChangePercent;

    //EZY_CHART
    //ADD FILE PATH
//    String FILEPATH = "E:\\TEMP\\HistoricalData-011018.txt";
//    String FILEPATH = "E:\\TEMP\\NHC.txt";
//    String FILEPATH = "E:\\TEMP\\HistoricalData-2019-08-22.txt";
//    String FILEPATH = "E:\\TEMP\\AKP.txt";
//    String FILEPATH = "E:\\TEMP\\ID8.txt";
    String FILEPATH = "E:\\TEMP\\";
    //"HMD.txt"SPW,SPT
    List<String> ALLFILES = Arrays.asList(
            new String[]{"WBT.txt"}
    );


    @Autowired
    ArrayList<String> allasxcodes;
    @Autowired
    AllStockRepo allstockRepo;
    @Autowired
    StockRepo stockRepo;

    @Test
    public void runAllAlgo() {
        System.out.println("----RUN ALL ALGO-------");
        int count = 0;

        ALLFILES.forEach(filename -> {
            String path = FILEPATH + filename;
            boolean runonce = true;
            try {
                Scanner scanner = new Scanner(new File(path));

                while (scanner.hasNextLine()) {
                    String[] country = scanner.nextLine().split(" ");
                    //  System.out.println("-------------------------***********-------: " + country.length);
                    //System.out.println("-------------------------DATA----country---: "+ country[0] +  allcodes.contains( country[0].toUpperCase()));
                    //   System.out.println("-------------------------DATA----country---: "+allasxcodes);
                    if (runonce) {
                        String code = country[0].toUpperCase();
                        System.out.println("-------------------------DATA----country---:" + code + ":");
                        AllStock allStock = allstockRepo.findOne(QAllStock.allStock.code.eq(code)).get();
                        String top = "";
                        if (allStock.getMarketcap() > 100000000) top = "300";

                        CoreStock stock = CoreStock.builder()
                                .code(country[0].toUpperCase() + ".AX")
                                .date(LocalDate.now())
                                .category(allStock.getSubcategory())
                                .name(allStock.getName())
                                .top(top)
                                .tags("ISRAEL")
                                .build();
                        stockRepo.save(stock);
                        runonce = false;
                    }

                    LocalDate date = LocalDate.parse(country[1], DateTimeFormatter.ofPattern("yyMMdd"));

                    CoreData data = new CoreData(country[0] + ".AX", date, new Double(country[5]) / 100, new Double("0"), new Double("0"), new Double(country[2]) / 100, new Double(country[3]) / 100,
                            new Double(country[4]) / 100, new Long(country[6]));

                    System.out.println("-------------------------DATA-------: " + data);
                    datarepo.save(data);
                    //count++;
                }

                scanner.close();
                //   System.out.println("-------------------------CLOSING-------SAVE: " + count);
                datarepo.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }


        });


    }

    @Test
    public void calcPercent() {
        calcChangePercent.run();
    }

}
