package com.delacerna.signin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_fragment_sample.*

class FragmentSample : Fragment() {

    private lateinit var mContext: Context
    private val recipe = ArrayList<Campaign>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_fragment_sample, container, false)

        val recyclerView1 = view.findViewById<RecyclerView>(R.id.recyclerView)

        val mLayoutManager = LinearLayoutManager(this.activity, LinearLayout.VERTICAL, false)

        recipe.add(Campaign(R.drawable.facebook, "Harold", "Travel", "01/02/2018"))
        recipe.add(Campaign(R.drawable.facebook, "Brad", "Travel", "01/02/2018"))
        recipe.add(Campaign(R.drawable.facebook, "Inday", "Travel", "01/02/2018"))
        recipe.add(Campaign(R.drawable.facebook, "feye", "Travel", "01/02/2018"))
        recipe.add(Campaign(R.drawable.facebook, "jm", "Travel", "01/02/2018"))
        recipe.add(Campaign(R.drawable.facebook, "brian", "Travel", "01/02/2018"))
        recipe.add(Campaign(R.drawable.facebook, "dodge", "Travel", "01/02/2018"))
        recipe.add(Campaign(R.drawable.facebook, "ace", "Travel", "01/02/2018"))
        recipe.add(Campaign(R.drawable.facebook, "arnold", "Travel", "01/02/2018"))
        recipe.add(Campaign(R.drawable.facebook, "tonyo", "Travel", "01/02/2018"))

        val mAdapter = Adapter(recipe)
        recyclerView1.layoutManager = mLayoutManager
        recyclerView1.adapter = mAdapter
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}
