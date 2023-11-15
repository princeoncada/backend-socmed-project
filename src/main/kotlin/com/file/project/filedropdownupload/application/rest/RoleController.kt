package com.file.project.filedropdownupload.application.rest

import com.file.project.filedropdownupload.domain.service.RoleService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.route}/role")
class RoleController(
    private val roleService: RoleService
) {
    @GetMapping
    fun getEntities(
        pageable: Pageable
    ): ResponseEntity<Any> {
        return try {
            val entities = roleService.getEntities(pageable)
            ResponseEntity.ok(entities)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createEntity(
        @RequestBody requestEntity: RoleService.RoleRequest,
        pageable: Pageable
    ): ResponseEntity<Any> {
        return try {
            val entity = roleService.createEntity(requestEntity, pageable)
            ResponseEntity.ok(entity)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEntity(
        @PathVariable id: String,
        pageable: Pageable
    ): ResponseEntity<Any> {
        return try {
            val entities = roleService.deleteEntity(id, pageable)
            ResponseEntity.ok(entities)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}