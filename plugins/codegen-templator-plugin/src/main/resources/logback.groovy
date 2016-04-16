import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender;

import static ch.qos.logback.classic.Level.*

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "codegen-templator-plugin> %msg%n"
    }
}


appender("FILE", FileAppender) {
  file = "logs/codegen-log.out"
  append = true
  encoder(PatternLayoutEncoder) {
    pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  }
}

// logger("com.javacodegeeks.examples.logbackexample.beans", INFO)

root(INFO, ['CONSOLE', "FILE"])