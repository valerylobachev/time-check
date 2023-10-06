package biz.lobachev.timecheck.controller

import java.io.Serializable
import java.time.Instant
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime

/**
 * DTO for {@link biz.lobachev.timecheck.repository.TimeRecord}
 */
data class TimePayload(
    val id: String,
    val instantDt: Instant,
    val offsetDt: OffsetDateTime,
    val localTime: LocalTime,
    val offsetTime: OffsetTime
) : Serializable
