package com.file.project.filedropdownupload.application.rest

import com.file.project.filedropdownupload.domain.model.File
import com.file.project.filedropdownupload.domain.service.FileService
import org.flywaydb.core.api.resource.Resource
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("\${api.route}/file")
class FileController(
    private val fileService: FileService
) {
    @GetMapping("/test")
    fun test(): String {
        return "Hello World"
    }

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        return try {
            fileService.store(file)
            ResponseEntity.ok("File uploaded successfully: " + file.originalFilename)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file: " + file.originalFilename)
        }
    }

    @GetMapping("/all")
    fun getAllFiles(): ResponseEntity<List<File>> {
        return try {
            val files = fileService.getAllFiles()
            ResponseEntity.ok(files)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/download/{id}")
    fun downloadFile(@PathVariable id: String): ResponseEntity<File> {
        return try {
            val file = fileService.getFile(id)
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.name + "\"")
                .body(file)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/view/{id}")
    fun viewFile(@PathVariable id: String): ResponseEntity<ByteArrayResource> {
        return try {
            val file = fileService.getFile(id)
            val resource = ByteArrayResource(file.data)
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.name + "\"")
                .contentType(MediaType.parseMediaType(file.mimeType!!))
                .body(resource)
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
}