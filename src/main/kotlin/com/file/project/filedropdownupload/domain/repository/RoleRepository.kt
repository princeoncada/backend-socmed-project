package com.file.project.filedropdownupload.domain.repository

import com.file.project.filedropdownupload.domain.model.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RoleRepository: JpaRepository<Role, String> {
    fun findById(id: String, pageable: Pageable): Page<Role>
    fun findByName(name: String): Optional<Role>
}