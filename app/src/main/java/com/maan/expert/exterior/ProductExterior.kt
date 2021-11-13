package com.maan.expert.exterior

import com.maan.expert.model.Product

data class ProductExterior(var id:Int,var name:String,var desc:String,var price:Float,
                           var isFav:Boolean,var nameOfManufacturer:String,
                           var placeOfManufacturer:String)


fun ProductExterior.toProduct(): Product {
    return Product(id=this.id, name=this.name, desc=this.desc, price=this.price, isFav=this.isFav)
}

//        val productExterior=ProductExterior(13,"MSugar","asdsdsds",25f,false,"Amit","Sangaria")
//        var product=productExterior.toProduct()
