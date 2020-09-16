package com.dharma.algogenerator;

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
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
///IMPORT stock from NAB
//***** DONT USE INSIGHT TRADER > VOLUME is REDUCE is 100x
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
//    String FILEPATH = "E:\\TEMP\\";
    // String FILEPATH = "E:\\TEMP\\";
    String FILEPATH = "E:\\TEMP\\STOCK6\\";
    //    String FILEPATH = "E:\\TEMP\\STOCK\\";
    //"HMD.txt"SPW,SPT
    List<String> ALLFILES = Arrays.asList(
            new String[]{"ERL.txt", "FDM.txt", "ORM.txt", "SOP.txt", "UNL.txt", "GIB.txt", "8CO.txt", "BNR.txt", "PEC.txt", "NGY.txt", "CAZ.txt", "HLX.txt", "BAT.txt", "KAM.txt", "AIR.txt", "ANA.txt", "HPR.txt", "TCO.txt", "WSI.txt", "BAR.txt", "BEE.txt", "KIS.txt", "CPV.txt", "IXU.txt", "FEI.txt", "RLG.txt", "SUD.txt", "BDG.txt", "CWL.txt", "CGN.txt", "EEU.txt", "CAD.txt", "TIA.txt", "AVE.txt", "UCW.txt", "KNM.txt", "AIY.txt", "TEG.txt", "ALY.txt", "BUX.txt", "CXU.txt", "HSC.txt", "KNO.txt", "DVN.txt", "SVH.txt", "FRM.txt", "JCS.txt", "CAF.txt", "MEY.txt", "LSR.txt", "ATX.txt", "POU.txt", "ESS.txt", "SER.txt", "DDD.txt", "INV.txt", "HCD.txt", "HRR.txt", "CWX.txt", "ERG.txt", "JRL.txt", "YPB.txt", "SIV.txt", "DHR.txt", "ODA.txt", "VMG.txt", "HWH.txt", "AGR.txt", "OLI.txt", "ABX.txt", "DTR.txt", "ACB.txt", "NWE.txt", "NXM.txt", "COY.txt", "MNW.txt", "RHI.txt", "CP1.txt", "AIV.txt", "AJY.txt", "ICR.txt", "WCG.txt", "PSL.txt", "AYR.txt", "MEL.txt", "GES.txt", "NYR.txt", "PM1.txt", "PFG.txt", "SVD.txt", "TDY.txt", "TKL.txt", "CCG.txt", "OAU.txt", "NAE.txt", "LPE.txt", "SRK.txt", "VTI.txt", "BUY.txt", "GUL.txt", "AJQ.txt", "CRS.txt", "MCM.txt", "MSR.txt", "KBC.txt", "IBG.txt", "ICG.txt", "RNT.txt", "APR.txt", "FTZ.txt", "MEB.txt", "KGM.txt", "TPC.txt", "WBE.txt", "GLH.txt", "CCE.txt", "IP1.txt", "AS1.txt", "ELE.txt", "FZR.txt", "CY5.txt", "MTC.txt", "SPQ.txt", "A3D.txt", "MIL.txt", "TNR.txt", "AJJ.txt", "AU8.txt", "SW1.txt", "DDB.txt", "ABV.txt", "POD.txt", "ODY.txt", "OGA.txt", "AEE.txt", "AMD.txt", "FYI.txt", "BNL.txt", "LSA.txt", "PIL.txt", "1ST.txt", "FPP.txt", "IVZ.txt", "ONE.txt", "ASQ.txt", "BOA.txt", "OLH.txt", "CAV.txt", "RFT.txt", "EGY.txt", "GRL.txt", "MBK.txt", "DCX.txt", "MGU.txt", "BCC.txt", "TSC.txt", "CE1.txt", "KPE.txt", "HNG.txt", "EN1.txt", "SCN.txt", "WWG.txt", "CR1.txt", "CRO.txt", "CDY.txt", "M8S.txt", "TMX.txt", "PWN.txt", "AOU.txt", "SRI.txt", "CNL.txt", "TKM.txt", "3DA.txt", "AFL.txt", "TDO.txt", "MBM.txt", "CL8.txt", "CYM.txt", "JAY.txt", "CXZ.txt", "VRS.txt", "PUA.txt", "NCL.txt", "ADX.txt", "HNR.txt", "C6C.txt", "AVQ.txt"}
            //            new String[]{"APE.TXT", "ADI.TXT", "ALG.TXT", "ALX.TXT", "AD8.TXT", "AVH.TXT", "BVS.TXT", "CTX.TXT", "CNI.TXT", "COF.TXT", "CQE.TXT", "COL.TXT", "DHG.TXT", "EHL.TXT", "HLS.TXT", "IAP.TXT", "ING.TXT", "INR.TXT", "ISX.TXT", "ITG.TXT", "JIN.TXT", "NWL.TXT", "OBL.TXT", "PDL.TXT", "PET.TXT", "PRN.TXT", "RDC.TXT", "SM1.TXT", "SOL.TXT", "UMG.TXT", "URW.TXT", "VEA.TXT", "VUK.TXT", "VVR.TXT", "XRO.TXT", "Z1P.TXT"}
    );
//***** DONT USE INSIGHT TRADER > VOLUME is REDUCE is 100x


    @Autowired
    ArrayList<String> allasxcodes;
    @Autowired
    AllStockRepo allstockRepo;
    @Autowired
    StockRepo stockRepo;

    @Test
    public void runAllAlgo() {
        System.out.println("----RUN ALL ALGO-------");
        // int count = 0;
        AtomicInteger count = new AtomicInteger();
        ALLFILES.forEach(filename -> {
            String path = FILEPATH + filename;
            boolean runonce = true;
            try {
                Scanner scanner = new Scanner(new File(path));

                while (scanner.hasNextLine()) {
                    String[] country = scanner.nextLine().split(" ");
                    //  System.out.println("-------------------------***********-------: " + country.length);
                    System.out.println("-------------------------START----CODE---: " + country[0] + ".AX");

//                    System.out.println("-------------------------DATA----country---: "+ country[0] +  allcodes.contains( country[0].toUpperCase()));
                    //   System.out.println("-------------------------DATA----country---: "+allasxcodes);
                    if (runonce) {
                        String code = country[0].toUpperCase();
                        System.out.println("-------------------------DATA----country---:" + code + ":");
                        //        AllStock allStock = allstockRepo.findOne(QAllStock.allStock.code.eq(code)).get();
                        StringBuffer name = new StringBuffer("");
                        StringBuffer category = new StringBuffer("");
                        StringBuffer top = new StringBuffer("");

                        allstockRepo.findOne(QAllStock.allStock.code.eq(code)).ifPresent(allStock1 -> {
                            name.append(allStock1.getName());
                            category.append(allStock1.getSubcategory());
                            if (allStock1.getMarketcap() > 100000000) top.append("300");
                        });


                        CoreStock stock = CoreStock.builder()
                                                   .code(country[0].toUpperCase() + ".AX")
                                                   .date(LocalDate.now())
                                                   .category(category.toString())
                                                   .name(name.toString())
                                                   .top(top.toString())
                                                   //  .tags("ISRAEL")
                                                   .build();

                        System.out.println("-------------------------CORE STOCK -------: " + stock);
                        stockRepo.save(stock);
                        runonce = false;
                    }

                    //***** DONT USE INSIGHT TRADER > VOLUME is REDUCE is 100x
                    //EZYChart
                    LocalDate date = LocalDate.parse(country[1], DateTimeFormatter.ofPattern("yyMMdd"));


                    //INSIGHT TRADER
                    //  LocalDate date = LocalDate.parse(country[1], DateTimeFormatter.ofPattern("MM/dd/yy"));
                    //METASTOCK
                    //LocalDate date = LocalDate.parse(country[1], DateTimeFormatter.ofPattern("yyMMdd"));
                    //METASTOCK

                    CoreData data = new CoreData(country[0] + ".AX", date, new Double(country[5]) / 100, new Double("0"), new Double("0"), new Double(country[2]) / 100, new Double(country[3]) / 100,
                            new Double(country[4]) / 100, new Long(country[6]));
                    //INSIGHT TRADER DONT USER
//                    CoreData data = new CoreData(country[0] + ".AX", date, new Double(country[5]) / 100, new Double("0"), new Double("0"), new Double(country[2]), new Double(country[3]),
//                            new Double(country[4]), new Long(country[6]));


                    System.out.println("-------------------------DATA-------: " + data);
                    datarepo.save(data);
                    count.incrementAndGet();
                    System.out.println("-------------------------END-----: ");

                }

                scanner.close();
                System.out.println("-------------------------CLOSING-------SAVE: " + count);
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
