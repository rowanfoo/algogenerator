package com.dharma.algogenerator;

import com.dharma.algogenerator.data.entity.CoreData;
import com.dharma.algogenerator.data.repo.DataRepo;
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
import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class IMPORTFILE {

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
//    String FILEPATH = "E:\\TEMP\\BIN.txt";
//    String FILEPATH = "E:\\TEMP\\BUD.txt";
    // String FILEPATH = "E:\\TEMP\\SMI-1.txt";
//    String FILEPATH = "E:\\TEMP\\HistoricalData-2020-04-23.txt";
//    String FILEPATH = "E:\\TEMP\\HistoricalData-2020-07-09.txt";
    String FILEPATH = "E:\\TEMP\\HistoricalData-2020-08-21.txt";  /// EZY chart


    @Autowired
    ArrayList<String> allasxcodes;

    @Test
    public void runAllAlgo() {
        System.out.println("----RUN ALL ALGO-------");
        int count = 0;
        try {


            Scanner scanner = new Scanner(new File(FILEPATH));
            while (scanner.hasNextLine()) {

                // use comma as separator
                String[] country = scanner.nextLine().split(" ");
                //  System.out.println("-------------------------***********-------: " + country.length);
                //    System.out.println("-------------------------DATA----country---: " + country[0] + "::" + allasxcodes.contains((country[0].toUpperCase()) + ".AX"));
                //  System.out.println("-------------------------DATA----country---: " + allasxcodes);
                if (allasxcodes.contains(country[0].toUpperCase())) {
                    System.out.println("-------------------------DATA----country---: " + country[0].toUpperCase());

                    LocalDate date = LocalDate.parse(country[1], DateTimeFormatter.ofPattern("yyMMdd"));

                    CoreData data = new CoreData(country[0] + ".AX", date, new Double(country[5]) / 100, new Double("0"), new Double("0"), new Double(country[2]) / 100, new Double(country[3]) / 100,
                            new Double(country[4]) / 100, new Long(country[6]));

                    System.out.println("-------------------------DATA-------: " + data);
                    datarepo.save(data);
                    datarepo.flush();
                    count++;
                }


            }

            scanner.close();
            System.out.println("-------------------------CLOSING-------SAVE: " + count);
            datarepo.flush();
            calcChangePercent.run();
            System.out.println("-------------------------CHG PERCENT RUN DONE-------: ");

            //  calcAverage.run();
            System.out.println("-------------------------AVERAGE RUN DONE-------: ");

            //  calcRSI.run();
            System.out.println("-------------------------RSI RUN DONE-------: ");

            System.out.println("-------------------------ALGO RUN-------: ");

            //  algo.executeAll();
            System.out.println("-------------------------ALGO DONE-------: ");


        } catch (Exception e) {
            e.printStackTrace();


        }

    }

    @Test
    public void calcPercent() {
        calcChangePercent.run();
    }

}
