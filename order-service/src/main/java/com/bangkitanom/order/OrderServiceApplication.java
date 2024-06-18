package com.bangkitanom.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.bangkitanom.order.webclient.CustomerClient;
import com.bangkitanom.order.webclient.ProductClient;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
public class OrderServiceApplication {

	private final LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction;
	private final WebClient.Builder webClientBuilderCustomer;
	private final WebClient.Builder webClientBuilderProduct;

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	// @Bean
	// @LoadBalanced
	// RestTemplate restTemplate() {
	// 	return new RestTemplate();
	// }

	@Bean
	WebClient webClientCustomer() {
		return webClientBuilderCustomer
		.baseUrl("http://customer-service")
		.filter(loadBalancedExchangeFilterFunction)
		.build();
	}

	@Bean
	CustomerClient customerClient() {
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
		.builder(WebClientAdapter.forClient(webClientCustomer())).build();
		return factory.createClient(CustomerClient.class);
	}

	@Bean
	WebClient webClientProduct() {
		return webClientBuilderProduct
		.baseUrl("http://product-service")
		.filter(loadBalancedExchangeFilterFunction)
		.build();
	}

	@Bean
	ProductClient productClient() {
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
		.builder(WebClientAdapter.forClient(webClientProduct())).build();
		return factory.createClient(ProductClient.class);
	}

}
