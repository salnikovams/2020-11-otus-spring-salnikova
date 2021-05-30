package ru.otus.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@EnableWebSecurity
@Configuration
@ComponentScan
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;


    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Autowired
    private CustomAuthenticationSuccessHandler mySuccessHandler;

    private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/limit")
                .hasAnyRole("OPERATOR", "CONTROLLER")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/limit")
                .hasRole("OPERATOR")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT,"/limit/**", "/limit")
                .hasRole("OPERATOR")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE,"/limit/**")
                .hasRole("OPERATOR")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT,"/checkLimit")
                .hasAnyRole("OPERATOR", "CONTROLLER")
                .and()
                .formLogin()
                .successHandler(mySuccessHandler)
                .failureHandler(myFailureHandler)
                .and()
                .httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
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
