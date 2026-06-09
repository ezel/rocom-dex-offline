package org.martin.rocomdex.data

import android.content.Context
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow

class DexRepository(private val db: DexDatabase) {
    suspend fun getAllPets(): List<Pet> {
        return db.petDao().loadAllPets()
    }

    suspend fun getOnePet(id: Int): PetDetailModel {
        val petWithFeature = db.petDao().loadOnePetWithFeature(id)
        val skills = db.petDao().loadOnePetSkillsList(id)
        return PetDetailModel.fromPetWFS(petWithFeature, skills )
    }

    suspend fun getAllSkills(): List<Skill> {
        return db.skillDao().loadAllSkills()
    }
}

fun calcHpStats(race: Int, nature: Float = 1.0f, ev: Int = 60, level: Int = 60): Int {
    //[(等级/25+1)*(种族值+个体值/2)/2+等级+10]*性格修正+成长值
    return (((level / 25 + 1) * (race + ev / 2) / 2 + level + 10) * nature + 100).toInt()
}

fun calc5VStats(race: Int, nature: Float = 1.0f, ev: Int = 60, level: Int = 60): Int {
    //[(种族值+个体值/2)/2*(1+等级/50)+10]*性格修正+成长值
    return (((race + ev / 2) / 2 * (1 + level / 50) + 10) * nature + 50).toInt()
}

fun raceToList(race: Int, calc: (Int, Float, Int, Int) -> Int): List<Int> {
    return listOf(
        race,
        calc(race, 1.0f, 0, 60),
        calc(race, 1.0f, 60, 60),
        calc(race, 1.1f, 0, 60),
        calc(race, 1.1f, 60, 60)
    )
}

data class PetDetailModel(
    val pet: Pet = NullPet,
    val feature: Feature = NullFeature,
    val skills: List<SkillsOfPet> = emptyList(),
) {
    val stats: Map<String, List<Int>> = mapOf(
        "hp" to raceToList(pet.raceHP, ::calcHpStats),
        "atk" to raceToList(pet.racePAtk, ::calc5VStats),
        "def" to raceToList(pet.racePDef, ::calc5VStats),
        "satk" to raceToList(pet.raceSAtk, ::calc5VStats),
        "sdef" to raceToList(pet.raceSDef, ::calc5VStats),
        "spd" to raceToList(pet.raceSpe, ::calc5VStats),
    )

    companion object {
        fun fromPetWFS(sourcePet: PetWithFeature, sourceSkills: List<SkillsOfPet>): PetDetailModel {
            return PetDetailModel(
                pet = sourcePet.pet,
                feature = sourcePet.feature,
                skills = sourceSkills
            )
        }
    }
}
