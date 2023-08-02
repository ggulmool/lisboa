apply(plugin= "kotlin-jpa")

dependencies {
    implementation(project(":application"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // H2DB
    runtimeOnly("com.h2database:h2")
}