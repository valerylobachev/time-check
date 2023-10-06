package biz.lobachev.timecheck.repository;

import biz.lobachev.timecheck.repository.TimeRecord
import org.springframework.data.jpa.repository.JpaRepository

interface TimeCheckRepository : JpaRepository<TimeRecord, String> {
}
