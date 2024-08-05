package koin

import terminal.Terminal
import terminal.Cmd
import terminal.CommandLauncher
import terminal.ProcessCreation
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
    singleOf(::CommandLauncher)
    singleOf(::Terminal)
    singleOf(::AppStore)

    single { CoroutineScope(Dispatchers.IO + SupervisorJob()) }

}

