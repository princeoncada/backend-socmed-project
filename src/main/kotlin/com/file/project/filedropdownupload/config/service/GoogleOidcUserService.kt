package com.file.project.filedropdownupload.config.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.oidc.user.OidcUser

class GoogleOidcUserService (
    @Qualifier("googleOidcUserService") private val delegate: OidcUserService
): OidcUserService() {
    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oidcUser = delegate.loadUser(userRequest)
        val idToken = oidcUser.idToken
        return DefaultOidcUser(oidcUser.authorities, idToken, "email")
    }
}