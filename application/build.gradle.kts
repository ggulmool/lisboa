apply(plugin= "kotlin-jpa")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // H2DB
    testRuntimeOnly("com.h2database:h2")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
    testImplementation(project(":bootstrap:lisboa-web"))
    testImplementation(project(":adapters:lisboa-out-collector"))
    testImplementation(project(":adapters:lisboa-out-persistence"))
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}