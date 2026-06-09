package org.martin.rocomdex.ui.skill


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.martin.rocomdex.data.DexRepository
import org.martin.rocomdex.data.Skill

class SkillsViewModel(private val dexRepository: DexRepository) : ViewModel() {
    private val _skillsList = MutableStateFlow<List<Skill>>(emptyList())
    val skillsList = _skillsList.asStateFlow()

    //private val _skill = MutableStateFlow<Skill>(Skill())
    //val skill = _skill.asStateFlow()

    fun fetchAllSkills() {
        viewModelScope.launch(Dispatchers.IO) {
            _skillsList.value = dexRepository.getAllSkills()
        }
    }

    fun fetchSkill(id: Int) {
        Log.d(TAG, "get skill ${id}")
        viewModelScope.launch(Dispatchers.IO) {
            //_pet.value = dexRepository.getOnePet(id)
        }
    }

    companion object {
        private const val TAG = "SkillsViewModel"
        fun provideFactory(repo: DexRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SkillsViewModel(repo) as T
                }
            }
    }
}