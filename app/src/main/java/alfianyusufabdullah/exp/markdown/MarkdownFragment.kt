package alfianyusufabdullah.exp.markdown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.fragment_markdown.*
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@FlowPreview
@ExperimentalTime
class MarkdownFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_markdown, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageMarkdownPreview.adapter = MarkdownPageAdapter(childFragmentManager)
        tabMarkdownPreview.setupWithViewPager(pageMarkdownPreview)
    }

    private class MarkdownPageAdapter(manager: FragmentManager) :
        FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount() = 2

        override fun getItem(position: Int) = when (position) {
            1 -> MarkdownPreviewFragment.newInstance(MarkdownPreviewFragment.TYPE_HTML)
            0 -> MarkdownPreviewFragment.newInstance(MarkdownPreviewFragment.TYPE_MARKDOWN)
            else -> throw IllegalArgumentException("Unknown position")
        }

        override fun getPageTitle(position: Int) = when (position) {
            1 -> "HTML"
            0 -> "MARKDOWN"
            else -> throw IllegalArgumentException("Unknown position")
        }
    }
}