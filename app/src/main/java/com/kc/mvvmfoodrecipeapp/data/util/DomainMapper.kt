package com.kc.mvvmfoodrecipeapp.data.util

interface DomainMapper <T, DomainModel> {

    fun mapToDomainModel(model: T): DomainModel
    fun mapFromDomainModel(domainModel: DomainModel): T
}