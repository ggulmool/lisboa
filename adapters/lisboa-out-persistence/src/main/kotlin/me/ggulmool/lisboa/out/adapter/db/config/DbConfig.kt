package me.ggulmool.lisboa.out.adapter.db.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = ["me.ggulmool.lisboa.out.adapter.db.entity"])
@EntityScan(basePackages = ["me.ggulmool.lisboa.out.adapter.db.entity"])
class DbConfig