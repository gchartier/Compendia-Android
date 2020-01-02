package com.gabrieldchartier.compendia.persistence.authentication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gabrieldchartier.compendia.models.AccountProperties

@Dao
interface AccountPropertiesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndReplace(accountProperties: AccountProperties): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrIgnore(accountProperties: AccountProperties): Long

    @Query("SELECT * FROM account_properties WHERE pk = :pk")
    fun searchByPK(pk: Int): LiveData<AccountProperties?>

    @Query("SELECT * FROM account_properties WHERE pk = :pk")
    fun searchByPKNoLiveData(pk: Int): AccountProperties

    @Query("SELECT * FROM account_properties WHERE email = :email")
    fun searchByEmail(email: String): AccountProperties?

    @Query("UPDATE account_properties SET username = :username WHERE pk = :pk")
    fun updateAccountProperties(username: String, pk: Int)

    @Query("SELECT * FROM account_properties")
    fun loadAllUsers(): Array<AccountProperties>
}