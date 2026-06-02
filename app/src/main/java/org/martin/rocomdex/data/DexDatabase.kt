package org.martin.rocomdex.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Embedded
import androidx.room.Entity
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
    @ColumnInfo(name="race_hp") val raceHP: Int,
    @ColumnInfo(name="race_patk") val racePAtk: Int,
    @ColumnInfo(name="race_satk") val raceSAtk: Int,
    @ColumnInfo(name="race_pdef") val racePDef: Int,
    @ColumnInfo(name="race_sdef") val raceSDef: Int,
    @ColumnInfo(name="race_spe") val raceSpe: Int,
    @ColumnInfo(name="race_sum") val raceSum: Int,
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

data class PetWithFeature(
    @Embedded val pet: Pet,
    @Relation(
        parentColumn = "feature",
        entityColumn = "id"
    )
    val feature: Feature
)

@Dao
interface PetDao {
    @Query("SELECT * FROM pet_base ORDER BY hid")
    fun loadAllPets(): Array<Pet>

    @Transaction
    @Query("SELECT * FROM pet_base ORDER BY hid")
    fun loadAllPetsWithFeature(): Flow<List<PetWithFeature>>
}

@Database(entities = [Pet::class, Feature::class], version=1)
abstract class DexDatabase : RoomDatabase() {
    abstract fun petDao(): PetDao
}