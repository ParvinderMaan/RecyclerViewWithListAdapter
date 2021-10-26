package com.maan.expert.model

sealed class CargoBox{

      data class CategoryBox(var title:String,var lstOfCategory: List<Category>):CargoBox(),IndexSelection{
            companion object {
                  fun create(headerTitle:String,lstOfCategory: List<Category>): List<CategoryBox> {
                        val categoryBox= CategoryBox(headerTitle,lstOfCategory)
                        val lstOfCatBox= mutableListOf<CategoryBox>()
                        lstOfCatBox.add(categoryBox)
                        return lstOfCatBox.toList()
                  }
            }

            override var selectedIndex: Int=-1
      }
      class ProductBox(var product: Product):CargoBox(){

            companion object {
                  fun create(lstOfProduct:List<Product>): List<ProductBox> {
                        val lstOfMappedProduct= mutableListOf<ProductBox>()
                        lstOfProduct.forEach { item ->
                              lstOfMappedProduct.add(ProductBox(item))
                        }
                        return lstOfMappedProduct.toList()
                  }
            }
      }


      companion object {
            fun merge(lstOfCategory: List<CategoryBox>, lstOfProduct: List<ProductBox>):  List<CargoBox> {
                  return lstOfCategory + lstOfProduct
            }
      }
}

interface IndexSelection{
      var selectedIndex: Int
}
