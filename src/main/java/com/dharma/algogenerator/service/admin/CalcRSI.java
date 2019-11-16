package com.dharma.algogenerator.service.admin;

import com.dharma.algogenerator.data.entity.CoreData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalcRSI extends BaseAdmin {
    @Override
    public void run() {

        System.out.println(" --------- calcRSI START -----");
        int periodLength = 14;
        for (String code : allasxcodes) {


            List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc(code + ".AX");
            System.out.println(" --------- calcRSI START 1 -----" + code + ".AX");
            CoreData coreDataToday = coreData.get(0);
            CoreData coreDataYest = coreData.get(1);
            System.out.println(" --------- calcRSI START 2-----");
            Double today = coreDataToday.getClose();
            Double yest = coreDataYest.getClose();
            Double avgUp = coreDataYest.getAvgup();
            Double avgDown = coreDataYest.getAvgdown();
            System.out.println(" --------- calcRSI START 3-----");
            Double change = coreDataToday.getClose() - coreDataYest.getClose();
            Double gains = Math.max(0, change);
            Double losses = Math.max(0, -change);
            System.out.println(" --------- calcRSI START 4-----");
            System.out.println(" --------- calcRSI START 4-----" + avgUp);
            System.out.println(" --------- calcRSI START 4-----" + avgDown);

            System.out.println(" --------- calcRSI START 4-----" + gains);
            System.out.println(" --------- calcRSI START 4-----" + losses);
            avgUp = ((avgUp * (periodLength - 1)) + gains) / (periodLength);
            System.out.println(" --------- calcRSI START 4.1-----");
            avgDown = ((avgDown * (periodLength - 1)) + losses) / (periodLength);
            System.out.println(" --------- calcRSI START 4.2-----");
            Double rsi = 100 - (100 / (1 + (avgUp / avgDown)));

            System.out.println(" --------- calcRSI START 5-----");

            coreDataToday.setAvgup(avgUp);
            coreDataToday.setAvgdown(avgDown);

            coreDataToday.setRsi(rsi);
            datarepo.save(coreDataToday);

            System.out.println(" --------- calcRSI done-----");
        }
        System.out.println("--------------DONE -------->CalcRSI");

    }


}
