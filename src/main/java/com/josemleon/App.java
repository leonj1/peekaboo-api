package com.josemleon;

import com.google.gson.Gson;
import com.josemleon.configs.AppProperties;
import com.josemleon.controllers.Controller;
import com.josemleon.controllers.RestEndpoints;
import com.josemleon.controllers.SecretsController;
import com.josemleon.controllers.routes.AddSecretRoute;
import com.josemleon.controllers.routes.FetchQueueStatsRoute;
import com.josemleon.controllers.routes.GetSecretWithPasswordRoute;
import com.josemleon.controllers.routes.HealthCheckRoute;
import com.josemleon.exceptions.PropertiesFileNotFoundException;
import com.josemleon.models.Secret;
import com.josemleon.services.BCryptEncryptPassword;
import com.josemleon.services.BeforeFilter;
import com.josemleon.services.EncryptPassword;
import com.josemleon.services.RandomStringSaltGenerator;
import com.josemleon.services.SaltGenerator;
import com.josemleon.services.SecretService;
import com.josemleon.services.SparkFilter;
import com.josemleon.services.WebServerFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String APPLICATION_PROPERTIES = "application.properties";

    public static void main( String[] args ) throws PropertiesFileNotFoundException, IOException {
        log.info("Some succeed because they are destined to. Most succeed because they are determined to. -- Unknown");

        // Starting here, we are going the job Spring would normally do
        AppProperties appProperties = null;
        Parser cmdlineParser = new CommandlineParser(args);
        try {
            appProperties = new AppProperties(
                    new GetEffectiveProperty(
                            new GetProperty(
                                    APPLICATION_PROPERTIES,
                                    cmdlineParser
                            ),
                            cmdlineParser
                    )
            );
        } catch (Exception e) {
            log.error(String.format("Really bad problem trying to find resource %s", APPLICATION_PROPERTIES));
            System.exit(1);
        }

        // Our REST endpoint
        Spark.port(appProperties.getHttpServerPort());

        // Initialize Web server filters
        WebServerFilters webServerFilters = new WebServerFilters(
                new SparkFilter[]{
                        new BeforeFilter()
                },
                "*",
                "PUT GET POST DELETE",
                ""
        );
        webServerFilters.start();

        EncryptPassword passwordEncryption = new BCryptEncryptPassword();
        SaltGenerator randomStringSaltGenerator = new RandomStringSaltGenerator(appProperties.getSaltPasswordRounds());
        SecretService secretService = new SecretService(
                new ConcurrentHashMap<UUID, Secret>(),
                passwordEncryption
        );

        RestEndpoints restEndpoints = new RestEndpoints(
                new Controller[]{
                        new SecretsController(
                                new HealthCheckRoute(),
                                new AddSecretRoute(
                                        secretService,
                                        passwordEncryption,
                                        randomStringSaltGenerator,
                                        appProperties.getSecretDefaultExpiryMinutes(),
                                        new Gson()
                                ),
                                new GetSecretWithPasswordRoute(secretService),
                                new FetchQueueStatsRoute(secretService)
                        )
                }
        );
        restEndpoints.start();
    }
}
