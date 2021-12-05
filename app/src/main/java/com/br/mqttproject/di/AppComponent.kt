package com.br.mqttproject.di

import com.br.data.di.DataModules.data
import com.br.domain.di.DomainModules.domain
import com.br.mqttproject.di.AppModule.presentation
import org.koin.core.module.Module

object AppComponent {

    fun getAllModules(): List<Module> =
        listOf(
            *getAppModules(),
            *getDataModules(),
            *getDomainModules()
        )

    private fun getAppModules(): Array<Module> {
        return arrayOf(presentation)
    }

    private fun getDataModules(): Array<Module> {
        return arrayOf(data)
    }

    private fun getDomainModules(): Array<Module> {
        return arrayOf(domain)
    }
}