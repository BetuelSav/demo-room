package com.example.demo_room.application

import androidx.room.Room
import com.example.demo_room.data.local.Database
import com.example.demo_room.data.local.repository.NoteRepositoryImplementation
import com.example.demo_room.domain.repository.NoteRepository
import com.example.demo_room.domain.use_case.AddNoteUseCase
import com.example.demo_room.domain.use_case.DeleteNoteUseCase
import com.example.demo_room.domain.use_case.GetAllNotesUseCase
import com.example.demo_room.domain.use_case.GetNoteByIdUseCase
import com.example.demo_room.presentation.feature_note.add_edit_note.AddEditNoteViewModel
import com.example.demo_room.presentation.feature_note.notes.NotesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppModules {

    private val viewModels = module {
        single { NotesViewModel(get(), get(), get()) }
        single { AddEditNoteViewModel(get(), get()) }
    }

    private val useCases = module {
        single { AddNoteUseCase(get()) }
        single { DeleteNoteUseCase(get()) }
        single { GetAllNotesUseCase(get()) }
        single { GetNoteByIdUseCase(get()) }
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