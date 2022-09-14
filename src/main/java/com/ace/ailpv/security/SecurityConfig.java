package com.ace.ailpv.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@SuppressWarnings({ "deprecated" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    RemembermeSuccessHandler remembermeSuccessHandler;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
<<<<<<< HEAD
                // .antMatchers("/assets/**", "/auth/**", "/login", "/logout", "/admin/register", "/api/**","/user/**").permitAll()
=======
                // .antMatchers("/assets/**", "/auth/**", "/login", "/logout",
                // "/admin/register", "/api/**","/user/**").permitAll()
>>>>>>> 4ce40cb6e52545cbb47a6068ed90ab6e6ee5631d
                // .antMatchers("/admin/**").hasRole("ADMIN")
                // .antMatchers("/teacher/**").hasRole("TEACHER")
                // .antMatchers("/student/**").hasAnyRole("TEACHER", "STUDENT","ADMIN")
                // .antMatchers("/admin/**").permitAll()
<<<<<<< HEAD
                // .antMatchers("/student/**").permitAll()
                // .anyRequest()
                // .authenticated()
                .antMatchers("/**").permitAll()
                
=======
                // .anyRequest()
                // .authenticated()
                .antMatchers("/**").permitAll()

>>>>>>> 4ce40cb6e52545cbb47a6068ed90ab6e6ee5631d
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .rememberMe()
                .authenticationSuccessHandler(remembermeSuccessHandler)
                .tokenValiditySeconds(2592000)
                .and()
                .exceptionHandling().accessDeniedPage("/user/403");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
