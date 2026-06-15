package org.martin.rocomdex.ui.pet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.martin.rocomdex.data.DexRepository
import org.martin.rocomdex.data.NullPetWithFeature
import org.martin.rocomdex.data.NullPetWithFeatureAndSkills
import org.martin.rocomdex.data.PetDetailModel

class PetDetailViewModel(private val dexRepository: DexRepository, petId: Int) : ViewModel() {
    private val _pet = MutableStateFlow<PetDetailModel>(PetDetailModel())
    val pet = _pet.asStateFlow()

    init {
        fetchPet(petId)
    }
    fun fetchPet(id: Int) {
        Log.d(TAG, "get pet $id")
        viewModelScope.launch(Dispatchers.IO) {
            _pet.value = dexRepository.getOnePet(id)
        }
    }

    companion object {
        private const val TAG = "PetDetailViewModel"
        fun provideFactory(repo: DexRepository, id: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PetDetailViewModel(repo, id) as T
                }
            }
    }
}