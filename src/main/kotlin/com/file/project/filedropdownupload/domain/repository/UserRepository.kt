package com.file.project.filedropdownupload.domain.repository

import com.file.project.filedropdownupload.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, String> {
    fun findByEmail(email: String): Optional<User>
}