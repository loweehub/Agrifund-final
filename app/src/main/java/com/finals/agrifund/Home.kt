package com.finals.agrifund

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: ListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeArrayList: ArrayList<Campaigns>

    lateinit var overImage: Array<Int>
    lateinit var overTitle: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView = view.findViewById(R.id.homeList)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ListAdapter(homeArrayList)
        recyclerView.adapter = adapter
    }

    private fun dataInitialize(){
        homeArrayList = arrayListOf<Campaigns>()
        overImage = arrayOf(
            R.drawable.dash1,
            R.drawable.dash2,
            R.drawable.dash3

        )

        overTitle = arrayOf(
            "Farming is a profesion of hope" +
                    "Farming is one of the most beneficial and essential activities on Earth. It sustains life by providing food, supports economies, and nurtures the land. Beyond just growing crops, farming fosters a connection with nature, promotes sustainability, and cultivates communities. It is a noble pursuit that contributes profoundly to the well-being of humanity and the health of our planet.",

            "Fisheries and aquaculture are vital " +
                    " Fishing is like a puzzle, where the river or ocean is the board, and the fish are the pieces that need to be carefully and patiently pieced together. Just as a puzzle requires strategy, patience, and a keen eye, so does fishing. It's not just about dropping a line in the water and hoping for the best - it's about understanding the environment, the behavior of the fish, and the right techniques to use. In this way, fishing is a metaphor for the larger world of fisheries, where the sustainable management of our oceans and rivers requires a deep understanding of the complex systems at play, and the ability to adapt and innovate in the face of changing conditions.",
            "Trees are poems that the earth writes" +
                    "    Just as a fisherman carefully selects the right bait, rod, and reel to catch the perfect fish, trees are the ultimate \"catch\" for our planet. They provide the perfect blend of oxygen, shade, and sustenance for all living creatures.",

        )


        for(i in overImage.indices){
            val camps  = Campaigns(overImage[i], overTitle[i])
            homeArrayList.add(camps)
        }
    }



}