package com.raffa.repository

import com.raffa.domain.Product
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ProductRepository: JpaRepository<Product, Long>{
    fun findByNameIgnoreCase(name: String): Product?
}