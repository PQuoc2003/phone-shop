package com.example.phonecommerce.security;


import com.example.phonecommerce.models.Roles;
import com.example.phonecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService customUserDetailsService() {
        return new UserDetailsServiceImp(userRepository);
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService())
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( author ->
                        author.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/forgot-password").permitAll()
                                .requestMatchers("/css/**", "/javascript/**", "/image/**").permitAll()
                                .requestMatchers("/admin/**").hasAuthority(String.valueOf(Roles.ROLES_ADMIN))
                                .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login.loginPage("/login")
                                .loginProcessingUrl("/process-login")
                                .defaultSuccessUrl("/home", true)
                                .successHandler((request, response, authentication) -> {
                                    if (authentication.getName().equals("admin_template")) {
                                        response.sendRedirect("/admin_template");
                                    } else {
                                        response.sendRedirect("/home");
                                    }
                                })
                                .permitAll()
                )
                .logout(logout ->
                        logout.invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .permitAll()
                )
                .authenticationManager(authenticationManager)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
        ;
        return http.build();
    }


}
