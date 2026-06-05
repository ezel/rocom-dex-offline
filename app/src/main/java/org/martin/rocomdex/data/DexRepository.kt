package org.martin.rocomdex.data

import android.content.Context
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow

class DexRepository(private val db: DexDatabase) {
    suspend fun getAllPets(): List<PetWithFeature> {
        return db.petDao().loadAllPetsWithFeature()
    }
    suspend fun getOnePet(id: Int): PetDetailModel {
        return PetDetailModel.fromPetWFS(db.petDao().loadOnePetWithFeatureAndSkills(id))
    }
}

data class PetDetailModel(
    val pet: Pet = NullPet,
    val feature: Feature = NullFeature,
    val skills: List<Skill> = emptyList(),
) {
    companion object {
        fun fromPetWFS(source: PetWithFeatureAndSkills): PetDetailModel {
            return PetDetailModel(pet = source.pet, feature = source.feature, skills = source.skills )
        }
    }
}
