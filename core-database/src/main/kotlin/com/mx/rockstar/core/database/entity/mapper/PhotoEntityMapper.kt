package com.mx.rockstar.core.database.entity.mapper

import com.mx.rockstar.core.database.entity.PhotoEntity
import com.mx.rockstar.core.model.Photo

object PhotoEntityMapper : EntityMapper<List<Photo>, List<PhotoEntity>> {
    override fun asEntity(domain: List<Photo>): List<PhotoEntity> {
        return domain.map { photo ->
            PhotoEntity(
                id = photo.id,
                sol = photo.sol,
                earthDate = photo.earthDate,
                camera = photo.camera,
                rover = photo.rover,
                imgSrc = photo.imgSrc,
                page = photo.page
            )
        }
    }

    override fun asDomain(entity: List<PhotoEntity>): List<Photo> {
        return entity.map { photoEntity ->
            Photo(
                id = photoEntity.id,
                sol = photoEntity.sol,
                earthDate = photoEntity.earthDate,
                camera = photoEntity.camera,
                rover = photoEntity.rover,
                imgSrc = photoEntity.imgSrc,
                page = photoEntity.page
            )
        }
    }

}

fun List<Photo>.asEntity(): List<PhotoEntity> {
    return PhotoEntityMapper.asEntity(this)
}

fun List<PhotoEntity>.asDomain(): List<Photo> {
    return PhotoEntityMapper.asDomain(this)
}