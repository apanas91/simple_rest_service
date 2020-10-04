package service.config;

import service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/authenticate").permitAll()
                .antMatchers("/api/v1/registration").permitAll()
                .antMatchers("/api/v1/message/**").permitAll()
                .antMatchers("/api/v2/**").permitAll()
//                .and().authorizeRequests().antMatchers("/message/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .and().authorizeRequests().antMatchers("/api/v1/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .and().authorizeRequests().antMatchers("/api/v1/user/**").permitAll()
                .and().authorizeRequests().antMatchers("/api/v1/order/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .and().authorizeRequests().antMatchers("/api/v1/admin/**").hasAuthority("ROLE_ADMIN")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}