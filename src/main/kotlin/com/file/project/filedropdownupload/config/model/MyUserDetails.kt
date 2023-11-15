package com.file.project.filedropdownupload.config.model

import com.file.project.filedropdownupload.domain.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class MyUserDetails(
    private val user: User
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableCollection<GrantedAuthority> = mutableListOf()
        authorities.add(GrantedAuthority { "ROLE_${user.role.name}" })
        return authorities
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return user.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
         return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun getUserId(): String {
        return user.id
    }
}