package com.file.project.filedropdownupload.domain.service

import com.file.project.filedropdownupload.domain.model.Post
import com.file.project.filedropdownupload.domain.repository.PostRepository
import com.file.project.filedropdownupload.domain.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.Instant
import java.util.*

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val fileService: FileService
) {
    fun getEntities(
        pageable: Pageable
    ): Page<Post> {
        return postRepository.findAll(pageable)
    }

    fun createEntity(
        email: String,
        request: PostRequest,
        pageable: Pageable
    ): Page<Post> {
        val id = UUID.randomUUID().toString()
        val savedEntity = postRepository.save(toEntity(id, email, request))
        return postRepository.findById(savedEntity.id, pageable)
    }

    data class PostRequest(
        val title: String,
        val file: MultipartFile
    )

    private fun toEntity (
        id: String,
        email: String,
        request: PostRequest,
    ): Post {
        return Post(
            id = id,
            title = request.title,
            description = "",
            user = userRepository.findByEmail(email).get(),
            file = fileService.store(request.file),
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    }
}