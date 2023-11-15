package com.file.project.filedropdownupload.domain.model

import jakarta.persistence.*
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy

@Entity
@Table(name = "tbl_files")
data class File(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String,

    @Column(name = "name")
    val name: String?,

    @Column(name = "mime_type")
    val mimeType: String?,

    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    val data: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as File

        if (id != other.id) return false
        if (name != other.name) return false
        if (mimeType != other.mimeType) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + mimeType.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}