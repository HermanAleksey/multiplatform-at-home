import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkCoreModule = module {
    single<HttpClient>(createdAtStart = true) { getDefaultHttpClient() }
}
