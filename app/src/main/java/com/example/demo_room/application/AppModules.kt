package com.example.demo_room.application

import org.koin.dsl.module

object AppModules {

    private val viewModels = module{

    }

    private val useCases = module {

    }

    private val database = module {

    }

    private val daos = module {

    }

    val modules = listOf(viewModels, useCases, database, daos)
}