package com.example.truecallerassignmentapplication.domain.exception



class UnauthorizedException : ModelException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}