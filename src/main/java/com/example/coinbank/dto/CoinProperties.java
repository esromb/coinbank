package com.example.coinbank.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class CoinProperties {

    private long quarter;
    private long dime;
    private long nickel;
    private long penny;
    private long fifty;
    private long seventy;
    private long seventyOne;
}
