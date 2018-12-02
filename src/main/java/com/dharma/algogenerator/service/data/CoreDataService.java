package com.dharma.algogenerator.service.data;

import com.dharma.algogenerator.data.entity.CoreData;
import com.dharma.algogenerator.data.entity.QCoreData;
import com.dharma.algogenerator.data.repo.DataRepo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CoreDataService {
    @Autowired
    DataRepo dataRepo;

    public Iterable<CoreData> getDataFrom(String code , String date){

        Iterable<CoreData> data =  dataRepo.findAll(QCoreData.coreData.date.gt(LocalDate.parse(date)).and(QCoreData.coreData.code.eq(code)));
        return data;
    }

    @Cacheable("coredata")
      public CoreData getData(String code , String date){
            System.out.println("----------------------- get DATA---------------");
        Optional <CoreData> data =  dataRepo.findOne (
                            QCoreData.coreData.date.eq(LocalDate.parse(date)).
                            and(QCoreData.coreData.code.eq(code)));
            System.out.println("----------------------- OK GOT DATA---------------" + data.get());

        return data.get();
    }

    @Cacheable("coredatafav")
    public List<CoreData> getDataAllStock(String []code , String date){
       List<CoreData> data =  Lists.newArrayList(
                dataRepo.findAll(QCoreData.coreData.code.in(code ).and(QCoreData.coreData.date.eq( LocalDate.parse(date ) ) ) )
        );
        return data;
    }



}
