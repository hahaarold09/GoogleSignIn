package com.delacerna.signin

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Adapter(private val campaignList: ArrayList<Campaign>):RecyclerView.Adapter<CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.campaign_list_layout,parent,false)
        return CustomViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return campaignList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.tvTitle.text = campaignList[position].campName
        holder.tvCategory.text = campaignList[position].campCategory
        holder.tvDeadline.text = campaignList[position].campDeadline

        holder.tvCampaignLayout?.setOnClickListener { v ->
            val intent = Intent(v?.context, TabLayoutCampaign::class.java)
            intent.putExtra("name",campaignList[position].campName)
            intent.putExtra("category",campaignList[position].campCategory)
            intent.putExtra("deadline",campaignList[position].campDeadline)
            v?.context?.startActivity(intent)
        }
    }

}
class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

    val tvTitle  = view.findViewById<TextView>(R.id.campaignTitle)
    val tvCategory  = view.findViewById<TextView>(R.id.campaignCategory)
    val tvDeadline  = view.findViewById<TextView>(R.id.campaignDeadline)
    val tvCampaignLayout  = view.findViewById<ConstraintLayout>(R.id.viewCampaign)




}