package com.example.truecallerassignmentapplication.domain.exception

/**
 * Exception throw by the application when a there is a network connection exception.
 */
class NotFoundException : ModelException {

    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}