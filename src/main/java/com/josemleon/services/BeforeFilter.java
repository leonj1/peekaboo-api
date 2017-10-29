package com.josemleon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static spark.Spark.before;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class BeforeFilter implements SparkFilter {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public BeforeFilter() {
    }

    public void init() {
        // Filter before each request
        before((req, res) -> {
            log.info("Before filter");
        });
    }
}
