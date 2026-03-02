@file:Suppress("NOTHING_TO_INLINE")

package justmc

inline fun vector(x: Double, y: Double, z: Double): Vector = Vector.of(x, y, z)
inline fun location(x: Double, y: Double, z: Double): Location = Location.of(x, y, z)
inline fun location(x: Double, y: Double, z: Double, yaw: Double, pitch: Double): Location = Location.of(x, y, z, yaw, pitch)

inline infix fun <A, B> A.to(other: B): Pair<A, B> = Pair.of(this, other)
inline operator fun <A, B> Pair<A, B>.component1(): A = this.first
inline operator fun <A, B> Pair<A, B>.component2(): B = this.second
