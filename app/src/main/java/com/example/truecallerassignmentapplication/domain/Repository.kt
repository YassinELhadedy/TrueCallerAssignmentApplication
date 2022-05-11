package com.example.truecallerassignmentapplication.domain

import com.fashhub.camxviewmlapplication.domain.WriteRepository


interface Repository<in T, out U> : ReadRepository<T, U>, WriteRepository<T, U> {

    // TODO: Uncomment if needed
    /*fun isCached(): Boolean*/

    // TODO: Uncomment if needed
    /*fun evict(): Observable<Unit>*/

}