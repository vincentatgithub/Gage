package cn.miroot.cloud.gateway.properties;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.spring.util.PropertySourcesUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.alibaba.nacos.api.PropertyKeyConst.*;

/**
 * @author fupan
 */
@Getter
@Setter
@Component
@ConfigurationProperties(NacosDynamicRouteProperties.PREFIX)
@DependsOn(NacosDynamicRouteProperties.NACOS_CONFIG_PROPERTIES_NAME)
public class NacosDynamicRouteProperties {
	/**
	 * nacos内置配置文件
	 */
	@Resource
	@JsonIgnore
	private NacosConfigProperties nacosConfigProperties;


	/**
	 * 环境变量
	 */
	@Resource
	@JsonIgnore
	private Environment environment;

	private static final Pattern PATTERN = Pattern.compile("-(\\w)");
	/**
	 * Prefix of {@link NacosDynamicRouteProperties}.
	 */
	public static final String PREFIX = "spring.cloud.gateway.dynamic.nacos";
	/**
	 * Name of {@link NacosConfigProperties}
	 */
	public static final String NACOS_CONFIG_PROPERTIES_NAME = "nacosConfigProperties";

	/**
	 * nacos config server address.
	 */
	private String serverAddr;

	/**
	 * the nacos authentication username.
	 */
	private String username;

	/**
	 * the nacos authentication password.
	 */
	private String password;

	/**
	 * encode for nacos config content.
	 */
	private String encode;

	/**
	 * nacos config group, group is config data meta info.
	 */
	private String group = "DEFAULT_GROUP";

	/**
	 * nacos config dataId prefix.
	 */
	private String prefix;

	/**
	 * timeout for get config from nacos.
	 */
	private int timeout = 3000;

	/**
	 * endpoint for Nacos, the domain name of a service, through which the server address
	 * can be dynamically obtained.
	 */
	private String endpoint;

	/**
	 * namespace, separation configuration of different environments.
	 */
	private String namespace;

	/**
	 * context path for nacos config server.
	 */
	private String contextPath;

	/**
	 * nacos config cluster name.
	 */
	private String clusterName;

	/**
	 * access key for namespace.
	 */
	private String accessKey;

	/**
	 * secret key for namespace.
	 */
	private String secretKey;

	/**
	 * nacos config dataId name.
	 */
	private String name = "dynamicRoute";

	/**
	 * share nacos config setting
	 */
	@PostConstruct
	public void init() {
		Dict var1 = Dict.parse(this);
		Dict.parse(nacosConfigProperties)
				.forEach((k, v) -> var1.put(k, var1.get(k, v)));
		BeanUtil.copyProperties(var1, this);
	}


	/**
	 * assemble properties for configService. (cause by rename : Remove the interference
	 * of auto prompts when writing,because autocue is based on get method.
	 *
	 * @return properties
	 */
	public Properties assembleConfigServiceProperties() {
		Properties properties = new Properties();
		properties.put(SERVER_ADDR, Objects.toString(this.serverAddr, ""));
		properties.put(USERNAME, Objects.toString(this.username, ""));
		properties.put(PASSWORD, Objects.toString(this.password, ""));
		properties.put(ENCODE, Objects.toString(this.encode, ""));
		properties.put(NAMESPACE, Objects.toString(this.namespace, ""));
		properties.put(ACCESS_KEY, Objects.toString(this.accessKey, ""));
		properties.put(SECRET_KEY, Objects.toString(this.secretKey, ""));
		properties.put(CLUSTER_NAME, Objects.toString(this.clusterName, ""));
		String endpoint = Objects.toString(this.endpoint, "");
		if (endpoint.contains(":")) {
			int index = endpoint.indexOf(":");
			properties.put(ENDPOINT, endpoint.substring(0, index));
			properties.put(ENDPOINT_PORT, endpoint.substring(index + 1));
		} else {
			properties.put(ENDPOINT, endpoint);
		}

		enrichNacosConfigProperties(properties);
		return properties;
	}

	private void enrichNacosConfigProperties(Properties nacosConfigProperties) {
		Map<String, Object> properties = PropertySourcesUtils
				.getSubProperties((ConfigurableEnvironment) environment, PREFIX);
		properties.forEach((k, v) -> nacosConfigProperties.putIfAbsent(resolveKey(k),
				String.valueOf(v)));
	}

	private String resolveKey(String key) {
		Matcher matcher = PATTERN.matcher(key);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1)
					.toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
