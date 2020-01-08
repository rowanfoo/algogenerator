package com.dharma.algogenerator.data.repo;

import com.dharma.algogenerator.data.entity.Fundamental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FundamentalRepo extends JpaRepository<Fundamental, String> {

}
