package com.dharma.algogenerator.data.repo;

import com.dharma.algogenerator.data.entity.TechTechstr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TechStrRepo extends JpaRepository<TechTechstr,Long> {

}
