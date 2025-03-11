package it.mulders.talks.trainingadmin

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

@Configuration
@EnableJdbcRepositories
class TrainingAdminConfiguration {
    @Bean
    fun dataSource() = EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build()

    @Bean
    fun transactionManager(dataSource: DataSource) = DataSourceTransactionManager(dataSource)

    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource) = NamedParameterJdbcTemplate(dataSource)
}