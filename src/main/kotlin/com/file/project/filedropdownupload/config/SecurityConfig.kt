package com.file.project.filedropdownupload.config

import com.file.project.filedropdownupload.config.authentication.filter.JwtAuthenticationFilter
import com.file.project.filedropdownupload.config.authentication.provider.GoogleOAuthAuthenticationProvider
import com.file.project.filedropdownupload.config.authentication.success.handler.GoogleOAuthAuthenticationSuccessHandler
import com.file.project.filedropdownupload.config.service.GoogleOidcUserService
import com.file.project.filedropdownupload.config.service.JwtService
import com.file.project.filedropdownupload.config.service.MyUserDetailsService
import com.file.project.filedropdownupload.domain.repository.RoleRepository
import com.file.project.filedropdownupload.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${domain}")
    private val domain: String,
    @Value("\${frontend.url}")
    private val frontEndUrl: String,
    private val googleOAuthAuthenticationProvider: GoogleOAuthAuthenticationProvider,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val myUserDetailsService: MyUserDetailsService,
    private val jwtService: JwtService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors {  }
            csrf { disable() }
            authorizeRequests {
                authorize(anyRequest, permitAll)
            }
            oauth2Login {
                userInfoEndpoint {
                    oidcUserService = oidcUserService()
                }
                authenticationSuccessHandler = googleOAuthAuthenticationSuccessHandler()
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(
                JwtAuthenticationFilter(
                    jwtService,
                    myUserDetailsService
                )
            )
        }
        return http.build()
    }


    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().applyPermitDefaultValues()
        config.allowedOrigins = listOf("http://localhost:5000")
        config.allowedMethods = listOf("*")
        config.allowedHeaders = listOf("Authorization", "Content-Type", "Accept", "Set-Cookie")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun googleOAuthAuthenticationSuccessHandler(): GoogleOAuthAuthenticationSuccessHandler {
        return GoogleOAuthAuthenticationSuccessHandler(
            domain,
            frontEndUrl,
            googleOAuthAuthenticationManager(googleOAuthAuthenticationProvider),
            myUserDetailsService,
            userRepository,
            roleRepository,
            jwtService
        )
    }

    @Bean
    fun googleOAuthAuthenticationManager(googleOAuthAuthenticationProvider: GoogleOAuthAuthenticationProvider): AuthenticationManager {
        return ProviderManager(listOf(googleOAuthAuthenticationProvider))
    }

    @Bean
    fun oidcUserService(): OidcUserService {
        return GoogleOidcUserService(OidcUserService())
    }
}