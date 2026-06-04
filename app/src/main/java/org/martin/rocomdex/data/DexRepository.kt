package org.martin.rocomdex.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class DexRepository(private val db: DexDatabase) {
    suspend fun getAllPets(): List<PetWithFeature> {
        return db.petDao().loadAllPetsWithFeature()
    }
    suspend fun getOnePet(id: Int): PetWithFeature {
        return db.petDao().loadOnePetWithFeature(id)
    }
}