package com.kc.mvvmfoodrecipeapp.data.network

import com.kc.mvvmfoodrecipeapp.data.model.RecipeItem
import com.kc.mvvmfoodrecipeapp.data.util.DomainMapper


class RecipeDtoMapper: DomainMapper<RecipeDto, RecipeItem> {
    override fun mapToDomainModel(model: RecipeDto): RecipeItem {
        return RecipeItem(
            id = model.pk,
            title = model.title,
            featuredImage = model.featured_image,
            rating = model.rating,
            publisher = model.publisher,
            sourceUrl = model.source_url,
            description = model.description,
            cookingInstructions = model.cooking_instructions,
            ingredients = model.ingredients?: listOf(),
            dateAdded = model.date_added,
            dateUpdated = model.date_updated
        )
    }

    override fun mapFromDomainModel(domainModel: RecipeItem): RecipeDto {
        return RecipeDto(
            pk = domainModel.id,
            title = domainModel.title,
            featured_image = domainModel.featuredImage,
            rating = domainModel.rating,
            publisher = domainModel.publisher,
            source_url = domainModel.sourceUrl,
            description = domainModel.description,
            cooking_instructions = domainModel.cookingInstructions,
            ingredients = domainModel.ingredients,
            date_added = domainModel.dateAdded,
            date_updated = domainModel.dateUpdated
        )
    }

    fun toDomainList(initial: List<RecipeDto>): List<RecipeItem>{
        return initial.map {
            mapToDomainModel(it)
        }
    }

    fun fromDomainList(initial: List<RecipeItem>): List<RecipeDto> {
        return initial.map {
            mapFromDomainModel(it)
        }
    }
}