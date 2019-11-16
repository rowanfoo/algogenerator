package com.dharma.algogenerator;

import com.dharma.algogenerator.controller.admin.AsxMetaStockImport;
import com.dharma.algogenerator.data.entity.CoreData;
import com.dharma.algogenerator.data.entity.QCoreData;
import com.dharma.algogenerator.data.repo.DataRepo;
import com.dharma.algogenerator.service.Algo.AlgoAdminDaily;
import com.dharma.algogenerator.util.Notification;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AlgogeneratorApplicationTests {
    @Autowired
    ArrayList<LocalDate> holidays;
    @Autowired
    AsxMetaStockImport asxMetaStockImport;

//    @Test
//    public void contextLoads() {
//
////        holidays.forEach(a-> System.out.println("---mydate--"+a + "=======" + LocalDate.now().equals(a) ));
////
////        System.out.println("----GOT-------"+holidays.contains(LocalDate.now()));
//
//
//        //  asxMetaStockImport.importAllData();
//        asxMetaStockImport.algo();
//
//    }
//
//    @Autowired
//    Notification notification;
//
//    @Test
//    public void sendMSg() {
//        notification.sendMsg("algo gen ", " Just a test");
//    }
//
//    @Autowired
//    AlgoAdminDaily algo;
//
//    @Test
//    public void runAllAlgo() {
//        System.out.println("----RUN ALL ALGO-------");
//        try {
//            algo.executeAll();
//        } catch (Exception e) {
//            e.printStackTrace();
//
//
//        }
//
//    }
//
//    @Autowired
//    AlgoAdminDaily algoAdminDaily;
//
//    @Test
//    public void contextLoadss() {
//        algoAdminDaily.fallWithLowVolumeStrReplace();
//
//    }
//
//    @Autowired
//    ArrayList<String> allasxcodes;
//
//
//    @Autowired
//    DataRepo dataRepo;
//    @PersistenceContext
//    private EntityManager em;
//
//    @Test
//    public void checkrsi() {
//        JPAQuery query = new JPAQuery(em);
//        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//
//
//        List<CoreData> core = queryFactory.selectFrom(QCoreData.coreData)
//                .where(QCoreData.coreData.code.eq("BHP.AX"))
//                .limit(14).orderBy(new OrderSpecifier<>(Order.DESC, QCoreData.coreData.date)).fetch();
//
//
////        queryFactory.from(QCoreData.coreData)
////        .limit(14).orderBy(new OrderSpecifier<>(Order.DESC, QCoreData.coreData.date)).fetch().forEach(
////                a->{
////                    System.out.println("---------------------" +a);
////                }
////        );
//
//
////      Iterable<CoreData> core = dataRepo.findAll(QCoreData.coreData.code.eq("BHP.AX").and(QCoreData.coreData.date.gt(LocalDate.now().minusDays(14)) ) ,new QSort(QCoreData.coreData.date.asc()) );
////        Iterable<CoreData> core = dataRepo.findAll(QCoreData.coreData.code.eq("BHP.AX")  ,new QSort(QCoreData.coreData.date.asc()) );
//
//        Collections.reverse(core);
//        core.forEach(a -> {
//            System.out.println("---------------------------------" + a);
//
//
//        });
////        CoreData[] d = Iterables.toArray(core, CoreData.class);
//        //CoreData[] d = ( CoreData[] )core.toArray( );
//        CoreData[] d = core.stream().toArray(CoreData[]::new);
//
//
//        System.out.println("--------RSI-------------------------" + CalculateRsi(d));
//
//    }
//
//    public static double CalculateRsi(CoreData[] prices) {
//
//        double sumGain = 0;
//        double sumLoss = 0;
//        for (int i = 1; i < prices.length; i++) {
//            double difference = prices[i].getClose() - prices[i - 1].getClose();
////            if (difference >= 0) {
////                sumGain += difference;
////            } else {
////                sumLoss -= difference;
////            }
//            if (prices[i].getClose() > prices[i - 1].getClose()) {
//                sumGain += prices[i].getClose() - prices[i - 1].getClose();
//            } else {
//                sumLoss += prices[i - 1].getClose() - prices[i].getClose();
//            }
//        }
//
//        double relativeStrength = (sumGain) / (sumLoss);
//        System.out.println("---------relativeStrength------------------------" + relativeStrength * 100);
//        return 100.0 - (100.0 / (1 + relativeStrength));
//    }
//
//
//    @Test
//    public void checkfiles() {
////        System.out.println("------------------" +allasxcodes.size() );
//        allasxcodes.forEach(a -> {
//            try {
//                String uri = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + a + "&apikey=A8PW8M2EYECYC0CB";
//                ObjectMapper mapper = new ObjectMapper();
//                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                System.out.println("------------xx---------------------" + uri);
//                String node = null;
//                node = mapper.readValue(new URL(uri), String.class);
//                System.out.println("----data done --:" + node);
//
//
//                TimeUnit.SECONDS.sleep(1);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        });
//
//
//    }


}
