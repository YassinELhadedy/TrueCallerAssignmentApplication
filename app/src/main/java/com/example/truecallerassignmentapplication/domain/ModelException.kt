package com.example.truecallerassignmentapplication.domain

/**
 * ModelException
 */
open class ModelException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}