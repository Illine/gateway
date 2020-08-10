package com.illine.weather.gateway.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@Slf4j(topic = "GATEWAY-CONFIG")
public class RibbonConfig {

    @Bean
    public ServerList<Server> ribbonServerList(IClientConfig config, DiscoveryClient discoveryClient) {
        LOGGER.info("Configuring a ribbon server list...");
        var servers =
                discoveryClient.getInstances(config.getClientName())
                        .stream()
                        .map(it -> new Server(it.getHost(), it.getPort()))
                        .toArray(Server[]::new);
        LOGGER.info("Servers were configured: {}", Arrays.toString(servers));
        return new StaticServerList<>(servers);
    }

}