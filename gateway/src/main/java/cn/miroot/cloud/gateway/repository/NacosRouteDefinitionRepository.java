package cn.miroot.cloud.gateway.repository;

import cn.hutool.json.JSONUtil;
import cn.miroot.cloud.gateway.properties.NacosDynamicRouteProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author fupan
 * nacos动态路由
 */
@Slf4j
@Component
@NoArgsConstructor
@DependsOn(NacosRouteDefinitionRepository.NACOS_DYNAMIC_ROUTE_PROPERTIES_NAME)
public class NacosRouteDefinitionRepository implements RouteDefinitionRepository {
	/**
	 * name of {@link NacosDynamicRouteProperties}
	 */
	public static final String NACOS_DYNAMIC_ROUTE_PROPERTIES_NAME = "nacosDynamicRouteProperties";
	/**
	 * dynamic route properties config {@link NacosDynamicRouteProperties}
	 */
	@Resource
	private NacosDynamicRouteProperties nacosDynamicRouteProperties;

	/**
	 * applicationEventPublisher {@link ApplicationEventPublisher}
	 */
	@Resource
	private ApplicationEventPublisher publisher;
	/**
	 * Nacos config service
	 */
	private ConfigService configService;


	@Override
	public Flux<RouteDefinition> getRouteDefinitions() {
		return Flux.fromIterable(this.loadNacosConfig());
	}

	@Override
	public Mono<Void> save(Mono<RouteDefinition> route) {
		return null;
	}

	@Override
	public Mono<Void> delete(Mono<String> routeId) {
		return null;
	}

	@PostConstruct
	public void init() {
		this.configServiceInitialization();
		this.addListener();
	}

	private void configServiceInitialization() {
		try {
			this.configService = NacosFactory.createConfigService(nacosDynamicRouteProperties.assembleConfigServiceProperties());
		} catch (Exception var4) {
			log.error("get dynamic route data from Nacos error,dataId:{}, ", nacosDynamicRouteProperties.getName(), var4);
		}
	}

	private List<RouteDefinition> loadNacosConfig() {
		try {
			String data = configService.getConfig(nacosDynamicRouteProperties.getName(), nacosDynamicRouteProperties.getGroup(), nacosDynamicRouteProperties.getTimeout());
			return JSONUtil.toList(data, RouteDefinition.class);
		} catch (NacosException var2) {
			log.error("get data from Nacos error,dataId:{}, ", nacosDynamicRouteProperties.getName(), var2);
		}
		return Collections.emptyList();
	}

	private void addListener() {
		try {
			configService.addListener(nacosDynamicRouteProperties.getName(), nacosDynamicRouteProperties.getGroup(), new Listener() {
				@Override
				public void receiveConfigInfo(String configInfo) {
					publisher.publishEvent(new RefreshRoutesEvent(this));
				}
				@Override
				public Executor getExecutor() {
					return null;
				}
			});
		} catch (NacosException var1) {
			log.error("nacos dynamic route add listener error dataId:{}",nacosDynamicRouteProperties.getName(),var1);
		}
	}

}
