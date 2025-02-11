package group7.security;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder createEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvcMatcher) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers(mvcMatcher.pattern("/users"), mvcMatcher.pattern("/admin/**"), mvcMatcher.pattern("/h2-console/**")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcher.pattern("/order**")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(mvcMatcher.pattern("/beverages/**")).permitAll()
                        .requestMatchers(mvcMatcher.pattern("/basket**")).permitAll()
                        .requestMatchers(mvcMatcher.pattern("/*")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .failureUrl("/login?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                //.csrf(request -> request.ignoringRequestMatchers(PathRequest.toH2Console()))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/public/**") // Ignore CSRF for public APIs (example)
                )
                .headers(headers -> headers.frameOptions(option -> option.sameOrigin()))
                .logout((logout) -> logout.permitAll().invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login");
    }


}
