package com.zacikp.example.paymenttrackerspring;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Map;

@Component
public class PaymentHandler {

    private Payments payments;

    public PaymentHandler(Payments payments) {
        this.payments = payments;
        payments.add("USD", 3000);
    }


    Mono<ServerResponse> jsonStream(ServerRequest serverRequest) {
        Flux<Map> payments = Flux.generate(synchronousSink -> synchronousSink.next(this.payments.getPayments()));
        Flux<Map> result = Flux.zip(Flux.interval(Duration.ofSeconds(1)), payments).map(Tuple2::getT2).share();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(result, Map.class);
    }

    Mono<ServerResponse> create(ServerRequest serverRequest) {
        Mono<Void> then = serverRequest.bodyToMono(Map.class).map(in -> {
            Map<String, Integer> m = in;
            m.forEach((k, v) -> payments.add(k, v));
            return m;
        }).then();
        return ServerResponse.ok().body(then, Void.class);

    }
}
