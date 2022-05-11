package com.example.truecallerassignmentapplication.domain


interface ReadRepository<in T, out U> : GetRepository<T, U>, GetAllRepository<U>