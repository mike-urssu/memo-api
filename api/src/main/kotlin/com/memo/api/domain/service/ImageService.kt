package com.memo.api.domain.service

import com.memo.api.domain.model.entity.Image
import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.repository.ImageRepository
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
class ImageService(
    private val imageRepository: ImageRepository
) {
    @Value("\${application.upload-path}")
    lateinit var uploadPath: String

    @Transactional
    fun createFiles(memo: Memo, filesFromRequest: List<MultipartFile>) {
        val files = mutableListOf<File>()
        for (fileFromRequest in filesFromRequest) {
            if (!fileFromRequest.isEmpty) {
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
        }
        fileRepository.saveAll(files)
    }
}
