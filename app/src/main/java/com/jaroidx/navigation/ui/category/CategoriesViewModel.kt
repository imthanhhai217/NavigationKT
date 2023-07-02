package com.jaroidx.navigation.ui.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jaroidx.navigation.api.`object`.BaseResponse
import com.jaroidx.navigation.models.CategoriesResponse
import com.jaroidx.navigation.models.Product
import com.jaroidx.navigation.repositories.CategoriesRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CategoriesViewModel(
    private val categoriesRepository: CategoriesRepository, application: Application
) : AndroidViewModel(application) {
    private val database = Firebase.database
    val categoriesResult: MutableLiveData<BaseResponse<CategoriesResponse>> = MutableLiveData()
    private var categoriesResponse: CategoriesResponse? = null

    fun saveMessage(message: String) {
        val myRef = database.getReference("message")
        myRef.setValue(message)
    }

    init {
        loadCategoriesFromFirebase()
        getAllCategories()
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            categoriesResult.postValue(BaseResponse.Loading())
            val result = categoriesRepository.getAllCategories()
            categoriesResult.postValue(handleCategoriesResponse(result))
        }
    }

    private fun loadCategoriesFromFirebase() {
        val myRef = database.getReference("categories")
        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children){
                    Log.d("TAG", "onDataChange: ${i.getValue(String::class.java)}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun handleCategoriesResponse(response: Response<CategoriesResponse>): BaseResponse<CategoriesResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                categoriesResponse = it
                return BaseResponse.Success(it)
            }
        }
        return BaseResponse.Error(null, "Load categories failed")
    }

    fun saveCategory(categories: List<String>) {
        val myRef = database.getReference("categories")
        myRef.setValue(categories)
    }

    fun saveProduct(i: Product) {
        val myRef = database.getReference("wish_products")
        myRef.push().setValue(i).addOnSuccessListener {
            if (it != null && (it as Task<Void>).isSuccessful) {
                Log.d("TAG", "saveProduct: addSuccess")
            }
        }.addOnFailureListener {
            Log.d("TAG", "saveProduct: ${it.message}")
        }
    }

    fun getProducts() {
        val myRef = database.getReference("wish_products")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val product = i.getValue(Product::class.java)
//                    Log.d("TAG", "onDataChange: ${product.toString()}")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        myRef.addChildEventListener(wishProductChildEventListener)
    }

    private val wishProductChildEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val product = snapshot.getValue(Product::class.java)
            Log.d("TAG", "onChildAdded: ${product.toString()}")
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }
}