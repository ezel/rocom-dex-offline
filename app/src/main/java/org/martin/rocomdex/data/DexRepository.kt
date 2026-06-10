package org.martin.rocomdex.data

class DexRepository(private val db: DexDatabase) {
    suspend fun getAllPets(): List<Pet> {
        return db.petDao().loadAllPets()
    }

    suspend fun getOnePet(id: Int): PetDetailModel {
        val petWithFeature = db.petDao().loadOnePetWithFeature(id)
        val skills = db.petDao().loadOnePetSkillsList(id)
        val evolution = if (petWithFeature.pet.evolution != null) {
            // TODO: get evolutionPetIcons
             db.petDao().loadOnePetEvolutionChain(id)
        } else { null }
        // if form

        return PetDetailModel.fromPetWFS(petWithFeature, skills, evolution)
    }

    suspend fun getOnePetIconInfo(id: Int): PetIconInfo {
        return db.petDao().loadOnePetIconInfo(id)
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
    val evolutions: List<PetEvolution>? = null,
    val evolutionPetIcons: Map<Int, PetIconInfo>? = null
) {
    val stats: Map<String, List<Int>> = mapOf(
        "hp" to raceToList(pet.raceHP, ::calcHpStats),
        "atk" to raceToList(pet.racePAtk, ::calc5VStats),
        "def" to raceToList(pet.racePDef, ::calc5VStats),
        "satk" to raceToList(pet.raceSAtk, ::calc5VStats),
        "sdef" to raceToList(pet.raceSDef, ::calc5VStats),
        "spd" to raceToList(pet.raceSpe, ::calc5VStats),
    )
    val skillMap: Map<Int, List<SkillsOfPet>> =
        skills.groupBy { it.petsSkillInfo.type }.toSortedMap()
    val skillMapCount = skillMap.mapValues { it.value.size }

    companion object {
        fun fromPetWFS(
            sourcePet: PetWithFeature,
            sourceSkills: List<SkillsOfPet>,
            sourceEvolution: List<PetEvolution>? = null,
        ): PetDetailModel {
            return PetDetailModel(
                pet = sourcePet.pet,
                feature = sourcePet.feature,
                skills = sourceSkills,
                evolutions = sourceEvolution
            )
        }
    }
}
