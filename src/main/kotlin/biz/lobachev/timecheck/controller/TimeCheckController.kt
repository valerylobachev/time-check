package biz.lobachev.timecheck.controller

import biz.lobachev.timecheck.service.TimeCheckService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TimeCheckController(private val service: TimeCheckService) {
    @PostMapping("/save")
    fun save(@RequestBody payload: TimePayload): TimePayload {
        return service.save(payload)
    }
}
