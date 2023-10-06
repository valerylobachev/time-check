package biz.lobachev.timecheck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TimeCheckApplication

fun main(args: Array<String>) {
    runApplication<TimeCheckApplication>(*args)
}
