package alfianyusufabdullah.exp.markdown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.noties.markwon.Markwon
import kotlinx.android.synthetic.main.fragment_preview.*


class PreviewFragment : Fragment() {

    private val marks: String by lazy { arguments?.getString("m") ?: "**null**" }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mark = Markwon.create(requireContext())
        mark.setMarkdown(markdownPreview, marks)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            PreviewFragment().apply {
                arguments = Bundle().apply {
                    putString("m", param1)
                }
            }
    }
}