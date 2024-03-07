package com.example.saccamelkafkabugdemo;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

@SpringBootTest
@AutoConfigureMessageVerifier
public class ContractBase {
    public void sendMessage() {
        try (CamelContext context = new DefaultCamelContext()) {

            // Add a route to send a message to Kafka
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:sendMessage")
                            .to("kafka:myKafkaTopic?brokers=localhost:9092");
                }
            });

            // Start CamelContext
            context.start();

            // Create a producer template
            try {
                // Send a message to Kafka
                context.createProducerTemplate().sendBody("direct:sendMessage", "{\"bookName\": \"foo\"}");

                // Wait for a while to allow the message to be sent
                Thread.sleep(2000);
            } finally {
                // Stop CamelContext
                context.stop();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
