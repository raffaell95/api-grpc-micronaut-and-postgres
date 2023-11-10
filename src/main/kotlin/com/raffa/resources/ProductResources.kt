package com.raffa.resources


import com.raffa.*
import com.raffa.dto.ProductReq
import com.raffa.dto.ProductUpdateReq
import com.raffa.exceptions.BaseBusinessException
import com.raffa.services.ProductService
import com.raffa.util.ValidationUtil
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources(
    private val productService: ProductService
): ProductsServiceGrpc.ProductsServiceImplBase() {
    override fun create(request: ProductServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        val payload = ValidationUtil.validatePayload(request)
        val productReq = ProductReq(name = payload.name, price = payload.price, quantityInStock = payload.quantityInStock)
        val productRes = productService.create(productReq)

        val productServiceResponse = ProductServiceResponse.newBuilder()
            .setId(productRes.id)
            .setName(productRes.name)
            .setPrice(productRes.price)
            .setQuantityInStock(productRes.quantityInStock)
            .build()

        responseObserver?.onNext(productServiceResponse)
        responseObserver?.onCompleted()

    }

    override fun findById(request: RequestById?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        val productRes = productService.findById(request!!.id)

        val productServiceResponse = ProductServiceResponse.newBuilder()
            .setId(productRes.id)
            .setName(productRes.name)
            .setPrice(productRes.price)
            .setQuantityInStock(productRes.quantityInStock)
            .build()

        responseObserver?.onNext(productServiceResponse)
        responseObserver?.onCompleted()
    }

    override fun update(
        request: ProductServiceUpdateRequest?,
        responseObserver: StreamObserver<ProductServiceResponse>?
    ) {

        val payload = ValidationUtil.validateUpdatePayload(request)

        val productReq = ProductUpdateReq(
            id = payload.id,
            name = payload.name,
            price = payload.price,
            quantityInStock = payload.quantityInStock
        )

        val productRes = productService.update(productReq)

        val productServiceResponse = ProductServiceResponse.newBuilder()
            .setId(productRes.id)
            .setName(productRes.name)
            .setPrice(productRes.price)
            .setQuantityInStock(productRes.quantityInStock)
            .build()

        responseObserver?.onNext(productServiceResponse)
        responseObserver?.onCompleted()
    }

    override fun delete(request: RequestById?, responseObserver: StreamObserver<Empty>?) {
        productService.delete(request!!.id)
        responseObserver?.onNext(Empty.newBuilder().build())
        responseObserver?.onCompleted()
    }

    override fun findAll(request: Empty?, responseObserver: StreamObserver<ProductsList>?) {
        val productResList = productService.findAll()

        val productServiceResponse = productResList.map {
            ProductServiceResponse.newBuilder()
                .setId(it.id)
                .setName(it.name)
                .setPrice(it.price)
                .setQuantityInStock(it.quantityInStock)
                .build()
        }

        val response = ProductsList.newBuilder().addAllProducts(productServiceResponse).build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

}