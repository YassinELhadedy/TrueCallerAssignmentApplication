package com.example.truecallerassignmentapplication.domain

import kotlinx.coroutines.flow.Flow

/**
 * AbstractProxyGetRepository
 */
abstract class AbstractProxyGetRepository<T, U>(private val cacheRepository: Repository<T, U>,
                                                private val getRepository: GetRepository<T, U>
) : GetRepository<T, U> {

    protected abstract fun convert(entity: U): T

    override fun get(apiComponent: T): Flow<U> {
        TODO("Not yet implemented")
    }



//    override fun get(id: T): Flow< U> {
////        val cached = cacheRepository.get(id).cache()
////        val service by lazy {
////            getRepository.get(id).flatMap { entity: U ->
////                cacheRepository.insertOrUpdate(convert(entity))
////                        .map { _ -> entity }
////                        .onErrorReturn { _ -> entity }
////            }.cache()
////        }
////        return cached.isEmpty.toObservable()
////                .flatMap {
////                    if (it) {
////                        service
////                    } else {
////                        cached
////                    }
////                }
////                .onErrorResumeNext { _: Throwable ->
////                    service
////                }
//        TODO()
//    }

}