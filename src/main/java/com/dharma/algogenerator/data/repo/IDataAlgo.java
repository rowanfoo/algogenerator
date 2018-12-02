package com.dharma.algogenerator.data.repo;



import com.dharma.algogenerator.dto.MovingAverage;

import java.util.List;


public interface IDataAlgo {


    List<Object[]>  breakRoundNumber(String code);
    List<Object[]>  downGreater40Percent(String code);
    List<Object[]>  consequitveDayFallStr(String date);
    List<Object[]>  fiftyDayDistance();

    List<Object[]>  movingAveragebetween(MovingAverage movingAverage, double start, double end);


}
