package fr.polytech.unice.blablamove.teamc.blablamovebackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Class AppConfig
 *
 * @author Joël CANCELA VAZ
 */
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}
