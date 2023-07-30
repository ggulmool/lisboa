apply(plugin= "kotlin-jpa")

dependencies {
    implementation(project(":application"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // H2DB
    runtimeOnly("com.h2database:h2")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
}