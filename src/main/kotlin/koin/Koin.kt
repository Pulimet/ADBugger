package koin

import adb.Adb
import adb.Cmd
import adb.ProcessCreation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import store.AppStore

fun appModule() = listOf(mainKoinModule)

val mainKoinModule = module {
    singleOf(::ProcessCreation)
    singleOf(::Cmd)
    singleOf(::Adb)
    singleOf(::AppStore)

    single { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

}

