package com.app.jwt.configuracion;

import com.app.jwt.security.CustomUserDetailService;
import com.app.jwt.security.JwtAuthenticationEntryPoint;
import com.app.jwt.security.JwtAuthenticationFilter;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig  {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable() //deshabilitarlo ya spring lo implementa
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET,"/api/**")
                .permitAll()//metodo get se permite a todos
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                //.addFilter(jwtAuthenticationFilter())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();

        //http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

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

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    } */

    @Bean
    protected  AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

}
