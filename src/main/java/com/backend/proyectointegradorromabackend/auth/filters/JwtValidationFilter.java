package com.backend.proyectointegradorromabackend.auth.filters;

import com.backend.proyectointegradorromabackend.auth.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.backend.proyectointegradorromabackend.auth.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain chain)
            throws IOException, ServletException {

        //obtenemos la cabezera del token
        String header = request.getHeader(HEADER_AUTHORIZATION);
        //validamos su hay algo en el token y si empieza con el prefijo Beader
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }
        //obtenemos el token
        String token = header.replace(PREFIX_TOKEN, "");


        try {
            //validamos si el toque coincide con el firmado
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();//obtenemos los clamins

            Object authoritiesClaims = claims.get("authorities");
            String username = claims.getSubject(); // obtenemos el username del token

            //
            Collection<? extends GrantedAuthority> authorities = Arrays
                    .asList(new ObjectMapper()
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                            .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));
            //
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        } catch (JwtException error) {
            //en caso falla el token
            //creando un map que contiene el error y mensaje
            Map<String, String> body = new HashMap<>();
            body.put("error", error.getMessage());
            body.put("message", "El token JWT no es valido");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body)); // mandamos el map a formato json
            response.setStatus(401); //estado no autorizado
            response.setContentType("application/json ");
        }
    }
}
