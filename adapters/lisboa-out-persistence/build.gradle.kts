apply(plugin= "kotlin-jpa")

dependencies {
    compileOnly(project(":application"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // H2DB
    runtimeOnly("com.h2database:h2")

    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
}