package com.dharma.algogenerator.data.repo;

import com.dharma.algogenerator.data.entity.AllStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface AllStockRepo extends JpaRepository<AllStock, Long>, QuerydslPredicateExecutor<AllStock> {

}
