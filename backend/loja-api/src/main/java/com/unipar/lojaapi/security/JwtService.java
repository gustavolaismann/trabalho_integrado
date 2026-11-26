package com.unipar.lojaapi.security;

import com.unipar.lojaapi.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiracao-ms}")
    private long expiracaoMs;

    private SecretKey chave(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Cria o token no login
    public String gerarToken(Usuario usuario){
        Date now = new Date();
        return Jwts.builder()
                .subject(usuario.getLogin())
                .claim("perfil", usuario.getPerfil().name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiracaoMs))
                .signWith(chave())
                .compact();

    }

    // Le o token e se a assinatura estiver errada ou token vencido lança JWTEXCEPTION
    private Claims lerClaims(String token){
        return Jwts.parser()
                .verifyWith(chave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    // Devolve o login que esta dentro do token
    public String extrairLogin(String token){
        return lerClaims(token).getSubject();
    }

    // Diz se o token é valido
    public boolean tokenValido(String token){
        try{
            lerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e ){
            return false;
        }
    }
}
