package com.bookdemo.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    static Logger logger = LogManager.getLogger(Log.class.getName());

    public static void startTestCase(String sTestCaseName){
        logger.info("==========================="+sTestCaseName+" TEST START=================================");
    }

    public static void endTestCase(String sTestCaseName){
        logger.info("============================"+sTestCaseName+" TEST END==================================");
    }

    public static void info(String message) {

        logger.info(message);

    }

    public static void warn(String message) {

        logger.warn(message);

    }

    public static void error(String message) {

        logger.error(message);

    }

    public static void fatal(String message) {

        logger.fatal(message);

    }

    public static void debug(String message) {

        logger.debug(message);

    }


//
//    public static void main(String[] args) {
//        System.out.println("Hello");
//        logger.info("Information");
//        logger.error("Error");
//        logger.warn("Warnng");
//        logger.fatal("Fatal");
//    }
}
