
package tech.ynfy.frame.config.feign;

import feign.Logger.Level;
import feign.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class FeignConfiguration {
    private static final Logger log = LogManager.getLogger(FeignConfiguration.class);

    public FeignConfiguration() {
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5L, TimeUnit.MINUTES, 5L, TimeUnit.MINUTES, true);
    }

    @Bean
    Level feignLoggerLevel() {
        return Level.BASIC;
    }

    @Bean
    public feign.Logger logger() {
        return new FeignLogger();
    }
}
