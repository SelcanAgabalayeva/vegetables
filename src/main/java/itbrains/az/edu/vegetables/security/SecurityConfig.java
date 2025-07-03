package itbrains.az.edu.vegetables.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailService userDetailService;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/");
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers("/admin/product/create", "/admin/product/delete/**").hasRole("ADMIN")
                        .requestMatchers("/admin/product/edit/**").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers("/admin/product/**").hasAnyRole("ADMIN", "EDITOR")
                        .requestMatchers("/cart/**", "/checkout/**").authenticated()
                        .anyRequest().permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/login");
                        })
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error=true")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/");
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }


    public void configure(AuthenticationManagerBuilder auth)

            throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());

    }

}
