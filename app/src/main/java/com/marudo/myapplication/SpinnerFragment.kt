package com.marudo.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marudo.myapplication.databinding.FragmentSpinnerBinding
import org.json.JSONArray
import org.json.JSONObject

// Fragment for the Spinner
class SpinnerFragment : Fragment(), AdapterView.OnItemSelectedListener {

    interface DisplayUpdate {
        fun updateDisplay(itemObject: JSONObject)
    }

    private var _binding: FragmentSpinnerBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel : MainViewModel


    private lateinit var activityCallback: SpinnerFragment.DisplayUpdate

    private var itemArray:JSONArray = JSONArray()

    override fun onItemSelected(spinner: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        for (i in 0 until itemArray.length()) {
            if (itemArray.getJSONObject(i).get("name") == spinner?.getItemAtPosition(pos)) {
                activityCallback.updateDisplay(itemArray.getJSONObject(i))
            }
        }
    }


    override fun onNothingSelected(p0: AdapterView<*>?) {
        Log.d("SpinnerFragment", "No item Selected")
    }


    override fun onAttach(context: Context) {

        super.onAttach(context)
        activityCallback = context as DisplayUpdate
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSpinnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        //Set spinner options as item names
        val onFetch = Observer<JSONArray> {
                response -> run {
            itemArray = response
            var itemList: MutableList<String> = mutableListOf()


            for (i in 0 until response.length()) {
                var itemObject: JSONObject = itemArray.getJSONObject(i)
                itemList.add(itemObject.get("name").toString())
            }

            var arrayAdapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                itemList
            )

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.spinner.setAdapter(arrayAdapter)
            binding.spinner.onItemSelectedListener = this

        }
        }

        viewModel.armorInfo.observe(viewLifecycleOwner, onFetch)

        viewModel.fetchItems(requireContext())


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}