package com.meli.app

import com.meli.app.model.Product
import com.meli.app.model.ProductResultQuery
import com.meli.app.network.NetworkProductDataSource
import com.meli.app.utils.MeliResult
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.junit.Assert.*
import retrofit2.Response
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class FakeNetworkProductDataSourceTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(meliModulesTest) }

    private val fakeProductApiService: FakeProductApiService by inject(
        named(FAKE_PRODUCT_API_SERVICE)
    )

    private val productResponse = """
    {
        "id": "MCO534830668",
        "title": "Super Mario Bros Encima De La Tarjeta De Alimentación Del Ju",
        "price": 140000,
        "sold_quantity": 0,
        "pictures": [{"id":"890946-MCO32276837852_092019","url":"http://http2.mlstatic.com/D_890946-MCO32276837852_092019-O.jpg"}],
        "attributes": [{"id":"COLOR","name":"Color","value_name":null}],
        "warranty": "Garantía del vendedor: 30 dias",
        "available_quantity": 1
    }
    """.trimIndent()

    private val productListResponse = """
    {
        "results": [
            {
                "id": "MCO588325967",
                "title": "Juego Sin Encuadernar De 5 Piezas Fnaf Figuras De Accion Ju",
                "price": 109990,
                "thumbnail": "http://http2.mlstatic.com/D_840740-MCO43734513352_102020-I.jpg",
                "installments": {
                    "quantity": 36,
                    "amount": 3055.28
                }
            }, 
            {
                "id": "MCO588325967",
                "title": "Juego Sin Encuadernar De 5 Piezas Fnaf Figuras De Accion Ju",
                "price": 109990,
                "thumbnail": "http://http2.mlstatic.com/D_840740-MCO43734513352_102020-I.jpg",
                "installments": {
                    "quantity": 36,
                    "amount": 3055.28
                }
            }
        ]
    }
    """.trimIndent()

    private val fakeErrorProductListBody = """
        {
            "error": "Search error"
        }
    """.trimIndent()

    private val fakeErrorProductBody = """
        {
            "error": "Product not found"
        }
    """.trimIndent()

    @Test
    fun `Remote data source should return a MeliResult Success with the list of products`() =
        runBlocking {
            //Given
            val query = "juegos"
            val networkDataSource = NetworkProductDataSource(fakeProductApiService)
            fakeProductApiService.getProductListByQueryResponse =
                Response.success(convertFromJson(productListResponse))

            //When
            val result = networkDataSource.getProductListByQuery(query)

            //Then
            val expectedContent = Json.decodeFromString<ProductResultQuery>(productListResponse)
            val resultExpected = MeliResult.Success(expectedContent)
            assertEquals(result, resultExpected)
        }

    @Test
    fun `Remote data source should return a MeliResult Error because was an error in the search`() =
        runBlocking {
            //Given
            val query = "juegos"
            val networkDataSource = NetworkProductDataSource(fakeProductApiService)
            fakeProductApiService.getProductListByQueryResponse = Response.error(
                404,
                fakeErrorProductListBody.toResponseBody("application/json".toMediaType())
            )

            //When
            val result = networkDataSource.getProductListByQuery(query)

            //Then
            val resultExpected = MeliResult.Error(fakeErrorProductListBody)
            assertEquals(result, resultExpected)
        }

    @Test
    fun `Remote data source should return a MeliResult Success with the product`() = runBlocking {
        //Given
        val productId = "MCO534830668"
        val networkDataSource = NetworkProductDataSource(fakeProductApiService)
        fakeProductApiService.getProductByIdResponse =
            Response.success(convertFromJson(productResponse))

        //When
        val result = networkDataSource.getProductById(productId)

        //Then
        val expectedContent = Json.decodeFromString<Product>(productResponse)
        val resultExpected = MeliResult.Success(expectedContent)
        assertEquals(result, resultExpected)
    }

    @Test
    fun `Remote data source should return a MeliResult Error because did not find the Id`() =
        runBlocking {
            //Given
            val productId = "MCO534830668"
            val networkDataSource = NetworkProductDataSource(fakeProductApiService)
            fakeProductApiService.getProductByIdResponse =
                Response.error(
                    404,
                    fakeErrorProductBody.toResponseBody("application/json".toMediaType())
                )

            //When
            val result = networkDataSource.getProductById(productId)

            //Then
            val expectedResult = MeliResult.Error(fakeErrorProductBody)
            assertEquals(result, expectedResult)
        }

    private inline fun <reified API> convertFromJson(jsonResponse: String): API =
        Json.decodeFromString(jsonResponse)

}