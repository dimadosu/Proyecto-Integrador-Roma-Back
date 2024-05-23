package com.backend.proyectointegradorromabackend.auth.filters;

import com.backend.proyectointegradorromabackend.models.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.backend.proyectointegradorromabackend.auth.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    //método constructor

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class); //obtenemos los datos json del request y lo poblamos al objeto user
            username = user.getUsername(); //recuperamos el user y lo igualamos a la variable
            password = user.getPassword();

            //muestra el username y password en la consola
            //en producción quitar
            logger.info("Username desde request InputStrem (raw) " + username);
            logger.info("Password desde request InputStrem (raw) " + password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        //authenticationManager -> encargado de autenticar con el metódo
        //el cuerpo del authToken ya tiene el username y password
        return  authenticationManager. authenticate(authToken);
    }

    //método cuando se realiza la autenticación, generar respuesta exitosa al frontend
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //casteamos el username que tenemos a la clase userdetails.User de Spring Security
        String username = ((org.springframework.security.core.userdetails.User)authResult.getPrincipal())
                .getUsername();

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities(); //obtenemos los roles del usuario logueaedo
        boolean isAdmin = roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        Claims claims = Jwts.claims();
        claims.put("authorities",new ObjectMapper().writeValueAsString(roles)); //agregamos los roles para mandarlo al token
        claims.put("isAdmin", isAdmin);
        claims.put("username", username);

        //TOKEN CON JWT
        String token = Jwts.builder()
                .setClaims(claims) //los roles lo mandamos al token
                .setSubject(username)//mandamos el username
                .signWith(SECRET_KEY) //creamos el token con este filtro
                .setIssuedAt(new Date())//fecha actual
                .setExpiration(new Date(System.currentTimeMillis()+3600000))//fecha de expiración del toke +1hs
                .compact();
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        //creamos mediante un map un "objeto json"
        Map<String,Object> body = new HashMap<>();
        body.put("token", token);
        body.put("mensaje",String.format( "Hola %s has iniciado sesion con exito", username));
        body.put("username", username);

        //el body en formato map lo convertimos a json
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");

    }

    //método cuando sale mal la autenticación, generar respuesta al frontend
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //map que contiene el cuerpo del json a continuación
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error en la autenticacion username o password incorrecto");
        body.put("error", failed.getMessage()); //información del error

        //el map lo pasamos a formato json
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
}
