package com.dharma.algogenerator.service.Algo;

import com.dharma.algogenerator.controller.admin.AsxMetaStockImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AlgoSchedule {
    @Autowired
    AsxMetaStockImport asxMetaStockImport;

    @Scheduled(cron = "0 15 15 ? * MON-FRI", zone = "GMT-8")
    public void run() {

        asxMetaStockImport.importAllData();

    }

}
