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


class SkillDetailViewModel(private val dexRepository: DexRepository, skId: Int) : ViewModel() {
    private val _skill = MutableStateFlow<Skill?>(null)
    val skill = _skill.asStateFlow()

    init {
        fetchSkill(skId)
    }
    fun fetchSkill(id: Int) {
        Log.d(TAG, "get skill $id")
        viewModelScope.launch(Dispatchers.IO) {
            //_skill.value = dexRepository.getOnePet(id)
        }
    }

    companion object {
        private const val TAG = "SkillDetailViewModel"
        fun provideFactory(repo: DexRepository, id: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SkillDetailViewModel(repo, id) as T
                }
            }
    }
}