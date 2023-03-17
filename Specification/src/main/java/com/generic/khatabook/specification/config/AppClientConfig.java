package com.generic.khatabook.specification.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppClientConfig {

/*
    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:6600").build();
    }

    public SpecificationClientExchanger specificationClientExchanger() {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient())).build();
        return factory.createClient(SpecificationClientExchanger.class);
    }
*/

}
