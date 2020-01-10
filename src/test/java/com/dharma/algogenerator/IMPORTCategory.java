package com.dharma.algogenerator;

import com.dharma.algogenerator.data.entity.*;
import com.dharma.algogenerator.data.repo.AllStockRepo;
import com.dharma.algogenerator.data.repo.DataRepo;
import com.dharma.algogenerator.data.repo.StockRepo;
import com.dharma.algogenerator.service.Algo.AlgoAdminDaily;
import com.dharma.algogenerator.service.admin.CalcAverage;
import com.dharma.algogenerator.service.admin.CalcChangePercent;
import com.dharma.algogenerator.service.admin.CalcRSI;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class IMPORTCategory {

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
    String FILEPATH = "C:\\Users\\rowan\\Downloads\\";
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
    @Transactional
    public void getFromcsv() {
        System.out.println("---------etFromcsv-------------");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("C:\\Users\\rowan\\Desktop\\MINERALSDONE2.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            if (!allasxcodes.contains(data[0].trim())) {

                ArrayList<String> arrayList = new ArrayList<>();

                for (int i = 2; i < data.length; i++) {
                    if (!data[i].isEmpty()) arrayList.add(data[i].trim().replace("\"", "").replace(",", ""));
                }
                String cat = String.join(",", arrayList);

                System.out.println(data[0] + "----------" + cat);

            }


        }
    }


    public void runAllAlgo(String name, String cat) {
        System.out.println("----RUN ALL ALGO-------");
        int count = 0;

        String path = FILEPATH + name + ".txt";
        try {
            Scanner scanner = new Scanner(new File(path));
            if (scanner.hasNextLine()) makecode(name, cat);

            while (scanner.hasNextLine()) {
                String[] country = scanner.nextLine().split(" ");
                //  System.out.println("-------------------------***********-------: " + country.length);
                //System.out.println("-------------------------DATA----country---: "+ country[0] +  allcodes.contains( country[0].toUpperCase()));
                //   System.out.println("-------------------------DATA----country---: "+allasxcodes);

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


    }


    private void makecode(String code, String cat) {
        System.out.println("-------------------------DATA----country---:" + code + ":");
        AllStock allStock = allstockRepo.findOne(QAllStock.allStock.code.eq(code)).get();
        String top = "";
        if (allStock.getMarketcap() > 100000000) top = "300";
        CoreStock stock = CoreStock.builder()
                .code(code.toUpperCase() + ".AX")
                .date(LocalDate.now())
                .category(allStock.getSubcategory())
                .name(allStock.getName())
                .top(top)
                .tags(cat)
                .build();
        stockRepo.save(stock);


    }

    @Test
    public void makenews() {
        String code = "NHC";
        String url = " https://www.asx.com.au/asx/1/company/" + code + "/announcements?count=20";
        try {
            HttpResponse<JsonNode> j = Unirest.get(url).asJson();

            //System.out.println("----ans----" + j.getBody().toString());

            //    JSONObject json = j.getBody().getObject().getJSONObject("data");


            //System.out.println("----ans----" + j.getBody().getObject().getJSONArray("data").getClass().getName());
            JSONArray arr = j.getBody().getObject().getJSONArray("data");


            arr.forEach(a -> {
                System.out.println("----ans----" + ((JSONObject) a).getString("header"));
                String date = ((JSONObject) a).getString("document_release_date");
                date = date.substring(0, date.indexOf("T"));
                LocalDate dt = LocalDate.parse(date);
                System.out.println("----ans----" + dt);
                News news = News.builder()
                        .code(code).date(dt).title(((JSONObject) a).getString("header")).title(((JSONObject) a).getString("url")).build();


            });

            // JSONObject json = arr.getJSONObject(0);

            //  String token = json.getString("token");
            //   System.out.println("----ans----" + json.getString("header"));


        } catch (UnirestException e) {
            e.printStackTrace();
        }


    }


}


//{
//        "relative_url":"/asxpdf/20191220/pdf/44cs89300clts7.pdf",
//        "number_of_pages":1,
//        "size":"224.2KB",
//        "document_date":"2019-12-20T13:38:04+1100",
//        "market_sensitive":true,
//        "header":"Colton Project Update","id":"02187984",
//        "url":"http://www.asx.com.au/asxpdf/20191220/pdf/44cs89300clts7.pdf",
//        "document_release_date":"2019-12-20T13:43:43+1100",
//        "legacy_announcement":false}
