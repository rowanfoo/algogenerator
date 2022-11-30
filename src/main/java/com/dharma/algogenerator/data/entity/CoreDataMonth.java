
package com.dharma.algogenerator.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Getter
@Setter

public class CoreDataMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String code;
    public LocalDate date;
    public Double open;
    public Double high;
    public Double low;
    public Long volume;
    public Double close;
    private Double changes;
    private Double changepercent;

    @Override
    public String toString() {
        return "CoreDataMonth{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                '}';
    }

    public String getVolAsString() {
        if (volume > 1000000000) {
            return (volume / 1000000000) + "B";
        } else {
            return (volume / 1000000) + "M";
        }

    }
    @JsonSetter("date")
    public void setDateJSON(String date) {
        // System.out.println("------------setDateJSON : " + date);
        this.date = LocalDate.parse(date);
//        if (code.indexOf(".") > 0) {
//            this.code = code.substring(0, code.indexOf(".")) + ".AX";
//        } else {
//            this.code = code + ".AX";
//        }


    }

}
