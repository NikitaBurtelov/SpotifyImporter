package org.spotify.importer

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan("bmstu.bd.lab.lab_01.model")

class Lab01Application

fun main(args: Array<String>) {
    val context = runApplication<Lab01Application>(*args)

    val log = KotlinLogging.logger {}
}