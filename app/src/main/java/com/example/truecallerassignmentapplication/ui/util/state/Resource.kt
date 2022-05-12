package com.example.truecallerassignmentapplication.ui.util.state


/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
</T> */
sealed class Resource<T>(val status: Status, val data: T?, val message: String?) {
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val resource = o as Resource<*>
        if (status !== resource.status) {
            return false
        }
        if (if (message != null) message != resource.message else resource.message != null) {
            return false
        }
        return if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{" +
                "status=" + status.toString() +
                ", message='" + message + '\''.toString() +
                ", data=" + data +
                '}'
    }

    class Success<T>(data: T?) : Resource<T>(Status.SUCCESS, data, null)

    class Error<T>(msg: String?, data: T?) : Resource<T>(Status.ERROR, data, msg)

    class Loading<T>(data: T?) : Resource<T>(Status.LOADING, data, null)


}