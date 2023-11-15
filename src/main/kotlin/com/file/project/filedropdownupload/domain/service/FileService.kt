package com.file.project.filedropdownupload.domain.service

import com.file.project.filedropdownupload.domain.model.File
import com.file.project.filedropdownupload.domain.repository.FileRepository
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class FileService(
    private val fileRepository: FileRepository
) {
    fun store(
        file: MultipartFile
    ): File {
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        val fileEntity = File(
            id = UUID.randomUUID().toString(),
            name = fileName,
            mimeType = file.contentType,
            data = file.bytes
        )
        val savedEntity = fileRepository.save(fileEntity)
        return fileRepository.findById(savedEntity.id).get()
    }

    fun getFile(
        id: String
    ): File {
        return fileRepository.findById(id).get()
    }

    fun getAllFiles(): List<File> {
        return fileRepository.findAll()
    }
}