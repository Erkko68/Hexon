package eric.bitria.hexon.src.utils

// Sorts a Triple using the natural order of its elements
fun <T : Comparable<T>> sortTriple(triple: Triple<T, T, T>): Triple<T, T, T> {
    val sorted = listOf(triple.first, triple.second, triple.third).sorted()
    return Triple(sorted[0], sorted[1], sorted[2])
}

// Sorts a Pair using the natural order of its elements
fun <T : Comparable<T>> sortPair(pair: Pair<T, T>): Pair<T, T> {
    return if (pair.first <= pair.second) pair else Pair(pair.second, pair.first)
}