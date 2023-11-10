package com.raffa.services.impl

import com.raffa.domain.Product
import com.raffa.dto.ProductReq
import com.raffa.dto.ProductRes
import com.raffa.dto.ProductUpdateReq
import com.raffa.exceptions.AlreadyExistsException
import com.raffa.exceptions.ProductNotFoundException
import com.raffa.repository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.Optional


internal class ProductServiceImplTest {

    private val productRepository = Mockito.mock(ProductRepository::class.java)
    private val productService  = ProductServiceImpl(productRepository)

    @Test
    fun `when create method is call with valid data a ProductRes is returned`() {
        val productInput = Product(id = null, name = "product name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        Mockito.`when`(productRepository.save(productInput)).thenReturn(productOutput)

        val productReq = ProductReq(name = "product name", price = 10.00, quantityInStock = 5)
        val productRes = productService.create(productReq)

        Assertions.assertEquals(productRes.name, productReq.name)
    }

    @Test
    fun `when create method is call with duplicated product-name throws AlreadyExistsException`() {
        val productInput = Product(id = null, name = "product name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        Mockito.`when`(productRepository.findByNameIgnoreCase(productInput.name))
            .thenReturn(productOutput)

        val productReq = ProductReq(name = "product name", price = 10.00, quantityInStock = 5)

        Assertions.assertThrowsExactly(AlreadyExistsException::class.java){
            productService.create(productReq)
        }
    }

    @Test
    fun `when findById method is call with valid id a ProductRes is returned`(){
        val productInput = 1L
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        Mockito.`when`(productRepository.findById(productInput))
            .thenReturn(Optional.of(productOutput))

        val productRes = productService.findById(productInput)

        Assertions.assertEquals(productInput, productRes.id)
        Assertions.assertEquals(productOutput.name, productRes.name)
    }

    @Test
    fun `when findById method is call with invalid id, throws ProductNotFoundException`(){
        val id = 1L
        Assertions.assertThrowsExactly(ProductNotFoundException::class.java){
            productService.findById(id)
        }
    }

    @Test
    fun `when update method is call with duplicated product-name throws AlreadyExistsException`() {
        val productInput = Product(id = null, name = "product name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        Mockito.`when`(productRepository.findByNameIgnoreCase(productInput.name))
            .thenReturn(productOutput)

        val productReq = ProductUpdateReq(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        Assertions.assertThrowsExactly(AlreadyExistsException::class.java){
            productService.update(productReq)
        }
    }

    @Test
    fun `when update method is call with invalid id, throws ProductNotFoundException`(){
        val productReq = ProductUpdateReq(id = 1, name = "product name", price = 10.00, quantityInStock = 5)
        Assertions.assertThrowsExactly(ProductNotFoundException::class.java){
            productService.update(productReq)
        }
    }

    @Test
    fun `when update method is call with valid data a ProductRes is returned`() {
        val productInput = Product(id = 1, name = "updated product", price = 11.00, quantityInStock = 10)
        val findByIdOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        Mockito.`when`(productRepository.findById(productInput.id!!))
            .thenReturn(Optional.of(findByIdOutput))

        Mockito.`when`(productRepository.update(productInput))
            .thenReturn(productInput)

        val productReq = ProductUpdateReq(id = 1, name = "updated product", price = 11.00, quantityInStock = 10)
        val productRes = productService.update(productReq)

        Assertions.assertEquals(productRes.name, productReq.name)
    }

    @Test
    fun `when delete method is call with valid id a ProductRes is deleted`() {
        val id = 1L
        val productInput = Product(id = null, name = "product name", price = 10.00, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)

        Mockito.`when`(productRepository.findById(id))
            .thenReturn(Optional.of(productOutput))

        Assertions.assertDoesNotThrow { productService.delete(id) }
    }

    @Test
    fun `when delete method is call with invalid id throws ProductNotFoundException`() {
        val id = 1L

        Mockito.`when`(productRepository.findById(id))
            .thenReturn(Optional.empty())

        Assertions.assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.delete(id)
        }
    }

    @Test
    fun `when findAll method is call a list of ProductRes is returned`() {
        val productsList = listOf(
            Product(id = 1, name = "product name", price = 10.00, quantityInStock = 5)
        )

        Mockito.`when`(productRepository.findAll())
            .thenReturn(productsList)

        val productRes = productService.findAll()

        Assertions.assertEquals(productsList[0].name, productRes[0].name)
    }

    @Test
    fun `when findAll method is call without products a emptyList of ProductRes is returned`() {
        val productsList = emptyList<ProductRes>()

        Mockito.`when`(productRepository.findAll())
            .thenReturn(emptyList())

        val productRes = productService.findAll()

        Assertions.assertEquals(productsList.size, productRes.size)
    }
}