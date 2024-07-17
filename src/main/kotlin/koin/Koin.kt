package koin

import adb.Cmd
import org.koin.dsl.module
import store.AppStore

fun appModule() = listOf(mainKoinModule)

val mainKoinModule = module {
    single { Cmd() }
    single { AppStore(get()) }
}
