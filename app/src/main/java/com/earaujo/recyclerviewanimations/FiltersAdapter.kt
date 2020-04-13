package com.earaujo.recyclerviewanimations

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list_filters.view.*

class FiltersAdapter(
    private val items: List<List<String>>,
    private val callback: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return getItemViewHolder(parent, inflater)
    }

    private fun getItemViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_list_filters, parent, false)
        return FiltersViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FiltersViewHolder).bind()
    }

    override fun getItemCount() = items.size

    inner class FiltersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var lastTotalWidth = 0

        fun bind() {
            itemView.filterRecyclerView.adapter =
                FilterAdapter(items[layoutPosition]) {
                    callback()
                    handleClickCallback(it)
                }

            itemView.filterRecyclerView.itemAnimator?.apply {
                moveDuration = 200
                addDuration = 200
                removeDuration = 200
                changeDuration = 200
            }
        }

        private fun handleClickCallback(view: View?) {
            //Change from wrap_content to pixels
            itemView.layoutParams = itemView.layoutParams?.apply {
                width = itemView.width
                itemView.requestLayout()
            }

            (itemView as? RecyclerView)?.let {
                view?.let { v ->
                    lastTotalWidth = itemView.width
                    it.slideAnimate(itemView.width, v.width, 250)
                } ?: run {
                    it.slideAnimate(itemView.width, lastTotalWidth)
                }
            }
        }

        private fun View.slideAnimate(fromX: Int, toX: Int, delay: Long = 0) {
            val slideAnimator = ValueAnimator.ofInt(fromX, toX).apply {
                duration = 200
                addUpdateListener {
                    this@slideAnimate.layoutParams = this@slideAnimate.layoutParams?.apply {
                        width = it.animatedValue as Int
                        this@slideAnimate.requestLayout()
                    }
                }
            }
            AnimatorSet().apply {
                play(slideAnimator)
                interpolator = AccelerateDecelerateInterpolator()
                startDelay = delay
                start()
            }

        }
    }

}
