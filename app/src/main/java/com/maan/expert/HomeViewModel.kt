package com.maan.expert

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maan.expert.model.CargoBox
import com.maan.expert.model.Category
import com.maan.expert.model.Product
import com.maan.expert.widget.ContentBin
import com.maan.expert.widget.UiState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val _lstOfCargos=MutableLiveData<List<CargoBox>>()
    fun getCargos():LiveData<List<CargoBox>> =_lstOfCargos

    private val _uiState=MutableLiveData<UiState<CargoBox>>()
    fun uiState():LiveData<UiState<CargoBox>> =_uiState

    var counter=0  // test --> Error layout

    private suspend fun fetchCategories()=flow {
            val lstOfCategory = mutableListOf<Category>()
            lstOfCategory.add(Category(1,"Apple","The description is"))
            lstOfCategory.add(Category(2,"Banana","The description is"))
            lstOfCategory.add(Category(3,"Carrot","The description is"))
            lstOfCategory.add(Category(4,"Decot","The description is"))
            lstOfCategory.add(Category(5,"Elliot","The description is"))
            lstOfCategory.add(Category(6,"Fanda","The description is"))
            lstOfCategory.add(Category(7,"Ginia","The description is"))
            lstOfCategory.add(Category(8,"Hilla","The description is"))
            lstOfCategory.add(Category(9,"Ikhone","The description is"))
            lstOfCategory.add(Category(10,"Jumgle","The description is"))
            delay(3000)

           if(counter++ == 0)throw Exception("Something went wrong") // test --> Error layout

            emit(lstOfCategory)
    }

    private suspend fun fetchProducts()=flow{
            val lstOfProduct = mutableListOf<Product>()
            lstOfProduct.add(Product(1,"ASugar","asdsdsds",25f,false))
            lstOfProduct.add(Product(2,"BTea","asdsdsds",215f,false))
            lstOfProduct.add(Product(3,"Coffee","asdsdsds",225f,false))
            lstOfProduct.add(Product(4,"Donut","asdsdsds",235f,false))
            lstOfProduct.add(Product(5,"EPea","asdsdsds",255f,false))
            lstOfProduct.add(Product(6,"FLassi","asdsdsds",235f,false))
            lstOfProduct.add(Product(7,"GChona","asdsdsds",225f,false))
            lstOfProduct.add(Product(8,"HMilk","asdsdsds",215f,false))
            lstOfProduct.add(Product(9,"IChona","asdsdsds",225f,false))
            lstOfProduct.add(Product(10,"JMilk","asdsdsds",215f,false))
            lstOfProduct.add(Product(11,"KChona","asdsdsds",225f,false))
            lstOfProduct.add(Product(12,"LMilk","asdsdsds",215f,false))
            delay(5000)
            emit(lstOfProduct)
    }



    fun fetchCargo() {

        viewModelScope.launch {
            fetchCategories().zip(fetchProducts()){ lstOfCategory,lstOfProduct ->
                val lstOfMappedCategory =CargoBox.CategoryBox.create("Categories",lstOfCategory)
                val lstOfMappedProduct=CargoBox.ProductBox.create(lstOfProduct)
                CargoBox.merge(lstOfMappedCategory,lstOfMappedProduct)
            }.onStart {
               _uiState.value=UiState.Progress
            }.catch {
                _uiState.value=UiState.Error("Something went wrong",action = true)
            }.collect {
                _uiState.value=UiState.Content(ContentBin(it))
            }
        }
    }

    fun mergeNewList() {
        viewModelScope.launch {
            fetchMoreProducts()
                .map { lstOfProduct ->
                  CargoBox.ProductBox.create(lstOfProduct)
                }
                .collect { newList ->
                    _lstOfCargos.value= _lstOfCargos.value?.plus(newList)
                }
        }
    }

    private suspend fun fetchMoreProducts()=flow{
        val lstOfProduct = mutableListOf<Product>()
        lstOfProduct.add(Product(13,"MSugar","asdsdsds",25f,false))
        lstOfProduct.add(Product(14,"NTea","asdsdsds",215f,false))
        lstOfProduct.add(Product(15,"Ooffee","asdsdsds",225f,false))
        lstOfProduct.add(Product(16,"Ponut","asdsdsds",235f,false))
        lstOfProduct.add(Product(17,"QPea","asdsdsds",255f,false))
        delay(1000)
        emit(lstOfProduct)
    }

    fun updateRandomItem(pos: Int) {
        val currentList: MutableList<CargoBox> = (_lstOfCargos.value?: emptyList()) as MutableList<CargoBox>
        val newList=mutableListOf<CargoBox>()
        newList.addAll(currentList)

        val itemToBeUpdated: CargoBox =currentList[pos]
        if(itemToBeUpdated is CargoBox.ProductBox){
            val newItem: Product =itemToBeUpdated.product.copy(name = "--Updated")
            newList.remove(itemToBeUpdated)
            newList.add(pos,CargoBox.ProductBox(newItem))
            _lstOfCargos.value=newList
        }

    }
}