package com.file.project.filedropdownupload.domain.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "tbl_posts")
data class Post (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "description")
    val description: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false, referencedColumnName = "id")
    val file: File,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant
)