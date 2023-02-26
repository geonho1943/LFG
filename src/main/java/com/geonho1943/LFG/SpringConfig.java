package com.geonho1943.LFG;

import com.geonho1943.LFG.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    @Autowired
    DataSource dataSource;

    @Bean
    public UserRepository userRepository(){
        return new UserModel(dataSource);
    }
    @Bean
    public DocRepository docRepository(){
        return new DocModel(dataSource);
    }

    @Bean
    public AppRepository appRepository(){
        return new AppModel(dataSource);
    }

}
