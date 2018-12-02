package com.dharma.algogenerator.data.repo;

import com.dharma.algogenerator.data.entity.CoreStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface StockRepo extends JpaRepository<CoreStock,Long>,StockCustomRepo, QuerydslPredicateExecutor<CoreStock> {


}
