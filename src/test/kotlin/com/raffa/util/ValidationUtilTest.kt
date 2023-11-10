package com.raffa.util

import com.raffa.ProductServiceRequest
import com.raffa.ProductServiceUpdateRequest
import com.raffa.exceptions.InvalidArgumentException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ValidationUtilTest {

    @Test
    fun `when validatePayload method is call with valid data, should not throw exception`(){
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        Assertions.assertDoesNotThrow{
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validateUpdatePayload method is call with valid data, should not throw exception`(){
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("product name")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        Assertions.assertDoesNotThrow{
            ValidationUtil.validateUpdatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with invalid product name, should throw exception`(){
        val request = ProductServiceRequest.newBuilder()
            .setName("")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        Assertions.assertThrowsExactly(InvalidArgumentException::class.java){
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validateUpdatePayload method is call with invalid product name, should throw exception`(){
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        Assertions.assertThrowsExactly(InvalidArgumentException::class.java){
            ValidationUtil.validateUpdatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with invalid price, should throw exception`(){
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(-20.99)
            .setQuantityInStock(10)
            .build()

        Assertions.assertThrowsExactly(InvalidArgumentException::class.java){
            ValidationUtil.validatePayload(request)
        }
    }

    @Test
    fun `when validateUpdatePayload method is call with invalid price, should throw exception`(){
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("product name")
            .setPrice(-20.99)
            .setQuantityInStock(10)
            .build()

        Assertions.assertThrowsExactly(InvalidArgumentException::class.java){
            ValidationUtil.validateUpdatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is call with null payload, should throw exception`(){
        Assertions.assertThrowsExactly(InvalidArgumentException::class.java){
            ValidationUtil.validatePayload(null)
        }
    }

    @Test
    fun `when validateUpdatePayload method is call with null payload, should throw exception`(){
        Assertions.assertThrowsExactly(InvalidArgumentException::class.java){
            ValidationUtil.validateUpdatePayload(null)
        }
    }

}