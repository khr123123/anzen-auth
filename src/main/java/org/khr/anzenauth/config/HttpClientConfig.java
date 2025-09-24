package org.khr.anzenauth.config;

import org.khr.anzenauth.api.addressApi.AddressHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }

    @Bean
    public AddressHttpClient addressHttpClient(RestClient restClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builder()
            .exchangeAdapter(RestClientAdapter.create(restClient))
            .build();
        return factory.createClient(AddressHttpClient.class);
    }
}
