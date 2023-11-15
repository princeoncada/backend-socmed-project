package com.file.project.filedropdownupload.domain.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "tbl_users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,

    @Column(name="email", unique = true, nullable = false)
    val email: String,

    @Column(name="password", nullable = false)
    val password: String,

    @ManyToOne
    @JoinColumn(name="role_id", referencedColumnName = "id")
    val role: Role,

    @CreationTimestamp
    @Column(name="created_at", nullable = false)
    val createdAt: Instant,

    @UpdateTimestamp
    @Column(name="updated_at", nullable = false)
    val updatedAt: Instant
)