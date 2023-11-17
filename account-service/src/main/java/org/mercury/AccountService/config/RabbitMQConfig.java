package org.mercury.AccountService.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitMQConfig
 * @Description TODO
 * @Author katefu
 * @Date 10/4/23 11:45 PM
 * @Version 1.0
 **/

@Configuration
public class RabbitMQConfig {
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public Queue createReturnAccountQueue() {
        return new Queue("q.return-account");
    }

    @Bean
    public Queue createGetEmployeeTeamsQueue() {
        return new Queue("q.get-account-company");
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter){
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setMessageConverter(converter);
        return template;
    }


}
