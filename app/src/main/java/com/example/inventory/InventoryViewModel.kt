package com.example.inventory

import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getAll().asLiveData()

    private fun insertItem(item: Item){
        viewModelScope.launch { itemDao.insert(item) }
    }

    private fun updateItem(item: Item){
        viewModelScope.launch { itemDao.update(item) }
    }

    fun deleteItem(item: Item){
        viewModelScope.launch { itemDao.delete(item) }
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        val item = itemDao.getItemById(id)
        return item.asLiveData()
    }

    fun sellItem(item: Item){
        if(item.quantity > 0){
            val tempItem = item.copy(quantity = item.quantity - 1)
            updateItem(tempItem)
        }
    }

    fun isItemAvailable(item: Item): Boolean {
        return item.quantity > 0
    }

    private fun getNewItemEntity(name: String, price: String, quantity: String) : Item {
        return Item(
            name = name,
            price = price.toDouble(),
            quantity = quantity.toInt()
        )
    }

    fun addNewItem(name: String, price: String, quantity: String){
        val item = getNewItemEntity(name, price, quantity)
        insertItem(item)
    }

    fun isEntryValid(name: String, price: String, quantity: String): Boolean {
        return (name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank())
    }
}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}