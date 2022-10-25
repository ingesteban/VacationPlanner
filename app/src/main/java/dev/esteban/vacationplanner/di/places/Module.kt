package dev.esteban.vacationplanner.di.places

import dev.esteban.places.data.datasource.local.db.PlaceRoomDatabase
import dev.esteban.places.data.datasource.local.PlaceLocalDataSource
import dev.esteban.places.domain.repository.PlaceRepository
import dev.esteban.places.domain.usecase.UpdatePlaceVisitedUseCase
import dev.esteban.places.domain.usecase.VacationPlacesUseCase
import dev.esteban.places.domain.usecase.DeleteVacationPlaceUseCase
import dev.esteban.places.data.repository.PlaceRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun injectPlacesModule() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        arrayListOf(
            utilsModule,
            dataSourceModule,
            repositoryModule,
            useCaseModule,
            roomModule
        )
    )
}

val utilsModule: Module = module {
    single { Dispatchers.IO }
}

val useCaseModule: Module = module {
    factoryOf(::VacationPlacesUseCase)
    factoryOf(::UpdatePlaceVisitedUseCase)
    factoryOf(::DeleteVacationPlaceUseCase)
}

val repositoryModule: Module = module {
    singleOf(::PlaceRepositoryImpl) {
        bind<PlaceRepository>()
    }
}

val roomModule: Module = module {
    single { PlaceRoomDatabase.build(context = get()) }
    single { get<PlaceRoomDatabase>().placeDao() }
}

val dataSourceModule: Module = module {
    single { PlaceLocalDataSource(context = get()) }
}
