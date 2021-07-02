package com.hcf.nszh.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.hcf.nszh.gateway.enums.MethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

/**
 * @Author maruko
 * @Date 2019/6/19
 **/
@Component
@Slf4j
@Configuration
public class DemoFilter implements GlobalFilter, Ordered {

    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Value("${referer.domain:*%&!80djlbv@}")
    private String[] refererDomains;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("------------开始进入请求地址拦截----------");

        ServerHttpRequest req = exchange.getRequest();
        ServerHttpResponse resp = exchange.getResponse();
        String referer = req.getHeaders().getFirst("referer");
        String origin = req.getHeaders().getFirst("origin");
        log.info("------------从request中获取到的referer：" + referer + "--------origin: " + origin);
        // 只验证POST请求
        String method = req.getMethod().toString();
        if (MethodEnum.POST.equals(method) || MethodEnum.GET.equals(method)) {

            java.net.URL refererUrl;
            java.net.URL originUrl;
            String refererHost = null;
            String originHost = null;
            try {
                if (referer != null) {
                    refererUrl = new java.net.URL(referer);
                    refererHost = refererUrl.getHost();
                }
                if (origin != null) {
                    originUrl = new java.net.URL(origin);
                    originHost = originUrl.getHost();
                }
            } catch (MalformedURLException e) {
            }

            log.info("------------开始进入请求地址拦截-------host: " + refererHost + "------refererDomains: " + refererDomains.toString());

            if (refererHost != null) {
                if (refererDomains != null) {
                    for (String s : refererDomains) {
                        boolean b = s.equals(refererHost) && s.equals(originHost);
                        boolean b1 = s.equals(refererHost) && originHost == null;
                        if (b || b1 || "*%&!80djlbv@".equals(s)) {
                            log.info("---------------------能比配----------------------------");
                            return chain.filter(exchange).then(
                                    Mono.fromRunnable(() -> {
                                        Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                                        if (startTime != null) {
                                            log.info(exchange.getRequest().getURI().getRawPath() +
                                                    ": " + (System.currentTimeMillis() - startTime) + "ms");
                                        }
                                    })
                            );
                        }
                    }
                }
            }
        }

        JSONObject message = new JSONObject();
        message.put("code", -11);
        message.put("data", "鉴权失败");
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = resp.bufferFactory().wrap(bits);
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        resp.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return resp.writeWith(Mono.just(buffer));

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }


}
