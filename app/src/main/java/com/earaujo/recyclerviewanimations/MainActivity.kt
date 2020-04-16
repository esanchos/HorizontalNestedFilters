package com.earaujo.recyclerviewanimations

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.transition.doOnStart
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup() {

        filtersRecyclerView.adapter =
            FiltersAdapter(
                listOf(
                    listOf("Comprar", "Alugar"),
                    listOf("Ação", "Aventura", "Comédia", "Terror", "Suspense", "Drama")
                )
            ) { view, position ->
                handleCallback(view, position)
            }
    }

    private fun handleCallback(view: View?, position: Int) {
        mainScrollView.fullScroll(ScrollView.FOCUS_LEFT)
        val animDuration = resources.getInteger(R.integer.anim_duration_filter).toLong()
        val transition = TransitionSet().apply {
            duration = animDuration
            ordering = TransitionSet.ORDERING_TOGETHER
            if (view == null) {
                startDelay = animDuration
            }
            filtersRecyclerView.adapter?.let {
                for (index in 0..it.itemCount) {
                    (filtersRecyclerView.findViewHolderForLayoutPosition(index)?.itemView as? RecyclerView)?.children?.forEach { child ->
                        excludeTarget(child, true)
                    }
                }
            }
            addTransition(ChangeBounds())
            doOnStart {
                if (view != null) {
                    filtersRecyclerView.findViewHolderForLayoutPosition(position)?.itemView?.background =
                        TransitionDrawable(
                            arrayOf(
                                getBitmap(R.drawable.shape_recycler_background_selected),
                                getBitmap(R.drawable.shape_recycler_background)
                            )
                        ).apply { startTransition(animDuration.toInt()) }
                } else {
                    filtersRecyclerView.findViewHolderForLayoutPosition(position)?.itemView?.background =
                        TransitionDrawable(
                            arrayOf(
                                getBitmap(R.drawable.shape_recycler_background),
                                getBitmap(R.drawable.shape_recycler_background_selected)
                            )
                        ).apply { startTransition(animDuration.toInt()) }
                }
            }
        }
        TransitionManager.beginDelayedTransition(mainScrollView, transition)
    }

    private fun Context.getBitmap(bitmapId: Int) =
        AppCompatResources.getDrawable(this, bitmapId)

}
