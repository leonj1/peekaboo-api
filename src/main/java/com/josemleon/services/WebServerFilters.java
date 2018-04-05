package com.josemleon.services;

import static spark.Spark.before;
import static spark.Spark.options;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class WebServerFilters {
    private SparkFilter[] filters;
    private String origin;
    private String methods;
    private String headers;
    private boolean corsInitialized;

    public WebServerFilters(SparkFilter[] filters, final String origin, final String methods, final String headers) {
        this.filters = filters;
        this.origin = origin;
        this.methods = methods;
        this.headers = headers;
        this.corsInitialized = false;
    }

    public void start() {
        enableCORS();
        for(SparkFilter filter : this.filters) {
            filter.init();
        }
    }

    // Enables CORS on requests. This method is an initialization method and should be called once.
    private void enableCORS() {
        if (corsInitialized) {
            return;
        }
        this.corsInitialized = true;

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }
}