package teosprint.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import teosprint.todo.domain.user.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final JwtAuthenticationFilter jwtAuthenticateFilter) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        return http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("**").permitAll()
//                .antMatchers("/v1/user/sign-up", "/v1/user/sign-in", "/v1/user/check/**").permitAll()
//                .antMatchers("/swagger-ui/**", "/v3/**", "/swagger-ui/", "/swagger-ui.html", "/v3/api-docs/", "/webjars/", "/swagger-resources/**").permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // 추가
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
