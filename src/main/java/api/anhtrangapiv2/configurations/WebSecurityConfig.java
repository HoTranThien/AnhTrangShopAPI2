package api.anhtrangapiv2.configurations;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import api.anhtrangapiv2.components.PreFilter;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final PreFilter preFilter;

    @Value("${api.prefix}")
    private String prefix;
    private String[] whiteList;
    private String[] onlyForAdminList;

    @PostConstruct
    public void init() {
    // Initialize after @Value injection
    this.whiteList = new String[]{
        String.format("%s/color/**", this.prefix),
        String.format("%s/size/**", this.prefix),
        String.format("%s/product/**", this.prefix),
        String.format("%s/delivery/**", this.prefix),
        String.format("%s/collection/**", this.prefix),
        String.format("%s/parentcategory/**", this.prefix),
        String.format("%s/childrencategory/**", this.prefix)
    };
    this.onlyForAdminList = new String[]{
        String.format("%s/color/**", this.prefix),
        String.format("%s/size/**", this.prefix),
        String.format("%s/product/**", this.prefix),
        String.format("%s/delivery/**", this.prefix),
        String.format("%s/collection/**", this.prefix),
        String.format("%s/parentcategory/**", this.prefix),
        String.format("%s/childrencategory/**", this.prefix),
        String.format("%s/order/**", this.prefix)
    };
}

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception{
        http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.GET,whiteList).permitAll()
                .requestMatchers(String.format("%s/user/**", this.prefix)).permitAll()
                .requestMatchers(onlyForAdminList).hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET,
                    String.format("%s/order/**", this.prefix)).hasAnyRole("USER")
                .anyRequest().authenticated()
                )
            .exceptionHandling((exceptionHandling) -> exceptionHandling
                .accessDeniedPage("/errors/access-denied"))
            //.exceptionHandling((exceptionHandling) -> new AccessDeniedException("access-denied"))
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        
        return http.build();
    }

 
}
