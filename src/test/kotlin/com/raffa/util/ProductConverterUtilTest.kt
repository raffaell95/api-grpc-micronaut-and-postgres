package com.raffa.util

import com.raffa.domain.Product
import com.raffa.dto.ProductReq
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ProductConverterUtilTest {

    @Test
    fun `when toProductRes in call, should return a ProductRes with all data`(){
        val product = Product(id = 1, name = "product name", price = 10.90, quantityInStock = 10)
        val productRes = product.toProductRes()

        Assertions.assertEquals(product.id, productRes.id)
        Assertions.assertEquals(product.name, productRes.name)
        Assertions.assertEquals(product.price, productRes.price)
        Assertions.assertEquals(product.quantityInStock, productRes.quantityInStock)
    }

    @Test
    fun `when toDomain in call, should return a Product with all data`(){
        val productReq = ProductReq(name = "product name", price = 10.90, quantityInStock = 10)
        val product = productReq.toDomain()

        Assertions.assertEquals(null, product.id)
        Assertions.assertEquals(productReq.name, product.name)
        Assertions.assertEquals(productReq.price, product.price)
        Assertions.assertEquals(productReq.quantityInStock, product.quantityInStock)
    }
}