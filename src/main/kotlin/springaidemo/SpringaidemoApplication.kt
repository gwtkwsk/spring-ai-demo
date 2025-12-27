package springaidemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringaidemoApplication

fun main(args: Array<String>) {
	runApplication<SpringaidemoApplication>(*args)
}
