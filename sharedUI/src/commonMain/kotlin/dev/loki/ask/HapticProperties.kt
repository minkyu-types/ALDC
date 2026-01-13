package dev.loki.ask

import androidx.compose.runtime.Immutable
import kotlin.time.Duration

/**
 * Platform-specific haptic tuning properties.
 *
 * iOS - `sharpness`, `envelope` will be mapped to CoreHaptics
 * Android - these properties may be ignored
 */
@ConsistentCopyVisibility
@Immutable
data class HapticProperties internal constructor(
    val sharpness: Float? = null,
    val envelope: Envelope? = null
) {
    data class Envelope(
        val attack: Duration? = null,
        val decay: Duration? = null,
        val release: Duration? = null,
    )

    companion object
}

// region
// Companion Factory Functions
fun HapticProperties.Companion.sharpness(value: Float): HapticProperties =
    HapticProperties(sharpness = value.coerceIn(0f, 1f))

fun HapticProperties.Companion.envelope(
    attack: Duration? = null,
    decay: Duration? = null,
    release: Duration? = null
): HapticProperties =
    HapticProperties(
        envelope = HapticProperties.Envelope(attack, decay, release)
    )
// endregion

// region
// Instance Builder Functions
fun HapticProperties.sharpness(value: Float): HapticProperties =
    copy(sharpness = value.coerceIn(0f, 1f))

fun HapticProperties.envelope(
    attack: Duration? = null,
    decay: Duration? = null,
    release: Duration? = null
): HapticProperties =
    copy(
        envelope = HapticProperties.Envelope(attack, decay, release)
    )
// endregion