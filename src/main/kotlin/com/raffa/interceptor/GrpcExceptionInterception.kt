package com.raffa.interceptor

import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import jakarta.inject.Singleton

@Singleton
class GrpcExceptionInterception: ServerInterceptor {
    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        call: ServerCall<ReqT, RespT>?,
        headers: Metadata?,
        next: ServerCallHandler<ReqT, RespT>?
    ): ServerCall.Listener<ReqT> {
        val listener = next!!.startCall(call, headers)
        return ExceptionHandleServerCallListener(
            call,
            headers,
            listener
        )
    }
}