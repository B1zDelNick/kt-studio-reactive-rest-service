package com.avp.studio

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class Application() : CommandLineRunner {

    override fun run(vararg p0: String?) {

        /*val handler = RouterFunctions.toHttpHandler(router)
        val httpServer = HttpServer.create(8888)

        val handlerAdapter = ReactorHttpHandlerAdapter(handler)

        httpServer.newHandler(handlerAdapter).block().onClose().block()*/
    }
}

inline fun <T:AutoCloseable,R> tryWithResource(closeable: T, block: (T) -> R): R {

    closeable.use { closeable ->
        return block(closeable)
    }
}

fun main(args: Array<String>) {

    //tryWithResource(SpringApplication.run(Application::class.java, *args))

    SpringApplication.run(Application::class.java, *args)
}
