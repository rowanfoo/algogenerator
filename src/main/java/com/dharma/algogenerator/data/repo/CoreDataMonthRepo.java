package com.dharma.algogenerator.data.repo;

import com.dharma.algogenerator.data.entity.CoreData;
import com.dharma.algogenerator.data.entity.CoreDataMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CoreDataMonthRepo extends JpaRepository<CoreDataMonth, String>, QuerydslPredicateExecutor<CoreDataMonth> {

    List<CoreDataMonth> findTop2ByCodeOrderByDateDesc(String code);

}

