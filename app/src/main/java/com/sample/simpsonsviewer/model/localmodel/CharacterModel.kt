package com.sample.simpsonsviewer.model.localmodel

import com.google.gson.Gson
import com.sample.simpsonsviewer.model.apimodel.Icon
import com.sample.simpsonsviewer.model.local.entities.CharacterEntity

data class CharacterModel (
    val firstURL: String,
    val icon: Icon,
    val name: String,
    val text: String,
)

fun List<CharacterEntity>.mapFromEntityToCharacter(): List<CharacterModel> =

    this.map {
        val iconGson = Gson().fromJson(it.icon, Icon::class.java)
        CharacterModel(
            firstURL = it.firstURL,
            icon = iconGson,
            name = it.name,
            text = it.text
        )
    }