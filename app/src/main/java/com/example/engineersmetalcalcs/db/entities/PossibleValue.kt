package com.example.engineersmetalcalcs.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "possible_value", foreignKeys = [ForeignKey(entity = Character::class,
    parentColumns = ["id"], childColumns = ["characterIdFk"])]
)
data class PossibleValue(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var characterIdFk: Long,
    var value: Float
)
