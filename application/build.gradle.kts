apply(plugin= "kotlin-jpa")

dependencies {
//    api(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // H2DB
    runtimeOnly("com.h2database:h2")
}