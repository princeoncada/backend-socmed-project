package com.file.project.filedropdownupload.config.authentication.provider

import com.file.project.filedropdownupload.config.model.MyUserDetails
import com.file.project.filedropdownupload.domain.repository.UserRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Component

@Component
class GoogleOAuthAuthenticationProvider(
    private val userRepository: UserRepository,
): AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        if (authentication is OAuth2AuthenticationToken) {
            val userDetails = extractUserDetails(authentication)
            return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        }
        throw UsernameNotFoundException("Authentication Provider: Google OAuth authentication failed.")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return OAuth2AuthenticationToken::class.java.isAssignableFrom(authentication)
    }

    private fun extractUserDetails(authentication: OAuth2AuthenticationToken): MyUserDetails {
        val email = authentication.principal.attributes["email"] as String
        val currentUser = userRepository.findByEmail(email)
        if (currentUser.isEmpty) throw UsernameNotFoundException("Authentication Provider / User Details Extraction: User not found with email: $email")
        return MyUserDetails(currentUser.get())
    }
}