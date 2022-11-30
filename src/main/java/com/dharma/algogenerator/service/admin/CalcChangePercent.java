package com.dharma.algogenerator.service.admin;

import com.dharma.algogenerator.data.entity.CoreData;
import com.dharma.algogenerator.data.entity.CoreDataMonth;
import com.dharma.algogenerator.data.entity.CoreDataWeek;
import com.dharma.algogenerator.data.entity.QCoreData;
import com.dharma.algogenerator.data.repo.CoreDataMonthRepo;
import com.dharma.algogenerator.data.repo.CoreDataWeekRepo;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Component
public class CalcChangePercent extends BaseAdmin {

    LocalDate date;

    @Override
    public void run() {
        System.out.println("---------------------->CalcChangePercent");

        for (String code : allasxcodes) {
            List<CoreData> coreData = datarepo.findTop2ByCodeOrderByDateDesc(code + ".AX");
            System.out.println(" ********************  DONE findTopTwoToday -----" + code + ".AX   " + coreData.size());

            CoreData coreDataToday = coreData.get(0);
            System.out.println(" ********************  DONE Today -----" + code + ".AX   " + coreDataToday.getClose());

            CoreData coreDataYest = coreData.get(1);
            System.out.println(" ********************  YEST Today -----" + code + ".AX   " + coreDataYest.getClose());


            Double change = coreDataToday.getClose() - coreDataYest.getClose();
            //          System.out.println("---------------------->CalcChangePercent------------" + change);
            coreDataToday.setChanges(change);
            if (change == 0.0) {
                coreDataToday.setChangepercent(0.0);
            } else {
                coreDataToday.setChangepercent(change / coreDataYest.getClose());

            }
//            System.out.println("---------------------->CalcChangePercent %%%%------------" + (change / coreDataYest.getClose()));
            datarepo.save(coreDataToday);
        }
    }

    @Autowired
CoreDataWeekRepo coreDataWeekRepo;

//    @Override
//    public void runweek() {
//        System.out.println("---------------------->CalcChangePercent WEEKLY");
//
//        for (String code : allasxcodes) {
//            List<CoreDataWeek> coreData = coreDataWeekRepo.findTop2ByCodeOrderByDateDesc(code + ".AX");
//            System.out.println(" ********************  DONE findTopTwoToday -----" + code + ".AX   " + coreData.size());
//
//            CoreDataWeek coreDataToday = coreData.get(0);
//            System.out.println(" ********************  DONE Today -----" + code + ".AX   " + coreDataToday.getClose());
//
//            CoreDataWeek coreDataYest = coreData.get(1);
//            System.out.println(" ********************  YEST Today -----" + code + ".AX   " + coreDataYest.getClose());
//
//
//            Double change = coreDataToday.getClose() - coreDataYest.getClose();
//            //          System.out.println("---------------------->CalcChangePercent------------" + change);
//            coreDataToday.setChanges(change);
//            if (change == 0.0) {
//                coreDataToday.setChangepercent(0.0);
//            } else {
//                coreDataToday.setChangepercent(change / coreDataYest.getClose());
//
//            }
////            System.out.println("---------------------->CalcChangePercent %%%%------------" + (change / coreDataYest.getClose()));
//            coreDataWeekRepo.save(coreDataToday);
//        }
//    }
//    interface Ddbfunction {
//        List<?> findTop2ByCodeOrderByDateDesc(String code);
//    }
//
//    @Override
//    public void runweeksp( JpaRepository jpa ,   Function<String , ? >  dbfunction ) {
//        System.out.println("---------------------->CalcChangePercent WEEKLY");
//
//        Function<String , ? > adder = coreDataWeekRepo::findTop2ByCodeOrderByDateDesc;
//
//        for (String code : allasxcodes) {
//            List<CoreDataWeek> coreData =  dbfunction.apply (code + ".AX");
//            System.out.println(" ********************  DONE findTopTwoToday -----" + code + ".AX   " + coreData.size());
//
//            CoreDataWeek coreDataToday = coreData.get(0);
//            System.out.println(" ********************  DONE Today -----" + code + ".AX   " + coreDataToday.getClose());
//
//            CoreDataWeek coreDataYest = coreData.get(1);
//            System.out.println(" ********************  YEST Today -----" + code + ".AX   " + coreDataYest.getClose());
//
//
//            Double change = coreDataToday.getClose() - coreDataYest.getClose();
//            //          System.out.println("---------------------->CalcChangePercent------------" + change);
//            coreDataToday.setChanges(change);
//            if (change == 0.0) {
//                coreDataToday.setChangepercent(0.0);
//            } else {
//                coreDataToday.setChangepercent(change / coreDataYest.getClose());
//
//            }
////            System.out.println("---------------------->CalcChangePercent %%%%------------" + (change / coreDataYest.getClose()));
//            jpa.save(coreDataToday);
//        }
//    }



    @PersistenceContext
    EntityManager em;

//
//    fun findLatestBID(tickerid: String): List<TickData> {
//
//        var sql = "SELECT * FROM Tick_Data c  WHERE   c.tickerid= ? and c.type='BID' order by c.date desc limit 1";
//        var query = em.createNativeQuery(sql, TickData::class.java);
//        query.setParameter(1, tickerid)
//        return query.getResultList() as List<TickData>
//
//    }
//}

    @Autowired
    ArrayList<String> allasxcodes;

    public void all() {
        for (String code : allasxcodes) {
            System.out.println("---------------------->CalcChangePercentALL: " + code);

            String sql = "SELECT * FROM core_data c  WHERE   c.code= ? order by c.date desc";
            Query query = em.createNativeQuery(sql , CoreData.class);
            query.setParameter(1, code+".AX");
            List<CoreData> coreData = (List<CoreData>) query.getResultList();

            for(int x=0 ; x <coreData.size()-1 ; x++ ){
                CoreData coreDataToday = coreData.get(x);
               // System.out.println(" ********************  DONE Today -----" + code + ".AX   " + coreDataToday.getClose());

                CoreData coreDataYest = coreData.get(x+1);
                //System.out.println(" ********************  YEST Today -----" + code + ".AX   " + coreDataYest.getClose());

                Double change = coreDataToday.getClose() - coreDataYest.getClose();
                change  = Math.round(change * 100.0) / 100.0;

//                System.out.println("---------------------->CalcChangePercent----change --------" + change);
                coreDataToday.setChanges(change);
                if (change == 0.0) {
                    coreDataToday.setChangepercent(0.0);
                } else {
                    Double db = change / coreDataYest.getClose();
                    //  System.out.println("=======change Percent dbbb++" + db);
                    db  = (Math.round(db * 100.0) / 100.0)*100;
  //                  System.out.println("=======change Percent ===" + db);
                    coreDataToday.setChangepercent(db);
                }
                datarepo.save(coreDataToday);
            }
            datarepo.flush();
        }




//            System.out.println("---------------------->CalcChangePercent %%%%------------" + (change / coreDataYest.getClose()));
//            datarepo.save(coreDataToday);
//        }
    }


    @Autowired
    CoreDataMonthRepo coreDataMonthRepo;


    public void allmonth() {
        ArrayList<String> nodata = new ArrayList<String>();
        nodata.add("SGA.AX");
        nodata.add("MKG.AX");
        nodata.add("AII.AX");
//        for (String code : allasxcodes) {
        for (String code : nodata) {

            System.out.println("---------------------->CalcChangePercentALL: " + code);

            String sql = "SELECT * FROM core_data_month c  WHERE   c.code= ? order by c.date desc";
            Query query = em.createNativeQuery(sql , CoreDataMonth.class);
            query.setParameter(1, code+".AX");
            List<CoreDataMonth> coreData = (List<CoreDataMonth>) query.getResultList();

            for(int x=0 ; x <coreData.size()-1 ; x++ ){
                CoreDataMonth coreDataToday = coreData.get(x);
                // System.out.println(" ********************  DONE Today -----" + code + ".AX   " + coreDataToday.getClose());

                CoreDataMonth coreDataYest = coreData.get(x+1);
                //System.out.println(" ********************  YEST Today -----" + code + ".AX   " + coreDataYest.getClose());

                Double change = coreDataToday.getClose() - coreDataYest.getClose();
                change  = Math.round(change * 100.0) / 100.0;

//                System.out.println("---------------------->CalcChangePercent----change --------" + change);
                coreDataToday.setChanges(change);
                if (change == 0.0) {
                    coreDataToday.setChangepercent(0.0);
                } else {
                    Double db = change / coreDataYest.getClose();
                    //  System.out.println("=======change Percent dbbb++" + db);
                    db  = (Math.round(db * 100.0) / 100.0)*100;
                    //                  System.out.println("=======change Percent ===" + db);
                    coreDataToday.setChangepercent(db);
                }
                coreDataMonthRepo.save(coreDataToday);
            }
            coreDataMonthRepo.flush();
        }




//            System.out.println("---------------------->CalcChangePercent %%%%------------" + (change / coreDataYest.getClose()));
//            datarepo.save(coreDataToday);
//        }
    }




    public void allweek() {

List<String> tmp = new ArrayList();

//        tmp.add("SGA.AX");
//        tmp.add("MKG.AX");
//        tmp.add("AII.AX");
        for (String code : allasxcodes) {


         //   for (String code : tmp) {
            System.out.println("---------------------->CalcChangePercentALL: " + code);

            List<CoreDataWeek> coreData = coreDataWeekRepo.findTop2ByCodeOrderByDateDesc(code);

            for(int x=0 ; x <coreData.size()-1 ; x++ ){
                CoreDataWeek coreDataToday = coreData.get(x);
                 System.out.println(" ********************  DONE Today -----"+  coreDataToday.date  +"::" + code + ".AX   " + coreDataToday.getClose());

                CoreDataWeek coreDataYest = coreData.get(x+1);
                System.out.println(" ********************  YEST Today -----"+coreDataYest.date  +"::"  + code + ".AX   " + coreDataYest.getClose());

                Double change = coreDataToday.getClose() - coreDataYest.getClose();
                change  = Math.round(change * 100.0) / 100.0;

//                System.out.println("---------------------->CalcChangePercent----change --------" + change);
                coreDataToday.setChanges(change);
                if (change == 0.0) {
                    coreDataToday.setChangepercent(0.0);
                } else {
                    Double db = change / coreDataYest.getClose();
                    //  System.out.println("=======change Percent dbbb++" + db);
                    db  = (Math.round(db * 100.0) / 100.0)*100;
                    //                  System.out.println("=======change Percent ===" + db);
                    coreDataToday.setChangepercent(db);
                }
                coreDataWeekRepo.save(coreDataToday);
            }
            coreDataWeekRepo.flush();
        }

    }



}
