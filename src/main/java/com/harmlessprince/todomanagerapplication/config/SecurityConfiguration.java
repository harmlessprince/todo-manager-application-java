package com.harmlessprince.todomanagerapplication.config;

import com.harmlessprince.todomanagerapplication.exceptions.HandleAccessDeniedException;
import com.harmlessprince.todomanagerapplication.exceptions.HandleAuthenticationException;
import com.harmlessprince.todomanagerapplication.filters.JwtAuthenticationFilter;
import com.harmlessprince.todomanagerapplication.interfaces.DateValidatorInterface;
import com.harmlessprince.todomanagerapplication.utils.DateTimeValidator;
import jakarta.servlet.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EnableMethodSecurity(
        securedEnabled = true
)
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    //    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private HandleAuthenticationException handleAuthenticationException;

//    @Autowired
    private HandleAccessDeniedException handleAccessDeniedException;

    @Autowired
    private CustomUserDetailService userDetailService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        logger.info("Hello from security {}",authenticationProvider.toString());
        httpSecurity.authorizeHttpRequests(authorizeHttpRequest -> {
                    authorizeHttpRequest.requestMatchers("/auth/**").permitAll();
                    authorizeHttpRequest.requestMatchers("/error").permitAll();
//                    authorizeHttpRequest.requestMatchers("/users/**");
//                    authorizeHttpRequest.requestMatchers(HttpMethod.DELETE,"/users/**").hasAuthority("super_admin");
                    authorizeHttpRequest.anyRequest().authenticated();
                })
                .exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedHandler(accessDeniedHandler()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(handleAuthenticationException))

//                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public UserDetailsService userDetailServiceBean() {
        return username -> userDetailService.loadUserByUsername(username);
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DateValidatorInterface dateTimeValidator() {
        return new DateTimeValidator("yyyy-MM-dd HH:mm:ss");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new HandleAccessDeniedException();
    }


//    @Bean
//    public FormattingConversionService conversionService() {
//
//        // Use the DefaultFormattingConversionService but do not register defaults
//        DefaultFormattingConversionService conversionService =
//                new DefaultFormattingConversionService(false);
//
//        // Ensure @NumberFormat is still supported
//        conversionService.addFormatterForFieldAnnotation(
//                new NumberFormatAnnotationFormatterFactory());
//
//        // Register JSR-310 date conversion with a specific global format
//        DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
//        dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        dateTimeRegistrar.registerFormatters(conversionService);
//
//        // Register date conversion with a specific global format
//        DateFormatterRegistrar dateRegistrar = new DateFormatterRegistrar();
//        dateRegistrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
//        dateRegistrar.registerFormatters(conversionService);
//
//        return conversionService;
//    }
}
