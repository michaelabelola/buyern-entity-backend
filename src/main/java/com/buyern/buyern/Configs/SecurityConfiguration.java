package com.buyern.buyern.Configs;

import com.buyern.buyern.Controllers.UserAuthController;
import com.buyern.buyern.Services.CustomUserDetailsService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@Configuration
@EnableWebSecurity
@Data
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
    final CustomAuthenticationProvider customAuthenticationProvider;
    final CustomUserDetailsService userDetailsService;
    final CustomCorsConfigurationSource corsConfigurationSource;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(customAuthenticationProvider);
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .configurationSource(corsConfigurationSource)
                .and()
                .csrf().disable()
                .sessionManagement().sessionFixation().newSession().maximumSessions(1).maxSessionsPreventsLogin(false).and()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/helper/**", "/user/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .failureHandler((request, response, exception) -> {
                    response.sendRedirect("/user/auth/signIn/fail");
                })
                .successHandler((request, response, authentication) -> {
//                    logger.error("*******************************");
//                    if (request.getHeaders("Set-Cookie").hasMoreElements())
//                    logger.error(request.getHeaders("Set-Cookie").nextElement());
//
//                    for (Cookie cookie : request.getCookies()) {
//                        if (cookie.getName() == "SESSION"){
//                            cookie.setMaxAge(3000);
//                        }
//                        logger.error(cookie.getName());
//                        logger.error(cookie.getComment());
//                        logger.error(cookie.getDomain());
//                        logger.error(cookie.getPath());
//                        logger.error(cookie.getValue());
//                        logger.error(String.valueOf(cookie.getMaxAge()));
//                        logger.error(String.valueOf(cookie.getSecure()));
//                    }request.getCookies().
//                    logger.error("*******************************");
//                    Cookie c = new Cookie("custom cookie", "omoweyrey");
//                    c.setComment("test");
//                    c.setMaxAge(3600);
//                    response.addCookie(c);
                    response.sendRedirect("/user/auth/signIn/success");
                })
                .loginProcessingUrl("/user/auth/signIn")
//                .defaultSuccessUrl("/signIn/success")
//                .failureUrl("/signIn/fail")
                .and()
                .logout()
                .logoutUrl("/user/auth/signOut")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/user/auth/signOut/success");
                })
                .logoutSuccessUrl("/user/auth/signOut/success")
                .deleteCookies("JSESSION", "SESSION")
                .and().httpBasic();
//      .antMatchers("/admin").access("hasRole('admin') and hasIpAddress('192.168.1.0/24') and @myCustomBean.checkAccess(authentication,request)")
    }

}