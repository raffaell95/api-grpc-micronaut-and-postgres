package com.raffa.util

import com.raffa.ProductServiceRequest
import com.raffa.ProductServiceUpdateRequest
import com.raffa.exceptions.InvalidArgumentException

class ValidationUtil {

    companion object{

        fun validatePayload(payload: ProductServiceRequest?): ProductServiceRequest{
            payload?.let {
                if(it.name.isNullOrBlank())
                    throw InvalidArgumentException("nome")

                if(it.price.isNaN() || it.price < 0)
                    throw InvalidArgumentException("preço")

                return it
            }
            throw InvalidArgumentException("payload")
        }

        fun validateUpdatePayload(payload: ProductServiceUpdateRequest?): ProductServiceUpdateRequest{
            payload?.let {
                if (it.id <= 0L)
                    throw InvalidArgumentException("ID")

                if(it.name.isNullOrBlank())
                    throw InvalidArgumentException("nome")

                if(it.price.isNaN() || it.price < 0)
                    throw InvalidArgumentException("preço")

                return it
            }
            throw InvalidArgumentException("payload")
        }

    }
}