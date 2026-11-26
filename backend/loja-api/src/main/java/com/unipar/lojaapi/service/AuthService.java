package com.unipar.lojaapi.service;


import com.unipar.lojaapi.dto.LoginRequestDto;
import com.unipar.lojaapi.dto.LoginResponseDto;
import com.unipar.lojaapi.exception.CredenciaisInvalidasException;
import com.unipar.lojaapi.model.Usuario;
import com.unipar.lojaapi.repository.UsuarioRepository;
import com.unipar.lojaapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto dto){

        Usuario usuario = usuarioRepository.findByLogin(dto.login())
                .orElseThrow(() -> new CredenciaisInvalidasException("Login ou senha inválidos"));

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())){
            throw new CredenciaisInvalidasException("Login ou senha inválidos");
        }

        String token = jwtService.gerarToken(usuario);
        return new LoginResponseDto(token, usuario.getLogin(), usuario.getPerfil().name());
    }
}
