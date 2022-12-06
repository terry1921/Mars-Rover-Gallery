package com.mx.rockstar.core.test

import com.mx.rockstar.core.model.Camera
import com.mx.rockstar.core.model.Photo
import com.mx.rockstar.core.model.Rover

object MockUtil {

    private fun mockCamera() = Camera(
        id = 14,
        name = "FHAZ",
        roverId = 6,
        fullName = "Front Hazard Avoidance Camera"
    )

    private fun mockCamera1() = Camera(
        id = 20,
        name = "FHAZ",
        roverId = 5,
        fullName = "Front Hazard Avoidance Camera"
    )

    private fun mockPhotoRover() = Photo.Rover(
        id = 6,
        name = "Opportunity",
        landingDate = "2004-01-25",
        launchDate = "2003-07-07",
        status = "2003-07-07"
    )

    fun mockPhoto() = Photo(
        id = 119056,
        sol = 10,
        earthDate = "2004-02-04",
        camera = mockCamera(),
        rover = mockPhotoRover(),
        imgSrc = "http://mars.nasa.gov/mer/gallery/all/1/f/010/1F129070908EDN0224P1101L0M1-BR.JPG",
        page = 1
    )

    fun mockPhoto1() = Photo(
        id = 119057,
        sol = 10,
        earthDate = "2004-02-04",
        camera = mockCamera(),
        rover = mockPhotoRover(),
        imgSrc = "http://mars.nasa.gov/mer/gallery/all/1/f/010/1F129070908EDN0224P1101R0M1-BR.JPG",
        page = 1
    )

    fun mockPhotoList() = listOf(mockPhoto(), mockPhoto1())

    fun mockRover() = Rover(
        id = 5,
        name = "Curiosity",
        landingDate = "2012-08-06",
        launchDate = "2011-11-26",
        status = "active",
        maxSol = 3671,
        maxDate = "2022-12-03",
        totalPhotos = 614335,
        cameras = listOf(mockCamera1()),
        image = "https://mars-photos.herokuapp.com/explore/images/Curiosity_rover.jpg"
    )

    fun mockRovers() = listOf(mockRover())

}