package com.ap.fq.Security.Controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.ap.fq.Security.Dto.JwtDto;
import com.ap.fq.Security.Dto.LoginUsuario;
import com.ap.fq.Security.Dto.NuevoUsuario;
import com.ap.fq.Security.Entity.Rol;
import com.ap.fq.Security.Entity.Usuario;
import com.ap.fq.Security.Enums.RolNombre;
import com.ap.fq.Security.Service.RolService;
import com.ap.fq.Security.Service.UsuarioService;
import com.ap.fq.Security.jwt.JwtProvider;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Set;
import com.ap.fq.Security.Enums.RolNombre;
import com.ap.fq.Security.Entity.Rol;
import java.util.HashSet;
import com.ap.fq.Security.Entity.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import com.ap.fq.Security.Dto.NuevoUsuario;
import com.ap.fq.Security.jwt.JwtProvider;
import com.ap.fq.Security.Service.RolService;
import com.ap.fq.Security.Service.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/auth" })
@CrossOrigin
public class AuthController
{
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RolService rolService;
    @Autowired
    JwtProvider jwtProvider;
    
    @PostMapping({ "/nuevo" })
    public ResponseEntity<?> nuevo(@Valid @RequestBody final NuevoUsuario nuevoUsuario, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<?>)new ResponseEntity((Object)new Mensaje("Campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);
        }
        if (this.usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
            return (ResponseEntity<?>)new ResponseEntity((Object)new Mensaje("Ese nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        }
        if (this.usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
            return (ResponseEntity<?>)new ResponseEntity((Object)new Mensaje("Ese email ya existe"), HttpStatus.BAD_REQUEST);
        }
        final Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(), this.passwordEncoder.encode((CharSequence)nuevoUsuario.getPassword()));
        final Set<Rol> roles = new HashSet<Rol>();
        roles.add(this.rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if (nuevoUsuario.getRoles().contains("admin")) {
            roles.add(this.rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }
        usuario.setRoles((Set)roles);
        this.usuarioService.save(usuario);
        return (ResponseEntity<?>)new ResponseEntity((Object)new Mensaje("Usuario guardado"), HttpStatus.CREATED);
    }
    
    @PostMapping({ "/login" })
    public ResponseEntity<JwtDto> login(@Valid @RequestBody final LoginUsuario loginUsuario, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<JwtDto>)new ResponseEntity((Object)new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        }
        final Authentication authentication = this.authenticationManager.authenticate((Authentication)new UsernamePasswordAuthenticationToken((Object)loginUsuario.getNombreUsuario(), (Object)loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = this.jwtProvider.generateToken(authentication);
        final UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        final JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return (ResponseEntity<JwtDto>)new ResponseEntity((Object)jwtDto, HttpStatus.OK);
    }
}