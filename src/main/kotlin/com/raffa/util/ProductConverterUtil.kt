package com.raffa.util

import com.raffa.domain.Product
import com.raffa.dto.ProductReq
import com.raffa.dto.ProductRes

fun Product.toProductRes(): ProductRes{
    return ProductRes(
        id = id!!,
        name = name,
        price = price,
        quantityInStock = quantityInStock
    )
}

fun ProductReq.toDomain(): Product{
    return Product(
        id = null,
        name = name,
        price = price,
        quantityInStock = quantityInStock
    )
}