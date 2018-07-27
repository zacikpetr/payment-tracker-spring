package com.zacikp.example.paymenttrackerspring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PaymentRouter {

    @Bean
    RouterFunction<?> routes(PaymentHandler paymentHandler) {
        return route(GET("/payment/stream"), paymentHandler::jsonStream)
                .andRoute(POST("/payment"), paymentHandler::create);
    }
}
