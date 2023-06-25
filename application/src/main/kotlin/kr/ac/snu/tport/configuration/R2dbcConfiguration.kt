package kr.ac.snu.tport.configuration

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.core.io.ClassPathResource
import org.springframework.data.convert.CustomConversions
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.DialectResolver
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
class R2dbcConfiguration(
    private val connectionFactory: ConnectionFactory
) {
    @Bean
    fun customConversions(
        customConverters: List<Converter<*, *>>
    ): R2dbcCustomConversions {
        val dialect = DialectResolver.getDialect(connectionFactory)
        val converters = dialect.converters + R2dbcCustomConversions.STORE_CONVERTERS
        return R2dbcCustomConversions(
            CustomConversions.StoreConversions.of(dialect.simpleTypeHolder, converters),
            customConverters
        )
    }

    @Bean
    fun transactionManager() =
        R2dbcTransactionManager(connectionFactory)

    @Bean
    fun initializer(): ConnectionFactoryInitializer? {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        val populator = CompositeDatabasePopulator()
        populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
        initializer.setDatabasePopulator(populator)
        return initializer
    }
}
