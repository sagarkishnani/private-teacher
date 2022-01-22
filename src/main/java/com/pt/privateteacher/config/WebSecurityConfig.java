package com.pt.privateteacher.config;

import com.pt.privateteacher.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        http
                //habilitacion del inicio de sesion
                .formLogin()
                .loginPage("/login")
                .permitAll()

                .and()

                //Definir los permisos a rutas especificas
                .authorizeRequests()
                .antMatchers()
                .authenticated()

                .anyRequest()
                .permitAll()

                .and()

                .rememberMe().key("rememberMeKey").tokenValiditySeconds(3600)
                .userDetailsService(userDetailsServiceImpl)

                .and()

                .exceptionHandling().accessDeniedPage("/403")

                .and()

                .logout(logout->logout.logoutRequestMatcher(
                        new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl("/"));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
