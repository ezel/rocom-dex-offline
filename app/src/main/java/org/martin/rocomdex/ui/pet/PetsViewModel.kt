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
import org.martin.rocomdex.data.PetWithFeature
import org.martin.rocomdex.data.PetWithFeatureAndSkills

class PetsViewModel(private val dexRepository: DexRepository) : ViewModel() {
    private val _petsList = MutableStateFlow<List<PetWithFeature>>(emptyList())
    val petsList = _petsList.asStateFlow()

    private val _pet = MutableStateFlow<PetDetailModel>(PetDetailModel())
    val pet = _pet.asStateFlow()
    fun fetchAllPets(infoStr: String) {
        Log.d(TAG, infoStr)
        viewModelScope.launch(Dispatchers.IO) {
            _petsList.value = dexRepository.getAllPets()
        }
    }

    fun fetchPet(id: Int) {
        Log.d(TAG, "get pet ${id}")
        viewModelScope.launch(Dispatchers.IO) {
            _pet.value = dexRepository.getOnePet(id)
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
        fun provideFactory(repo: DexRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PetsViewModel(repo) as T
                }
            }
    }
}