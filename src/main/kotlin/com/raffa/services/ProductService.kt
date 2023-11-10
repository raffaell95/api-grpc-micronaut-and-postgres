package com.raffa.services

import com.raffa.dto.ProductReq
import com.raffa.dto.ProductRes
import com.raffa.dto.ProductUpdateReq

interface ProductService {
    fun create(req: ProductReq): ProductRes
    fun findById(id: Long): ProductRes
    fun update(req: ProductUpdateReq): ProductRes
    fun delete(id: Long)
    fun findAll(): List<ProductRes>
}