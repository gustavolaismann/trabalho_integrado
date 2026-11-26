package com.unipar.lojaapi.security;


import com.unipar.lojaapi.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String cabecalho = request.getHeader("Authorization");

        if (cabecalho != null && cabecalho.startsWith("Bearer ")){
            String token = cabecalho.substring(7); // tira o bearer

            if (jwtService.tokenValido(token)){
                String login = jwtService.extrairLogin(token);

                usuarioRepository.findByLogin(login).ifPresent(usuario -> {

                    var permissao = new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil().name());
                    var autenticacao = new UsernamePasswordAuthenticationToken(login, null, List.of(permissao));
                    SecurityContextHolder.getContext().setAuthentication(autenticacao);
                });
            }
        }

        // Segue para o proximo filtro e depois para o controller
        filterChain.doFilter(request, response);
    }
}
