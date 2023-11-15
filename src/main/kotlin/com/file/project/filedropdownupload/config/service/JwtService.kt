package com.file.project.filedropdownupload.config.service

import com.file.project.filedropdownupload.config.model.MyUserDetails
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${backend.url}")
    private val backendUrl: String,
    private val secretKey: SecretKey
) {
    fun generateToken(principal: MyUserDetails): String {
        val now = Date()
        val validity = Date(now.time + ACCESS_TOKEN_VALIDITY)
        return Jwts.builder()
            .setIssuer("${backendUrl}/authenticate")
            .setSubject(principal.username)
            .setAudience("authenticate-api")
            .setId(principal.getUserId())
            .setExpiration(validity)
            .setIssuedAt(now)
            .claim("authorities", principal.authorities.map { it.authority })
            .signWith(secretKey)
            .compact()
    }

    fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun isTokenExpired(token: String): Boolean {
        return getClaims(token).expiration.before(Date())
    }

    fun isTokenValid(token: String, userDetails: MyUserDetails): Boolean {
        return getClaims(token).subject == userDetails.username && !isTokenExpired(token)
    }

    companion object {
        private const val ACCESS_TOKEN_VALIDITY = 3600000L // Token validity: 1 hour
    }
}