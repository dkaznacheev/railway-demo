package io.paketo.demo

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@SpringBootApplication
class DemoApplication

@Repository
interface AccessLogRepository : JpaRepository<AccessLog, Long> {
	@Query("SELECT COUNT(a) FROM AccessLog a")
	fun getTotalRowCount(): Long

	@Query("SELECT MAX(a.access_time) FROM AccessLog a")
	fun getLatestAccessTime(): LocalDateTime?
}

@Entity
@Table(name = "accesslog")
data class AccessLog(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	@Column(name = "access_time")
	val accessTime: LocalDateTime,
)

@RestController
@RequestMapping("/")
class IndexController(private val accessLogRepository: AccessLogRepository) {

	@GetMapping
	@Suppress("UNUSED")
	fun sayHello(): String {
		val accessLog = AccessLog(
			accessTime = LocalDateTime.now() // Set the current time as access time
		)

		val totalCount = accessLogRepository.getTotalRowCount()
		val latestAccessTime = accessLogRepository.getLatestAccessTime()

		accessLogRepository.save(accessLog)

		return "Hi! You are the visitor number $totalCount. Last access was at $latestAccessTime."
	}
}

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
