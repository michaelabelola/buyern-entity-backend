package com.buyern.buyern.Configs;

import com.buyern.buyern.Services.CustomUserDetailsService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Configuration
@EnableWebSecurity
@Data
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    final CustomAuthenticationProvider customAuthenticationProvider;
    final CustomUserDetailsService userDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder =
//                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication().passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(customAuthenticationProvider);
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .sessionManagement().sessionFixation().changeSessionId().maximumSessions(1).maxSessionsPreventsLogin(true).and()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/helper/**", "/signIn", "/signIn/**", "/signOut", "/signOut/**", "/signup", "/forgotPassword", "/resetPassword").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .failureHandler((request, response, exception) -> {
                    response.sendRedirect("/signIn/fail");
                })
                .successHandler((request, response, authentication) -> {
                    response.sendRedirect("/signIn/success");
                })
                .loginProcessingUrl("/signIn")
//                .defaultSuccessUrl("/signIn/success")
//                .failureUrl("/signIn/fail")
                .and()
                .logout()
                .logoutUrl("/signOut")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/signOut/success");
                })
                .logoutSuccessUrl("/signOut/success")
                .deleteCookies("JSESSION", "SESSION")

                .and().httpBasic();
//      .antMatchers("/admin").access("hasRole('admin') and hasIpAddress('192.168.1.0/24') and @myCustomBean.checkAccess(authentication,request)")
    }

}