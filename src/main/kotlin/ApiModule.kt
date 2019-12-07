import org.koin.dsl.module

val apiModule = module {
    single<Server> { Server() }
}