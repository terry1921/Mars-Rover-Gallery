package com.mx.rockstar.core.net

import com.mx.rockstar.core.model.CameraAbbrev
import com.mx.rockstar.core.model.RoverType
import com.mx.rockstar.core.net.service.RoverService
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class RoverServiceTest : ApiAbstract<RoverService>() {

    private lateinit var service: RoverService
    private val sol = 10
    private val roverType = RoverType.OPPORTUNITY.toLower()
    private val camera = CameraAbbrev.FHAZ.name

    @Before
    fun initService() {
        service = createService(RoverService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchRoversFromNetworkTest() = runTest {
        enqueueResponse("/RoverResponse.json")
        val response = service.fetchRovers()
        val responseBody = requireNotNull((response as ApiResponse.Success).data)

        assertThat(responseBody.rovers.size, `is`(4))
        assertThat(responseBody.rovers[0].id, `is`(5))
        assertThat(responseBody.rovers[0].name, `is`("Curiosity"))
    }

    @Throws(IOException::class)
    @Test
    fun fetchPhotosFromNetworkTest() = runTest {
        enqueueResponse("/PhotoResponse.json")
        val response = service.fetchPhotos(type = roverType, sol = sol, camera = camera, page = 1)
        val responseBody = requireNotNull((response as ApiResponse.Success).data)

        assertThat(responseBody.photos.size, `is`(6))
        assertThat(responseBody.photos[0].id, `is`(119056))
        assertThat(responseBody.photos[0].earthDate, `is`("2004-02-04"))
    }

}