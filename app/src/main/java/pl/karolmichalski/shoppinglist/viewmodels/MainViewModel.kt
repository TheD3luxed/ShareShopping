package pl.karolmichalski.shoppinglist.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.google.firebase.database.FirebaseDatabase
import pl.karolmichalski.shoppinglist.data.ProductsRepository
import pl.karolmichalski.shoppinglist.models.Product

class MainViewModel(application: Application) : AndroidViewModel(application) {

	val productName = MutableLiveData<String>()

	val productList = MutableLiveData<List<Product>>().apply { value = ArrayList() }

	private val localDatabase = ProductsRepository(application)
	private val cloudDatabase = FirebaseDatabase.getInstance().reference

	fun getProducts(owner: LifecycleOwner) {
		localDatabase.getAll().observe(owner, Observer {
			productList.value = it
		})
	}

	fun addProduct() {
		productName.value?.let { name ->
			localDatabase.insert( Product(name))
		}
	}

	fun removeProduct(product: Product) {
		localDatabase.delete(product)
	}

	fun clearProductName() {
		productName.value = ""
	}
}