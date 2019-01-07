package ch.stephgit.memory.persistence.repository

import android.content.ContentValues.TAG
import android.util.Log
import ch.stephgit.memory.persistence.entity.Player
import ch.stephgit.memory.persistence.service.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerRepository () {

    private var apiClient: APIClient = APIClient.build()


    fun saveUser(player: Player) : Long {
        apiClient.postRegister(player)
            .enqueue(object : Callback<Int> {
                override fun onFailure(call: Call<Int>?, t: Throwable?) {
                    Log.d(TAG, t.toString())
                }
                override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                    if (response?.isSuccessful == true) {
                       Log.d(TAG, response.message())

                    } else {
                        Log.d(TAG, "failed response")
                    }
                }
            })
        return 0
//        return db.playerDAO().savePlayer(player)
    }

    fun getUsers() : List<Player>  {
        var list: MutableList<Player> = ArrayList()
        apiClient.getUsers()
            .enqueue(object : Callback<List<Player>> {
                override fun onFailure(call: Call<List<Player>>, t: Throwable) {
                    Log.d(TAG, t.toString())
                }

                override fun onResponse(call: Call<List<Player>>, response: Response<List<Player>>) {
                    if (response.body() != null) {
                        response.body()!!.forEach { list.add(it) }
                    }
                }

            })
        return list
    }

    fun findPlayerById(userId: Int) : Player {
        val response = apiClient.getUsers().execute()
        if ((response.isSuccessful) && (response.body() != null)) {
            for (player in response.body()!!) {
                if (player.id == userId) {
                    return Player(player.userName, null, null, player.id )
                }
            }
        }
        return Player("")
    }

    fun findPlayerByUserName(username: String) : Player {
        val response = apiClient.getUsers().execute()
        if ((response.isSuccessful) && (response.body() != null)) {
            for (player in response.body()!!) {
                if (player.userName == username) {
                    return Player(player.userName, null, null, player.id )
                }
            }
        }
        return Player("")
    }


}