package com.monco;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * @Auther: monco
 * @Date: 2019/3/24 16:20
 * @Description:
 */
@Slf4j
@SpringBootApplication
public class HomeApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HomeApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("\n----------------------------------------------------------\n\tEnvironment '{}' is running! Access URLs:\n\tLocal: \t\thttp://localhost:{}\n\t\n----------------------------------------------------------", env.getProperty("spring.application.name"), env.getProperty("server.port"));
    }
}
