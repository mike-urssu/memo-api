package com.memo.api.domain.service

import com.memo.api.domain.model.entity.File
import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.repository.FileRepository
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.transaction.Transactional

@Service
class FileService(
    private val fileRepository: FileRepository
) {
    @Value("\${application.upload-path}")
    lateinit var uploadPath: String

    @Transactional
    fun createFiles(memo: Memo, filesFromRequest: List<MultipartFile>?) {
        if (filesFromRequest.isNullOrEmpty())
            return

        val files = mutableListOf<File>()
        for (fileFromRequest in filesFromRequest) {
            val fileName = fileFromRequest.originalFilename!!
            val savedName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileName)
            val file = File(
                id = null,
                memo = memo,
                fileName = fileName,
                savedName = savedName
            )
            files.add(file)
            memo.files.add(file)

            fileFromRequest.transferTo(java.io.File(java.io.File(uploadPath).absolutePath, savedName))
        }
        fileRepository.saveAll(files)
    }
}
