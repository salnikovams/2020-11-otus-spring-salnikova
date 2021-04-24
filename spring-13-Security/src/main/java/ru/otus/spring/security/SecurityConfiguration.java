package ru.otus.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;


    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/books")
                .hasAnyRole("USER", "ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers("/books/comment")
                .hasAnyRole("USER", "ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers("/books/**")
                .hasRole("ADMIN")
                .and()
                .formLogin().and()
                .httpBasic()
                .and()
                .logout();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
      return new PasswordEncoder(){

          @Override
          public String encode(CharSequence charSequence) {
              return charSequence.toString();
          }

          @Override
          public boolean matches(CharSequence charSequence, String s) {
              return charSequence.toString().equals(s);
          }
      };
    }



    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider (){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

}
