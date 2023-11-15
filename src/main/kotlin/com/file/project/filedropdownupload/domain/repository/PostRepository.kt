package com.file.project.filedropdownupload.domain.repository

import com.file.project.filedropdownupload.domain.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<Post, String> {
    fun findById(id: String, pageable: Pageable): Page<Post>
}