package me.ggulmool.lisboa.adapter.db.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = ["me.ggulmool.lisboa.adapter.db.entity"])
@EntityScan(basePackages = ["me.ggulmool.lisboa.adapter.db.entity"])
class DbConfig