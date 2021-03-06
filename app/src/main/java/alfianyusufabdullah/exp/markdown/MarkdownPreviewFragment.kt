package alfianyusufabdullah.exp.markdown

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.noties.markwon.Markwon
import io.noties.markwon.syntax.Prism4jThemeDefault
import io.noties.markwon.syntax.SyntaxHighlightPlugin
import io.noties.prism4j.Prism4j
import io.noties.prism4j.annotations.PrismBundle
import kotlinx.android.synthetic.main.fragment_preview.*
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@ExperimentalTime
@FlowPreview
@PrismBundle(include = ["java", "kotlin"], grammarLocatorClassName = ".GrammarLocatorSourceCode")
class MarkdownPreviewFragment private constructor() : Fragment() {

    private val type: String by lazy { arguments?.getString(KEY_TYPE) ?: "html" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prism4jThemeDefault = Prism4jThemeDefault(Color.parseColor("#f0f0f0"))
        val prism4j = Prism4j(GrammarLocatorSourceCode())

        val mark = Markwon.builder(requireContext())
            .usePlugin(SyntaxHighlightPlugin.create(prism4j, prism4jThemeDefault))
            .build()

        val mainViewModel = ViewModelProvider(requireActivity())[MarkdownViewModel::class.java]
        mainViewModel.apply {
            when (type) {
                TYPE_MARKDOWN -> markdown.observe(viewLifecycleOwner) {
                    mark.setMarkdown(
                        markdownPreview,
                        it
                    )
                }
                TYPE_HTML -> html.observe(viewLifecycleOwner) {
                    println(it)
                   markdownPreview.text = it
                }
            }
        }
    }

    companion object {
        const val TYPE_MARKDOWN = "markdown"
        const val TYPE_HTML = "html"

        const val KEY_TYPE = "type"

        @JvmStatic
        fun newInstance(type: String) =
            MarkdownPreviewFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_TYPE, type)
                }
            }
    }
}