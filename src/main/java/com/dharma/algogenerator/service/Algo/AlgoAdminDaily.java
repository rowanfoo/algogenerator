package com.dharma.algogenerator.service.Algo;

import com.dharma.algogenerator.data.entity.CoreData;
import com.dharma.algogenerator.data.entity.QCoreData;
import com.dharma.algogenerator.data.entity.TechTechstr;
import com.dharma.algogenerator.data.repo.DataRepo;
import com.dharma.algogenerator.data.repo.StockRepo;
import com.dharma.algogenerator.data.repo.TechStrRepo;
import com.dharma.algogenerator.dto.MovingAverage;
import com.dharma.algogenerator.util.FormatUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AlgoAdminDaily {

    @Autowired
    StockRepo repo;

    @Autowired
    DataRepo datarepo;


    @Autowired
    TechStrRepo techStrRepo;


    @Autowired
    ArrayList<String > allasxcodes;
    @PersistenceContext
    private EntityManager entityManager;



    LoadingCache<String, LocalDate> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(4, TimeUnit.HOURS)
            .build(new CacheLoader<String, LocalDate>() {
                @Override
                public LocalDate load(String arg0) throws Exception {
                    System.out.println("---------------cachebuilder add :" +arg0);
                    return  getLatestDate();
                }
            });



    public void executeAll() throws Exception{
//        Method[] allMethods = this.getClass().getDeclaredMethods();
//        Object[] parameters = { null };
//        for (Method m : allMethods) {
//
//            try {
//                System.out.println("----AlgoAdminDaily --- executeAll :"+m.getName()  );
//                m.invoke(this,parameters);
//            } catch (Exception e) {
//                System.out.println("!!!!!!!!!!!!!!!!!!ERROE----AlgoAdminDaily --- executeAll ------ "+e);
//            }
//        }

        System.out.println ("---------------------START ALGO ---------------> ");
        log.info("-----------------START ALGO-------------- ");


            List<CompletableFuture> arr = new ArrayList<>();
        arr.add(CompletableFuture.runAsync(() ->  consequitveDayFallStr()));
        arr.add(CompletableFuture.runAsync(() ->  downGreater40Percent()));
        arr.add(CompletableFuture.runAsync(() ->  fiftyDayDistance()));
        arr.add(CompletableFuture.runAsync(() ->  fallWithLowVolumeStrReplace()));
        arr.add(CompletableFuture.runAsync(() ->  movingAveragetwoHundred()));
        arr.add(CompletableFuture.runAsync(() ->   queryDsltwoHundredCrossFourHundred(0.01,0.05)));
        arr.add(CompletableFuture.runAsync(() ->  queryDslLowRSI()));
        arr.add(CompletableFuture.runAsync(() ->  down4PercenteEst()));
        arr.add(CompletableFuture.runAsync(() ->   greaterVolAvg(3)));
        arr.add(CompletableFuture.runAsync(() ->   queryDslfiftyDayless(-0.2)));


        CompletableFuture.allOf(arr.toArray(new  CompletableFuture[arr.size() ])).get();



//        consequitveDayFallStr();
//        downGreater40Percent();
//        fiftyDayDistance();
//        fallWithLowVolumeStrReplace();
//        movingAveragetwoHundred();
//
//        queryDsltwoHundredCrossFourHundred(0.01,0.05);
//        queryDslLowRSI();
//        down4PercenteEst();
//        greaterVolAvg(3);
//        queryDslfiftyDayless(-0.2);
//        //    breakRoundNumber();
//        consequitveDayFallStr();
//        downGreater40Percent();
//        log.info("-----------------DONE ALGO 3-------------- ");
//
//        fiftyDayDistance();
//       // fiftyDayless(0.2);
//        fallWithLowVolumeStrReplace();
//        movingAveragetwoHundred();
//        log.info("-----------------DONE ALGO 6-------------- ");
//
//        queryDsltwoHundredCrossFourHundred(0.01,0.05);
//        queryDslLowRSI();
//        queryDslfiftyDayless(0.2);
//        log.info("-----------------DONE ALGO 9-------------- ");
//
//        down4PercenteEst();
//        greaterVolAvg(3);
//        System.out.println ("---------------------DONE ALL ALGO ---------------> ");
//        log.info("-----------------DONE ALL ALGO-------------- ");

    }

//// TODO: 3/26/2018  cant get to work
//2019-01-03  dont seem to be working
//    public void breakRoundNumber(){
//        System.out.println ("breakRoundNumber ---------------> ");
//
//        allasxcodes.forEach((a)->{
//        List<Object[]> coreData = datarepo.breakRoundNumber(a+".AX");
//            System.out.println ("breakRoundNumber ----code-----------> "+a+".AX");
//        for (Object[] result : coreData) {
//           // System.out.println ("breakRoundNumber ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
//            System.out.println ("breakRoundNumber ----PRICE-----------> "+result[0] + " : " + result[0].getClass().getName()  + " : "  + result[1].getClass().getName() + " : "  + result[1] );
//
//            TechTechstr str = new TechTechstr(((Double)result[0]).toString() , ((java.sql.Date)result[1]).toLocalDate(), 23);
//
//            //str.setClose(Double.parseDouble( result[2]+""));
//            //str.setOpen(Double.parseDouble( result[3]+""));
//            str.setClose((Double) result[2]);
//            str.setOpen((Double)result[3]);
//
//            techStrRepo.save(str);
//            System.out.println ("breakRoundNumber ---------------> "+str);
//
//        }
//
//        });
//    }


    public void downGreater40Percent(){
        System.out.println ("downGreater40Percent ---------------> ");

        allasxcodes.forEach((a)->{
            List<Object[]> coreData = datarepo.downGreater40Percent(a+".AX");

            for (Object[] result : coreData) {

                TechTechstr str = new TechTechstr(a+".AX" ,  getLatestDate(), 28);
              //  System.out.println ("downGreater40Percent --------------code -> " + a);
             //   System.out.println ("downGreater40Percent --------------->res1 " + result[0]);
               // System.out.println ("downGreater40Percent --------------->res2 "+result[1]);
                if (result[0]==null) continue ;
                double min = Double.parseDouble( result[0]+"");
                double max = Double.parseDouble( result[1]+"");
                double no = (max-min)/max;

                if(no>0.3){
                    str.setNotes( "fall from "+max+" to "+min +" % fall "+Math.round(no*100)  );
                   techStrRepo.save(str);

                }
            }
        });
        System.out.println ("downGreater40Percent --DONE-------------> ");
    }
    public void enddayvolgreaterfourty() {
        List<Object[]> coreData  = datarepo.enddayvolgreaterfourty();

        for (Object[] result : coreData) {
            System.out.println ("enddayvolgreaterfourty ---------- code-----> :" + result[0]);
            System.out.println ("enddayvolgreaterfourty ---------- date- ----> :" + getCacheDate());
            TechTechstr str = new TechTechstr((String)result[0] , getCacheDate(), 33);
            str.setFiftycount( Long.parseLong( result[1]+""));
            str.setVolume( Long.parseLong( result[2]+""));
            str.setChangepercent(FormatUtil.roundDouble( str.getFiftycount() / str.getVolume()    ) );

            techStrRepo.save(str);

        }


     //   enddayvolgreaterfourty
    }



    public void consequitveDayFallStr () {
        System.out.println ("consequitveDayFallStr ---------------> ");
        LocalDate mydate = FormatUtil.getWorkDay(getCacheDate(), 6);
        List<Object[]> coreData = datarepo.consequitveDayFallStr(mydate.toString());

        for (Object[] result : coreData) {
           System.out.println ("consequitveDayFallStr ---------- code-----> :" + result[0]);
            System.out.println ("consequitveDayFallStr ---------- date- ----> :" + getCacheDate());
            TechTechstr str = new TechTechstr((String)result[0] , getCacheDate(), 6);
            str.setFifty( Double.parseDouble( result[1]+""));
            str.setClose(Double.parseDouble( result[2]+""));
            str.setClose( Double.parseDouble( result[3]+""));

            techStrRepo.save(str);

        }


    }
    public void  movingAveragetwoHundred(){
        System.out.println("------movingAveragetwoHundred---------" );

        List<Object[]> coreData = datarepo.movingAveragebetween(MovingAverage.onehundredfifty,0.005,0.05);

        for (Object[] result : coreData) {
            System.out.println ("movingAveragetonehundredfifty ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
            String mysql1 ="INSERT INTO techstr (code,date ,mode,close,twohundredchg) VALUES (?,?,31,?,?)"  ;

            TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 31);
            str.setClose(Double.parseDouble( result[1]+""));
            str.setTwohundredchg(Double.parseDouble( result[2]+""));

                 techStrRepo.save(str);
            System.out.println ("movingAverageonehundredfifty ---------------> "+str);

        }
        coreData = datarepo.movingAveragebetween(MovingAverage.twenty,0.005,0.05);

        for (Object[] result : coreData) {
            System.out.println ("movingAveragetwenty ---------------> "+result[0] + " " + result[1] + " - " + result[2]);
            String mysql1 ="INSERT INTO techstr (code,date ,mode,close,twohundredchg) VALUES (?,?,29,?,?)"  ;

            TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 29);
            str.setClose(Double.parseDouble( result[1]+""));
            str.setTwohundredchg(Double.parseDouble( result[2]+""));

                techStrRepo.save(str);
            System.out.println ("movingAveragetwenty---------------> "+str);

        }


    }


    public void  fiftyDayDistance() {
        List<Object[]> coreData = datarepo.fiftyDayDistance();
        System.out.println ("fiftyDayDistance START--------------> ");

        for (Object[] result : coreData) {
            System.out.println ("fiftyDayDistance -1--------------> "+result[0] + " " + result[1] + " - " + result[2]);


            TechTechstr str = new TechTechstr((String)result[0] , ((java.sql.Date)result[1]).toLocalDate(), 2);
            str.setClose(Double.parseDouble( result[2]+""));
            str.setFifty(Double.parseDouble( result[3]+"") );
            str.setFiftychg(Double.parseDouble( result[4]+""));

                 techStrRepo.save(str);
            System.out.println ("fiftyDayDistance -2--------------> "+str);

        }

        System.out.println ("fiftyDayDistance DONE--------------> ");
    }

    // 200 cross 400
    public void  queryDsltwoHundredCrossFourHundred(double start , double end ){
        System.out.println("---------queryDsltwoHundredCrossFourHundred-------->  " );

        NumberPath<Double> fourhundred = QCoreData.coreData.fourhundred;
        NumberPath<Double> twohundred = QCoreData.coreData.twohundred;


        Iterable<CoreData>  data  = datarepo.findAll(
                twohundred.subtract(fourhundred).divide(fourhundred).between(start, end)
                        .and (QCoreData.coreData.date.eq (getCacheDate()  ))
        );
       data.forEach((a)->{
            TechTechstr str = new TechTechstr(a.getCode() , a.getDate(), 32);
            str.setTwohundredchg(a.getTwohundredchg());
            techStrRepo.save(str);
        });

    }

    //low rsi
    public void  queryDslLowRSI(){
        System.out.println("---------queryDslLowRSI-------->  " );
        Iterable<CoreData>  data  = datarepo.findAll(
                QCoreData.coreData.rsi.lt(28)
                        .and (QCoreData.coreData.date.eq (getLatestDate()))
        );

        data.forEach((a)->{
            TechTechstr str = new TechTechstr(a.getCode() , a.getDate(), 24);
            str.setChangepercent(a.getRsi() );
            techStrRepo.save(str);
        } );


    }



    //fall with low vol
    public void  fallWithLowVolumeStrReplace() {
        System.out.println("---------queryFallLowVol-------->  " );
        Iterable<CoreData>  data  = datarepo.findAll(
                (QCoreData.coreData.volume.lt(QCoreData.coreData.avg3mth.divide(2))
                        .and(QCoreData.coreData.changepercent.lt(-0.04)    )
                        .and (QCoreData.coreData.date.eq (getLatestDate())))) ;
        data.forEach((a)->{
            TechTechstr str = new TechTechstr(a.getCode() , a.getDate(), 7);
            str.setVolume(a.getVolume());
            str.setThreemthvol(a.getAvg3mth() );
            str.setChangepercent(a.getChangepercent() );
            techStrRepo.save(str);
        } );

    }


    //down 4%
    public void  down4PercenteEst() {
        System.out.println("---------queryFall4%more-------->  " );

        Iterable<CoreData>  data  = datarepo.findAll(
                        (QCoreData.coreData.changepercent.lt(-0.04)    )
                        .and (QCoreData.coreData.date.eq (getCacheDate()))) ;


        data.forEach((a)->{
            TechTechstr str = new TechTechstr(a.getCode() , a.getDate(), 9);
            str.setClose(a.getClose() );
            str.setFifty(FormatUtil.roundDouble(a.getFifty())  );
            str.setFiftychg(a.getChangepercent() );
            str.setChangepercent(FormatUtil.roundDouble(a.getFiftychg()) );
            techStrRepo.save(str);
        } );





    }

    //Large volume than average
    public void   greaterVolAvg(int multiply){

        System.out.println("---------greaterVolAvg%more-------->  " );

        Iterable<CoreData>  data  = datarepo.findAll(
                (QCoreData.coreData.volume.gt(QCoreData.coreData.avg3mth.multiply(multiply))    )
                        .and (QCoreData.coreData.date.eq (getCacheDate()))) ;
        data.forEach((a)->{
            TechTechstr str = new TechTechstr(a.getCode() , a.getDate(), 3);
            str.setClose(a.getClose() );
            str.setVolume(a.getVolume());
            str.setThreemthvol(  a.getAvg3mth() );



            str.setChangepercent(a.getChangepercent() );
            techStrRepo.save(str);

       });

    }
//    public  void fiftyDayless(double fiftydaylesspercent){
//        System.out.println("---------fiftyDayless%more-------->  " );
//        Iterable<CoreData>  data  = datarepo.findAll(
//                (QCoreData.coreData.fiftychg.lt(fiftydaylesspercent)    )
//                        .and (QCoreData.coreData.date.eq (getCacheDate()))) ;
//        data.forEach((a)->{
//            TechTechstr str = new TechTechstr(a.getCode() , a.getDate(), 1);
//            str.setClose(a.getClose());
//            str.setFifty(a.getFifty());
//            str.setFiftychg(FormatUtil.roundDouble(a.getFiftychg()));
//            techStrRepo.save(str);
//
//        });
//        System.out.println("---------fiftyDayless%more- DONE------->  " );
//    }
    //less than 50d AVG
    public void  queryDslfiftyDayless(double  fiftydayaverage ){
        System.out.println("---------queryDslfiftyDayless-------->  " );
        Iterable<CoreData>  data  = datarepo.findAll(
                QCoreData.coreData.fiftychg.lt(fiftydayaverage)
                        .and (QCoreData.coreData.date.eq (getLatestDate()))
        );

        data.forEach((a)->{
           // System.out.println("---------queryDslfiftyDayless-------->  " +a.getCode() +" :  " + a.getFifty() );
            TechTechstr str = new TechTechstr(a.getCode() , a.getDate(), 1);
            str.setClose(a.getClose());
            str.setFifty(a.getFifty() );
            str.setFiftychg(FormatUtil.roundDouble(a.getFiftychg() ));
            techStrRepo.save(str);
        } );
        System.out.println("---------queryDslfiftyDayless- DONE------->  " );
    }






    private LocalDate getCacheDate(){
      try {
          return cache.get("localdate");
        } catch (Exception e) {
            System.out.println(" " + e);
        }
        return null;
    }

    private LocalDate getLatestDate(){

        JPAQuery query = new JPAQuery(entityManager);
        Object obj = query.from(QCoreData.coreData).select(QCoreData.coreData.date.max()   ).fetchOne();
        return (LocalDate)obj ;

    }



}
