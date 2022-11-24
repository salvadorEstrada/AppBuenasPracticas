package com.app.jwt.configuracion;

import com.app.jwt.security.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //deshabilitarlo ya spring lo implementa
                .authorizeRequests().antMatchers(HttpMethod.GET,"/api/**")
                .permitAll()//metodo get se permite a todos
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest()//cualquier otra petición
                .authenticated() //debe de ser authenticada
                .and() //
                .httpBasic(); //va utilixzar una autenticación básica

    }

    /*   @Override
    //ESTE METODO ES PARA HACER UNA PRUEBA DE USUARIOS REGISTRADSOS EN MEMORIA
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails salva= User.builder().username("salva")
                .password(passwordEncoder().encode("password"))
                .roles("USER").build();
        UserDetails admin= User.builder().username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN").build();
                //Por el momento los usuarios están registrados en memoria
        return new InMemoryUserDetailsManager(salva, admin);
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
