package com.maan.expert.widget

class ContentBin<ITEM> {
     var items: List<ITEM>?=null
     var item: ITEM?=null

    constructor(item:ITEM){
        this.item=item
    }
    constructor(items:List<ITEM>){
        this.items=items
    }
}