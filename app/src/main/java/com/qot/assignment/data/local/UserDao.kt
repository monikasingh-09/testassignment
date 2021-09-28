package com.qot.assignment.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qot.assignment.data.models.entity.User

@Dao
interface UserDao {

    @Query("DELETE FROM user")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(vararg user: User)

    @Query("SELECT * FROM user ORDER BY firstName")
    fun getAllUsers(): LiveData<List<User>?>

    @Query("SELECT COUNT(*) FROM user")
    fun getUsersCount(): Int

    @Query("SELECT * FROM user WHERE userId = :uid")
    fun getUser(uid: String): LiveData<User?>

}