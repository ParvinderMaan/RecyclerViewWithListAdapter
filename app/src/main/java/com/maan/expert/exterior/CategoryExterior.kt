package com.maan.expert.exterior

import com.maan.expert.model.Category


data class CategoryExterior(var id:Int,var name:String,var desc:String,var link:String,
                            var createdOn:String)


fun CategoryExterior.toCategory():Category{
    return Category( id=this.id, name=this.name, desc=this.desc)
}