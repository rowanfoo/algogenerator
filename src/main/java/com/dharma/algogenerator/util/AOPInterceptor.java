package com.dharma.algogenerator.util;

import com.dharma.algogenerator.data.entity.ErrorLog;
import com.dharma.algogenerator.data.repo.ErrorLogRepo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

@Aspect
@Component
public class AOPInterceptor {

    @Autowired
    ArrayList<LocalDate> holidays;

    @Autowired
    ErrorLogRepo errorLogRepo;


    /*
    If days is in holiday then dont run.
     */
    @Around("execution(void com.dharma.algogenerator.controller.admin.AsxMetaStockImport.importAllData())")
    public void employeeAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {

        System.out.println("Before invoking getName() method");


        Object value = null;
        try {
            System.out.println("CALL 1");
            if (!holidays.contains(LocalDate.now())) {
                System.out.println(" RUN ");
                proceedingJoinPoint.proceed();

            }

            //
            System.out.println("CALL 2");

        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @AfterThrowing(pointcut = "(execution(* com.dharma.algogenerator.controller.admin.*.*(..)))", throwing = "e")
    public void logControllerError(JoinPoint joinPoint, Exception e) {
        System.out.println("ERRRRRRRRRRRRRR-1------------" + " -> " + joinPoint.toString());
        System.out.println("ERRRRRRRRRRRRRR---2----------" + " -> " + e);
        errorLogRepo.save(
                ErrorLog.builder()
                        .date(LocalDate.now())
                        .message1(joinPoint.toString())
                        .message2(e.toString())
                        .build()
        );

    }


    @AfterThrowing(pointcut = "(execution(* com.dharma.algogenerator.service.Algo.*.*(..)))", throwing = "e")
    public void logSchedulerError(JoinPoint joinPoint, Exception e) {
        System.out.println("ERRRRRRRRRRRRRR-1------------" + " -> " + joinPoint.toString());
        System.out.println("ERRRRRRRRRRRRRR---2----------" + " -> " + e);
//        ErrorLog
        errorLogRepo.save(
                ErrorLog.builder()
                        .date(LocalDate.now())
                        .message1(joinPoint.toString())
                        .message2(e.toString())
                        .build()
        );

    }
}
