package me.adkhambek.taxi.utils


public sealed interface Try<out T> {
    public data class Success<T>(val data: T) : Try<T>
    public data class Failure(val failure: FailureMessage) : Try<Nothing>
}

public sealed interface FailureMessage {
    public val message: Text

    public data class UnknownException(override val message: Text) : FailureMessage
    public data class ServerException(override val message: Text) : FailureMessage
}