package com.fundbridge.investor.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String>config=new HashMap<>();
        config.put("cloud_name","degw6indi");
        config.put("api_key","377885351879736");
        config.put("api_secret","Z80gBiX1DNOG5csC2Kjb5GKXINk");
        return new Cloudinary(config);
    }
}
