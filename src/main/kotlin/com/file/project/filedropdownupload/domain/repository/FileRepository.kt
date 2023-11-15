package com.file.project.filedropdownupload.domain.repository

import com.file.project.filedropdownupload.domain.model.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FileRepository: JpaRepository<File, String> {
}