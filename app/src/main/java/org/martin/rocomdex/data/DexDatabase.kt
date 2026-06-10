package org.martin.rocomdex.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.RoomDatabase
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "pet_base")
data class Pet (
    @PrimaryKey val id: Int,
    val hid: Int,

    val name: String,
    @ColumnInfo(name="feature") val featureId: Int,
    @ColumnInfo(name="type1") val type1Id: Int,
    @ColumnInfo(name="type2") val type2Id: Int?,
    val stage: Int,
    val form: String?,
    @ColumnInfo(name="form_type") val formType: Int?,
    @ColumnInfo(name="bid") val bossId: Int?,
    @ColumnInfo(name="race_hp") val raceHP: Int,
    @ColumnInfo(name="race_patk") val racePAtk: Int,
    @ColumnInfo(name="race_satk") val raceSAtk: Int,
    @ColumnInfo(name="race_pdef") val racePDef: Int,
    @ColumnInfo(name="race_sdef") val raceSDef: Int,
    @ColumnInfo(name="race_spe") val raceSpe: Int,
    @ColumnInfo(name="race_sum") val raceSum: Int,
    val wish: Int,
    @ColumnInfo(name="egg1") val egg1Id: Int?,
    @ColumnInfo(name="egg2") val egg2Id: Int?,
    val evolution: String?,
    val res: String,
    @ColumnInfo(name="version_id") val versionId: Int?
)

@Entity(tableName = "ability")
data class Feature (
    @PrimaryKey val id: Int,
    val name: String,
    val desc: String,
    @ColumnInfo(name="target_type") val targetType: Int?,
    val res: String,
    @ColumnInfo(name="version_id") val versionId: Int?
)

@Entity(tableName = "skill")
data class Skill (
    @PrimaryKey val id: Int,
    val name: String,
    val desc: String,
    @ColumnInfo(name="skill_type") val skillType: Int,
    @ColumnInfo(name="damage_type") val damageType: Int,
    val energy: Int,
    val damage: Int?,
    @ColumnInfo(name="target_type") val targetType: Int?,
    val res: String,
    @ColumnInfo(name="version_id") val versionId: Int?
)

data class PetWithFeature(
    @Embedded val pet: Pet,

    @Relation(
        parentColumn = "feature",
        entityColumn = "id"
    )
    val feature: Feature
)

@Entity(primaryKeys = ["pid", "skid", "type"], tableName = "pets_skills")
data class PetsSkillsCrossRef(
    val pid: Int,
    val skid: Int,
    val type: Int,
    val info: Int?,
    @ColumnInfo(name="version_id") val versionId: Int?
)

@Entity(primaryKeys = ["root", "path"], tableName="pet_evolution")
data class PetEvolution(
    val root: Int,
    val path: String,
    val stage1: String,
    val stage2: String?,
    val stage3: String?,
    @ColumnInfo(name="version_id") val versionId: Int?
)

data class PetWithFeatureAndSkills(
    @Embedded val pet: Pet,

    @Relation(
        parentColumn = "feature",
        entityColumn = "id"
    )
    val feature: Feature,

    @Relation(
        parentColumn = "id",
//        entity = Skill::class,
        entityColumn = "id",
        associateBy = Junction(
            value = PetsSkillsCrossRef::class,
            parentColumn = "pid",
            entityColumn = "skid"
        )
    )
    val skills: List<Skill>
)

data class SkillsOfPet(
    @Embedded val petsSkillInfo: PetsSkillsCrossRef,
    @Relation(
        parentColumn = "skid",
        entityColumn = "id"
    )
    val skill: Skill
)

data class PetIconInfo(
    val id: Int,
    //val hid: Int,
    val name: String,
    //val stage: Int,
    val form: String?,
    //val formType: Int?,
    val res: String,
)

val NullPet : Pet = Pet(
    0,0,"NoName", 0, 5, null, 0, null, null, 2,
    0, 0, 0,0,0,0,0,0, null, null, null, "", null
)

val NullFeature : Feature = Feature (
    0, "NoName", "", null, "", null
)

val NullPetWithFeature : PetWithFeature = PetWithFeature(
    pet = NullPet,
    feature = NullFeature
)

val NullPetWithFeatureAndSkills : PetWithFeatureAndSkills = PetWithFeatureAndSkills(
    pet = NullPet,
    feature = NullFeature,
    skills = emptyList()
)

@Dao
interface PetDao {
    @Query("SELECT * FROM pet_base ORDER BY hid")
    suspend fun loadAllPets(): List<Pet>

    @Transaction
    @Query("SELECT * FROM pet_base ORDER BY hid")
    suspend fun loadAllPetsWithFeature(): List<PetWithFeature>

    @Transaction
    @Query("SELECT * FROM pet_base WHERE id = :pid ")
    suspend fun loadOnePetWithFeature(pid: Int): PetWithFeature

    @Transaction
    @Query("SELECT * FROM pet_base WHERE id = :pid ")
    suspend fun loadOnePetWithFeatureAndSkills(pid: Int): PetWithFeatureAndSkills

    @Transaction
    @Query("SELECT * FROM pets_skills WHERE pid = :pid ORDER BY type, info")
    suspend fun loadOnePetSkillsList(pid: Int): List<SkillsOfPet>

    @Query("SELECT * FROM pet_evolution WHERE path like '%' || :pid || '%' ")
    suspend fun loadOnePetEvolutionChain(pid: Int): List<PetEvolution>

    @Query("SELECT id, name, form, res FROM pet_base WHERE id = :pid ")
    suspend fun loadOnePetIconInfo(pid: Int): PetIconInfo

    @Query("SELECT id, name, form, res FROM pet_base WHERE id in (:pids) ")
    suspend fun loadPetIconInfoLists(pids: List<Int>) : List<PetIconInfo>
}

@Dao
interface SkillDao {
    @Query("SELECT * FROM skill")
    suspend fun loadAllSkills(): List<Skill>
}

@Database(entities = [Pet::class, Feature::class, Skill::class, PetsSkillsCrossRef::class, PetEvolution::class], version=1)
abstract class DexDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
    abstract fun skillDao(): SkillDao
}