package org.martin.rocomdex.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class DexRepository(private val db: DexDatabase) {
    fun getAllPets(): Flow<List<PetWithFeature>> {
        return db.petDao().loadAllPetsWithFeature()
    }
}