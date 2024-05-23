package com.backend.proyectointegradorromabackend.auth;

import com.backend.proyectointegradorromabackend.auth.filters.JwtAuthenticationFilter;
import com.backend.proyectointegradorromabackend.auth.filters.JwtValidationFilter;
import com.backend.proyectointegradorromabackend.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class SpringSecurityConfig {

    /*
    * Método que devuelve una cadena de las reglas de seguridad
    * */

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    //agregue esto
    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        //encriptamos
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager()throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/user/list").permitAll() // ruta publica que no requiere auth
                .requestMatchers(HttpMethod.GET, "/user/{id}").hasAnyRole("USER", "ADMIN") //en esta ruta pueden acceder usuarios y admin
                .requestMatchers(HttpMethod.POST, "/user/save").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                .requestMatchers(HttpMethod.POST,"/categoria/save").permitAll() //
                .requestMatchers(HttpMethod.PUT,"/categoria/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/categoria/**").permitAll() //cambiar esto
                .requestMatchers(HttpMethod.PUT, "/proveedor/{id}").permitAll()
                .requestMatchers("/proveedor/**").permitAll() //cambiar esto
                .requestMatchers("/marca/**").permitAll() //cambiar esto,
                .requestMatchers(HttpMethod.PUT, "/marca/{id}").permitAll()
                .requestMatchers(HttpMethod.GET,"/producto/list").hasAnyRole("USER","ADMIN","CUSTOMER")
                .requestMatchers("/producto/**").permitAll() //cambiar esto
                .anyRequest().authenticated() //cualquier otro request requiere auth
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(manager ->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }

    //agregue esto
    @Autowired
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService);
    }

    /*
    //CONFIGURACION DE CORS PARA EL CONSUMO DEL FRONTEND
    @Bean
    CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173"));//para que acceda al frontend
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT","DELETE")); //pra que acceda a los métodos
        config.setAllowedHeaders(Arrays.asList("Authorization","Content-Type")); //token y contenido
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); //la ruta en donde se aplica la configuracion
        return source;
    } */

    /*
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }*/
}
