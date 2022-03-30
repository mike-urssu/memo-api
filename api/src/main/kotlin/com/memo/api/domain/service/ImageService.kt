package com.memo.api.domain.service

import com.memo.api.domain.exception.CannotViewImageException
import com.memo.api.domain.exception.ImageNotFoundException
import com.memo.api.domain.model.entity.Image
import com.memo.api.domain.model.entity.Memo
import com.memo.api.domain.model.repository.ImageRepository
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
@Transactional
class ImageService(
    private val imageRepository: ImageRepository
) {
    @Value("\${application.upload-path}")
    lateinit var uploadPath: String

    fun createImages(memo: Memo, imagesFromRequest: List<MultipartFile>) {
        val images = imagesFromRequest.stream()
            .filter { !it.isEmpty }
            .map {
                val fileName = it.originalFilename!!
                val savedName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileName)
                val image = Image(memo = memo, fileName = fileName, savedName = savedName)
                memo.images.add(image)
                it.transferTo(File(File(uploadPath).absolutePath, savedName))
                image
            }.collect(Collectors.toList())
        imageRepository.saveAll(images)
    }

    fun updateImages(memo: Memo, imagesFromRequest: List<MultipartFile>?) {
        if (imagesFromRequest.isNullOrEmpty())
            return

        deleteImages(memo.images)
        createImages(memo, imagesFromRequest)
    }

    fun deleteImages(images: List<Image>) {
        images.forEach { File(uploadPath, it.savedName).delete() }
        imageRepository.deleteAllInBatch(images)
    }

    fun viewImage(imageId: Int): ByteArray {
        val image = imageRepository.findById(imageId).orElseThrow { ImageNotFoundException(imageId) }
        return viewImage(uploadPath + File.separator + image.savedName)
    }

    private fun viewImage(fileName: String): ByteArray {
        try {
            FileInputStream(fileName).use { inputStream ->
                ByteArrayOutputStream().use { outputStream ->
                    val buffer = ByteArray(8192)
                    var length: Int
                    while (inputStream.read(buffer).also { length = it } != -1)
                        outputStream.write(buffer, 0, length)
                    return outputStream.toByteArray()
                }
            }
        } catch (e: IOException) {
            throw CannotViewImageException(fileName)
        }
    }
}
