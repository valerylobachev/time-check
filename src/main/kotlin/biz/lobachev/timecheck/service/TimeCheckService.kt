package biz.lobachev.timecheck.service

import biz.lobachev.timecheck.controller.TimePayload
import biz.lobachev.timecheck.repository.TimeCheckRepository
import biz.lobachev.timecheck.repository.TimeRecord
import org.springframework.stereotype.Service

@Service
class TimeCheckService(private val repository: TimeCheckRepository) {
    fun save(payload: TimePayload): TimePayload {
        val record = TimeRecord(
            payload.id,
            payload.instantDt,
            payload.offsetDt,
            payload.localTime,
            payload.offsetTime,
        )
        repository.save(record)
        val r = repository.getReferenceById(payload.id)
        val res = TimePayload(
            r.id,
            r.instantDt,
            r.offsetDt,
            r.localTime,
            r.offsetTime
        )
        return res

    }
}
