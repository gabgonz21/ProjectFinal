package com.marudo.myapplication

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.marudo.myapplication.databinding.FragmentDisplayBinding
import org.json.JSONObject
import java.util.concurrent.Executors

class DisplayFragment : Fragment() {

    private var _binding: FragmentDisplayBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel : MainViewModel

    private lateinit var mainContext: Context

    val executor = Executors.newSingleThreadExecutor()
    val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDisplayBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

    }



    fun showArmorInfo(breedObject: JSONObject) {
        val defenseObject = breedObject.getJSONObject("defense")
        val baseDefense = defenseObject.getInt("base")
        //Setting Name, Defense, Rarity, and Type
        binding.nameTxt.text = "${breedObject.get("name").toString()}"
        binding.defenseTxt.text = "Base Defense: $baseDefense"
        binding.rarityTxt.text = "Rarity: ${breedObject.get("rarity").toString()}"
        binding.typeTxt.text = "Type: ${breedObject.get("type").toString()}"

        //Retrieving Armor Image
        //val imageObject: JSONObject = breedObject.get("image") as JSONObject
        val assets = breedObject.getJSONObject("assets")
        val imageObject = assets.getString("imageMale")
        val femaleimageObject = assets.getString("imageFemale")


        executor.execute {
            try {
                val imStream = java.net.URL(imageObject).openStream()
                val imStream2 = java.net.URL(femaleimageObject).openStream()
                val image = BitmapFactory.decodeStream(imStream)
                val image2 = BitmapFactory.decodeStream(imStream2)

                handler.post {
                    binding.maleArmor.setImageBitmap(image)
                    binding.femaleArmor.setImageBitmap(image2)
                }
            } catch (e: java.lang.Exception) {
                println(e)
            }
        }



    }

    //OnDestory for Android Life cycle
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}