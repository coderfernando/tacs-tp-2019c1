package ar.edu.utn.tacs.config;

import ar.edu.utn.tacs.services.MongoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    MongoUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/api/places/**")
                .authenticated().and().authorizeRequests().antMatchers("/api/me/**")
                .authenticated().and().authorizeRequests().antMatchers("/api/admin/**")
                .authenticated().and().formLogin().and().logout();
    }

//TODO: DEJO COMENTADO ESTO PARA PODER PASAR EL REGISTER. HAY QUE REVISARLO
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/resources/**").permitAll()
//                .antMatchers("/static/**").permitAll()
//                .antMatchers("/login*").permitAll()
//                .antMatchers("/logout*").permitAll()
//                .antMatchers("/signin*").permitAll()
//                .antMatchers("/api/user/*").permitAll()
//                .antMatchers("/api/user/login*").permitAll()
//                .antMatchers("/api/user/logout*").permitAll()
//                .antMatchers("/api/user/isuserlogged*").permitAll()
//                .anyRequest().authenticated()
//                .and().formLogin();
//
//        // disable page caching
//        http.headers().cacheControl();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
}