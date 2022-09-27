package com.example.demo_room.application

import androidx.room.Room
import com.example.demo_room.data.local.Database
import com.example.demo_room.data.local.repository.NoteRepositoryImplementation
import com.example.demo_room.domain.repository.NoteRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppModules {

    private val viewModels = module {

    }

    private val useCases = module {

    }

    private val localRepositoryModule = module {
        single<NoteRepository> { NoteRepositoryImplementation(get()) }
    }

    private val database = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                Database::class.java,
                Database.DATABASE_NAME
            ).build()
        }
    }

    private val daos = module {
        single { get<Database>().noteDao() }
    }

    val modules = listOf(viewModels, useCases, localRepositoryModule, database, daos)
}