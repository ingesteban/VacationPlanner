package dev.esteban.vacationplanner.di.app

import dev.esteban.vacationplanner.di.places.injectPlacesModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module
import dev.esteban.vacationplanner.viewmodel.VacationsViewModel
import dev.esteban.vacationplanner.viewmodel.VacationDetailViewModel
import org.koin.core.context.loadKoinModules

fun injectFeatures() {
    injectPlacesModule()
    injectViewModels()
}

fun injectViewModels() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        arrayListOf(
            viewModelModule,
        )
    )
}

val viewModelModule: Module = module {
    viewModelOf(::VacationsViewModel)
    viewModelOf(::VacationDetailViewModel)
}
