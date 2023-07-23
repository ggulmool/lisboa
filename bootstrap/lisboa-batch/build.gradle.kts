dependencies {
    implementation(project(":application"))
    implementation(project(":adapters:lisboa-out-collector"))
    implementation(project(":adapters:lisboa-out-persistence"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}