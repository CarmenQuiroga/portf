package com.backend.portf.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.backend.portf.Security.jwt.JwtEntryPoint;
import com.backend.portf.Security.jwt.JwtTokenFilter;





@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity  {


@Autowired
JwtEntryPoint jwtEntryPoint;

@Bean
public JwtTokenFilter jwtTokenFilter(){
   return new JwtTokenFilter();
}


@Bean
 public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
 } 
    
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       
       http.cors().and().csrf().disable() 
            .authorizeHttpRequests() 
            .antMatchers("**").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); 
        
         return http.build();
    }



   
    
}