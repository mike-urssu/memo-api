package com.memo.api.domain.service

import com.memo.api.domain.exception.CannotViewImageException
import com.memo.api.domain.exception.ImageNotFoundException
import com.memo.api.domain.exception.InvalidFileTypeException
import com.memo.api.domain.model.entity.Post
import com.memo.api.domain.model.entity.Thumbnail
import com.memo.api.domain.model.repository.ThumbnailRepository
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.UUID
import javax.servlet.ServletContext
import javax.transaction.Transactional

@Service
@Transactional
class ThumbnailService(
    private val thumbnailRepository: ThumbnailRepository,
    private val servletContext: ServletContext
) {
    @Value("\${application.upload-path}")
    lateinit var uploadPath: String

    fun createThumbnailIfPresent(post: Post, imagesFromRequest: MultipartFile?) {
        if (imagesFromRequest == null || imagesFromRequest.isEmpty)
            return

        val filename = imagesFromRequest.originalFilename!!
        val mimeType = servletContext.getMimeType(filename)
        if (!mimeType.startsWith("image/"))
            throw InvalidFileTypeException(mimeType)

        val savedName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(filename)
        imagesFromRequest.transferTo(File(File(uploadPath).absolutePath, savedName))

        val thumbnail = Thumbnail(filename = filename, savedName = savedName)
        thumbnailRepository.save(thumbnail)
        post.thumbnail = thumbnail
    }

    fun updateThumbnail(post: Post, thumbnail: MultipartFile?) {
        if (thumbnail == null || thumbnail.isEmpty)
            return

        deleteThumbnailIfPresent(post)
        createThumbnailIfPresent(post, thumbnail)
    }

    fun deleteThumbnailIfPresent(post: Post) {
        if (post.thumbnail != null) {
            File(uploadPath, post.thumbnail!!.savedName).delete()
            thumbnailRepository.delete(post.thumbnail!!)
        }
    }

    fun viewImage(imageId: Int): ByteArray {
        val image = thumbnailRepository.findById(imageId).orElseThrow { ImageNotFoundException(imageId) }
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
