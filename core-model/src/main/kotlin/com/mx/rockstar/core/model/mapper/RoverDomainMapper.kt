package com.mx.rockstar.core.model.mapper

import com.mx.rockstar.core.model.Photo
import com.mx.rockstar.core.model.Rover

object RoverDomainMapper : DomainMapper<Rover, Photo.Rover> {
    override fun asCapsule(domain: Rover): Photo.Rover {
        return Photo.Rover(
            id = domain.id,
            name = domain.name,
            launchDate = domain.launchDate,
            landingDate = domain.landingDate,
            status = domain.status
        )
    }

    override fun asDomain(capsule: Photo.Rover): Rover {
        return Rover(
            id = capsule.id,
            name = capsule.name,
            launchDate = capsule.launchDate ?: "",
            landingDate = capsule.landingDate ?: "",
            status = capsule.status ?: ""
        )
    }
}

fun Rover.asCapsule(): Photo.Rover {
    return RoverDomainMapper.asCapsule(this)
}

fun Photo.Rover.asDomain(): Rover {
    return RoverDomainMapper.asDomain(this)
}