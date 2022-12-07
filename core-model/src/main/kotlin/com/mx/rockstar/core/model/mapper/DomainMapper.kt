package com.mx.rockstar.core.model.mapper

interface DomainMapper<Domain, Capsule> {

    fun asCapsule(domain: Domain): Capsule

    fun asDomain(capsule: Capsule): Domain

}