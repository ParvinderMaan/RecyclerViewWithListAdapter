package com.maan.expert.widget

sealed class UiState<out ITEM> {

    object Progress:UiState<Nothing>()
    data class Error(var msg:String,var action:Boolean=true):UiState<Nothing>()
    data class Content<ITEM>(var content:ContentBin<ITEM>):UiState<ITEM>()
}


