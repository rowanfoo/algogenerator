package com.dharma.algogenerator.data.pojo;

import lombok.Data;

@Data
public class AsxdailydataPojo {
private String code ;
    private String last_price ;
    private String open_price ;
    private String day_high_price ;
    private String day_low_price ;
    private String change_price ;
    private String change_in_percent ;
    private String volume ;
    private String bid_price ;

    private String offer_price ;
    private String previous_close_price ;
    private String previous_day_percentage_change ;


    private String year_high_price ;
    private String last_trade_date ;
    private String year_high_date ;
    private String year_low_price ;
    private String year_low_date ;
    private String year_open_price ;


    private String year_open_date ;
    private String year_change_price ;
    private String year_change_in_percentage ;
    private Double pe ;
    private Double eps ;
    private Long average_daily_volume ;
    private String annual_dividend_yield ;
    private Long market_cap ;
    private Long number_of_shares ;


     public Double getYear_change_in_percentage(){
        return clean(year_change_in_percentage);
    }

    public Double getAnnual_dividend_yield(){
        return clean(annual_dividend_yield);
    }



    private Double  clean(String no){
         if(no==null)return 0.0;
         String clean = no.replaceAll("%","").replaceAll(",","");
         return Double.parseDouble(clean);


    }








}
