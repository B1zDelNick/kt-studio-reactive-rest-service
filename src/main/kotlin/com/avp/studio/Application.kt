package com.avp.studio

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.ipc.netty.http.server.HttpServer


@SpringBootApplication
class Application(val router: RouterFunction<ServerResponse>) : CommandLineRunner {

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

    SpringApplication.run(Application::class.java, *args)

    /*tryWithResource(SpringApplication.run(Application::class.java, *args)) {

        //it.getBean(NettyContext::class.java).onClose().block()
    }*/

}
