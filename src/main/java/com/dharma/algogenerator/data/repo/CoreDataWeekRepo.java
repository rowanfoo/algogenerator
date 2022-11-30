package com.dharma.algogenerator.data.repo;

import com.dharma.algogenerator.data.entity.CoreDataMonth;
import com.dharma.algogenerator.data.entity.CoreDataWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoreDataWeekRepo extends JpaRepository<CoreDataWeek, String>, QuerydslPredicateExecutor<CoreDataWeek>{
    List<CoreDataWeek> findTop2ByCodeOrderByDateDesc(String code);
}
