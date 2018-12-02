package com.dharma.algogenerator.data.repo;


import com.dharma.algogenerator.data.entity.CoreStock;

import java.util.List;

public interface StockCustomRepo {

     List<CoreStock> getMyId(String name);
     List<CoreStock> geStockName(String name) ;
}
