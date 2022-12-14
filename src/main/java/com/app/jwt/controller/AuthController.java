package com.app.jwt.controller;

import com.app.jwt.dto.LoginDTO;
import com.app.jwt.dto.RegistroDTO;
import com.app.jwt.model.Rol;
import com.app.jwt.model.Usuario;
import com.app.jwt.repository.RolRepository;
import com.app.jwt.repository.UsuarioRepository;
import com.app.jwt.security.JWTAuthResponseDTO;
import com.app.jwt.security.JwtTokenProvide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; //Clase de spring

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvide jwtTokenProvide;

    @PostMapping("/login")
    //INICIAR SESSION, PASAR LAS CREDENCIALES
    public ResponseEntity<JWTAuthResponseDTO> unthenticateUser(@RequestBody LoginDTO loginDTO) {

        //Esto sirve para iniciar session, authenticar usuario
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //obtenemos el token de jwtTokenProvider
        String token = jwtTokenProvide.generearTokenAcceso(authentication);
        //return new ResponseEntity(token, HttpStatus.OK); responde como texto
        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    //METODO PARA REGISTRAR USUARIOS

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistroDTO registroDTO){
        //Hace la validadci??n por nombre de usuario (username)
        if(usuarioRepository.existsByUsername(registroDTO.getUsername())){
            return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        //Hace la validaci??n por email , si existe un usuario con un email registrado, no se podr?? registrar
        if(usuarioRepository.existsByEmail(registroDTO.getEmail())){
            return new ResponseEntity<>("Ese email de usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        //Si no se cumple ninguna de las dos anteriores condiciones,entoces resgisrtrar usuario

        Usuario usuario= new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        //Se encapsula el campo password automaticamente
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        //ESTABLECER LOS ROLES DEL USUARIO

        Rol roles = rolRepository.findByNombre("ROLE_ADMIN").get();
        usuario.setRoles(Collections.singleton(roles));
        usuarioRepository.save(usuario);
        return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);


    }

}
