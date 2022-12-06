package com.mx.rockstar.core.database.entity.mapper

import com.mx.rockstar.core.database.entity.RoverEntity
import com.mx.rockstar.core.model.Rover

object RoverEntityMapper : EntityMapper<List<Rover>, List<RoverEntity>> {
    override fun asEntity(domain: List<Rover>): List<RoverEntity> {
        return domain.map { rover ->
            RoverEntity(
                id = rover.id,
                name = rover.name,
                landingDate = rover.landingDate,
                launchDate = rover.launchDate,
                status = rover.status,
                maxSol = rover.maxSol,
                maxDate = rover.maxDate,
                totalPhotos = rover.totalPhotos,
                cameras = rover.cameras,
                image = rover.image
            )
        }
    }

    override fun asDomain(entity: List<RoverEntity>): List<Rover> {
        return entity.map { roverEntity ->
            Rover(
                id = roverEntity.id,
                name = roverEntity.name,
                landingDate = roverEntity.landingDate,
                launchDate = roverEntity.launchDate,
                status = roverEntity.status,
                maxSol = roverEntity.maxSol,
                maxDate = roverEntity.maxDate,
                totalPhotos = roverEntity.totalPhotos,
                cameras = roverEntity.cameras,
                image = roverEntity.image
            )
        }
    }
}

fun List<Rover>.asEntity(): List<RoverEntity> {
    return RoverEntityMapper.asEntity(this)
}

fun List<RoverEntity>.asDomain(): List<Rover> {
    return RoverEntityMapper.asDomain(this)
}
