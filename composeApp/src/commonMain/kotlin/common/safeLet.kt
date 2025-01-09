package common

inline fun <T1, R> safeLet(p1: T1?, block: (T1) -> R): R? {
    return if (p1 != null) {
        block(p1)
    } else {
        null
    }
}

inline fun <T1, T2, R> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R): R? {
    return if (p1 != null && p2 != null) {
        block(p1, p2)
    } else {
        null
    }
}

inline fun <T1, T2, T3, R> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3) -> R): R? {
    return if (p1 != null && p2 != null && p3 != null) {
        block(p1, p2, p3)
    } else {
        null
    }
}

suspend inline fun <T1, R> suspendedSafeLet(p1: T1?, block: suspend (T1) -> R): R? {
    return if (p1 != null) {
        block(p1)
    } else {
        null
    }
}

suspend inline fun <T1, T2, R> suspendedSafeLet(p1: T1?, p2: T2?, block: suspend (T1, T2) -> R): R? {
    return if (p1 != null && p2 != null) {
        block(p1, p2)
    } else {
        null
    }
}

suspend inline fun <T1, T2, T3, R> suspendedSafeLet(p1: T1?, p2: T2?, p3: T3?, block: suspend (T1, T2, T3) -> R): R? {
    return if (p1 != null && p2 != null && p3 != null) {
        block(p1, p2, p3)
    } else {
        null
    }
}
