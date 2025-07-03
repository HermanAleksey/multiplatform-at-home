package com.justpoarokq.shared.core.base_database.api

import androidx.room.RoomDatabase
import com.justpoarokq.shared.core.base_database.dao.BooleanSettingDao
import com.justpoarokq.shared.core.base_database.dao.StringSettingDao
import com.justpoarokq.shared.core.base_database.database.SettingDatabase
import com.justpoarokq.shared.core.base_database.database.getSettingsDatabase
import com.justpoarokq.shared.core.base_database.database.getSettingsDatabaseBuilder
import com.justpoarokq.shared.core.base_database.mapper.SettingMapper
import com.justpoarokq.shared.core.base_database.repository.NetworkSettingsInteractorImpl
import com.justpoarokq.shared.core.base_database.repository.SettingsRepositoryImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val baseDatabaseModule = module {
    // Factory (creates new instance each time)
    single<RoomDatabase.Builder<SettingDatabase>> {
        getSettingsDatabaseBuilder(get(named("Context")))
    }
    single<SettingDatabase> {
        getSettingsDatabase(get())
    }
    factory<StringSettingDao> { get<SettingDatabase>().stringSettingDao() }
    factory<BooleanSettingDao> { get<SettingDatabase>().booleanSettingDao() }
    factoryOf(::SettingMapper)
    factory {
        SettingsRepositoryImpl(
            boolDao = get(), strDao = get(), mapper = get()
        )
    } bind SettingsRepository::class
    factory {
        NetworkSettingsInteractorImpl(settingsRepository = get())
    } bind NetworkSettingsInteractor::class
}