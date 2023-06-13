package com.song.pzforestserver;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springdoc.core.utils.Constants.SPRINGDOC_ENABLED;

@ConditionalOnProperty(name = SPRINGDOC_ENABLED, matchIfMissing = true)
@SpringBootApplication
@EnableScheduling
public class PzforestServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PzforestServerApplication.class, args);
        System.out.println("启动成功：Sa-Token配置如下：" + SaManager.getConfig());
    }

}
