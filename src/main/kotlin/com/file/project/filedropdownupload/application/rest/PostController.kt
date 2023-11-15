package com.file.project.filedropdownupload.application.rest

import com.file.project.filedropdownupload.domain.service.PostService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.route}/post")
class PostController(
    private val postService: PostService
) {
    @GetMapping
    fun getEntities(
        pageable: Pageable
    ): ResponseEntity<Any> {
        return try {
            val entities = postService.getEntities(pageable)
            ResponseEntity.ok(entities)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createEntity(
        @RequestBody requestEntity: PostService.PostRequest,
        pageable: Pageable
    ): ResponseEntity<Any> {
        return try {
            val authorization = SecurityContextHolder.getContext().authentication
            val entity = postService.createEntity(authorization.name, requestEntity, pageable)
            ResponseEntity.ok(entity)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}