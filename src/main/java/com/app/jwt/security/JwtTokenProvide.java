package com.app.jwt.security;

import com.app.jwt.exeption.BlogAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

@Component //esto indica que es un componenete de spring
public class JwtTokenProvide {
    //Se va a generar el token, los claims(privilegios de usuario)
    @Value("${app.jwt-secret}")//@Value para obtener un valor de una propiedad
    private String jwtSecret;
    //El tiempo que va a estar activo el token
    @Value("${app.jwt-expiration-milliseconds}")//@Value para obtener un valor de una propiedad
    private int jwtExpiration;

    public String generearTokenAcceso(Authentication authentication){
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime()+jwtExpiration);
        //Se establecen los datos anteriores al token
        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();

       return token;
    }
   //OBTIENE EL USUARIO
    public String ObtenerUsernameDelJWT(String token){
        //CLAIMS del token(solicitudes) del token  = datos, usuario, fecha de caducidad, password, roles
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();

    }
     //METODO PARA VALIDAR EL TOKEN QUE LOS DATOS SEAN CORRECTOS, NO ESTE CADUCADO
    public boolean validarToken(String token){
      try{
          //validar el token con su llave secreta
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
      }catch(SignatureException ex){
          throw new BlogAppException(HttpStatus.BAD_REQUEST,"Firma JWT inválida!");
      }

      catch(MalformedJwtException ex){
          throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT inválido!");
      }

      catch(ExpiredJwtException ex){
          throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT expirado!");
      }

      catch(UnsupportedJwtException ex){
          throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT incompatible!");
      }

      catch(IllegalArgumentException ex){
          throw new BlogAppException(HttpStatus.BAD_REQUEST,"La cadena claims JWT está vacía!");
      }
    }

}
