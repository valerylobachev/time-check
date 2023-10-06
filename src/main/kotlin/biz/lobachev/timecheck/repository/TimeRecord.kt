package biz.lobachev.timecheck.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.Instant
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime

@Entity(name = "times")
data class TimeRecord(
    @Id val id: String,
    val instantDt: Instant,
    val offsetDt: OffsetDateTime,
    val localTime: LocalTime,
    val offsetTime: OffsetTime
)
