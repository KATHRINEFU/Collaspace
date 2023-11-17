package org.mercury.TeamService.config;

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
 * @Date 10/5/23 9:54 AM
 * @Version 1.0
 **/

@Configuration
public class RabbitMQConfig {
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public Queue createReturnEmployeeTeamsQueue() {
        return new Queue("q.return-employee-teams");
    }

    @Bean
    public Queue createGetEmployeeQueue(){
        return new Queue("q.get-employee");
    }

    @Bean
    public Queue createGetAccountQueue(){
        return new Queue("q.get-account");
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
