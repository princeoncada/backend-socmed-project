package com.file.project.filedropdownupload.config.service

import com.file.project.filedropdownupload.config.model.MyUserDetails
import com.file.project.filedropdownupload.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(
        email: String?
    ): MyUserDetails {
        return try {
            val currentUser = userRepository.findByEmail(email!!).get()
            MyUserDetails(currentUser)
        } catch (e: Exception) {
            throw UsernameNotFoundException("User not found with email: $email / This is inside UserDetailsService")
        }
    }
}