package com.marudo.myapplication

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject


class MainViewModel : ViewModel() {
    var mhwURL = "https://mhw-db.com/armor"

    var armorInfo: MutableLiveData<JSONArray> = MutableLiveData()

    fun fetchItems(context: Context) {

        val queue = Volley.newRequestQueue(context)

        val request = StringRequest(
            mhwURL,
            { response ->
                armorInfo.value = JSONArray(response)
                var armorArray : JSONArray = JSONArray(response)

                //Verifying Cat API JSON Info
                for(i in 0 until armorArray.length()) {
                    var armorItem : JSONObject = armorArray.getJSONObject(i)

                    //now get the properties we want:  name and description
                    Log.i("MainViewModel", "Item Name: ${armorItem.getString("name")}")
                    Log.i("MainViewModel", "Item defense: ${armorItem.getString("defense")}")
                    Log.i("MainViewModel", "Item rank: ${armorItem.getString("rank")}")
                    Log.i("MainViewModel", "Item type: ${armorItem.getString("type")}")
                }
            },
            {
                Log.i("MainActivity", "That didn't work!")
            }
        )


        queue.add(request)

    }


}