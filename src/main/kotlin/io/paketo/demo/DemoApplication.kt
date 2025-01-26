package io.paketo.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class DemoApplication

@RestController
@RequestMapping("/")
class IndexController {

	@GetMapping
	fun sayHello(): String {
		return "hello!"
	}
}

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
