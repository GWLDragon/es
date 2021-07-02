package com.hcf.nszh.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

/**
 * 过滤器
 * 
 * @author hx
 */
@Slf4j
@Component
@Configuration
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config> {

	@Value("${referer.refererDomain}")
	private static String[] refererDomains;

	public CustomGatewayFilterFactory() {
		super(Config.class);
		log.info("加载 自定义拦截器 [Custom]...");
	}

	@Override
	public Config newConfig() {
		return new Config();
	}

	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("enabled");
	}

	/**
	 * @param config
	 * @return
	 */
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {

			log.info("---------------------开始进入请求地址拦截----------------------------");
			ServerHttpRequest req = exchange.getRequest();
			ServerHttpResponse resp = exchange.getResponse();
			String referer = req.getHeaders().getFirst("referer");
			// 只验证POST请求
			String method = req.getMethod().toString();
			if ("POST".equals(method) || "GET".equals(method)) {
				if (referer == null) {
					// 状态置为404
					resp.setStatusCode(HttpStatus.NOT_FOUND);
					return resp.setComplete();
				}
				java.net.URL url = null;
				try {
					url = new java.net.URL(referer);
				} catch (MalformedURLException e) {
					// URL解析异常，也置为404
					resp.setStatusCode(HttpStatus.NOT_FOUND);
					return resp.setComplete();
				}

				String host = url.getHost();
				if (refererDomains != null) {
					for (String s : refererDomains) {
						if (host.indexOf(s) != -1) {
							return chain.filter(exchange);
						}
					}
				}
			}

			resp.setStatusCode(HttpStatus.UNAUTHORIZED);
			return resp.setComplete();
		};
	}

	public static class Config {
		/**
		 * 控制是否开启拦截器
		 */
		private boolean enabled;

		public Config() {
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	}
}
