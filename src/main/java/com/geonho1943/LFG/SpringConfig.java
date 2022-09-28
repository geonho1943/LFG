package com.geonho1943.LFG;

import com.geonho1943.LFG.model.DocModel;
import com.geonho1943.LFG.model.DocRepository;
import com.geonho1943.LFG.model.UserModel;
import com.geonho1943.LFG.model.UserRepository;
import com.geonho1943.LFG.service.UserService;
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

}
