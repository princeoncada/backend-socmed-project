package com.file.project.filedropdownupload.domain.service

import com.file.project.filedropdownupload.domain.model.Role
import com.file.project.filedropdownupload.domain.repository.RoleRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {
    fun getEntities(
        pageable: Pageable
    ): Page<Role> {
        return roleRepository.findAll(pageable)
    }

    data class RoleRequest(
        val name: String
    )

    fun toEntity(
        id: String,
        request: RoleRequest
    ): Role {
        return Role(
            id = id,
            name = request.name,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }

    fun createEntity(
        request: RoleRequest,
        pageable: Pageable
    ): Page<Role> {
        val id = UUID.randomUUID().toString()
        val savedEntity = roleRepository.save(toEntity(id, request))
        return roleRepository.findById(savedEntity.id, pageable)
    }

    fun deleteEntity(
        id: String,
        pageable: Pageable
    ): Page<Role> {
        roleRepository.deleteById(id)
        return roleRepository.findAll(pageable)
    }
}