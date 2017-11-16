package douglasspgyn.com.github.desafiostone.common.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

/**
 * Created by Douglas on 13/11/17.
 */

class MaskEditTextChangedListener(private val mask: String, private val editText: EditText) : TextWatcher {

    private val symbolMask = HashSet<String>()
    private var updating: Boolean = false
    private var old = ""

    init {
        initSymbolMask()
    }

    private fun initSymbolMask() {
        for (i in 0 until mask.length) {
            val ch = mask[i]
            if (ch != '#') {
                symbolMask.add(ch.toString())
            }
        }
    }

    private fun unmask(s: String, replaceSymbols: Set<String>): String {
        var s = s
        for (symbol in replaceSymbols) {
            s = s.replace("[$symbol]".toRegex(), "")
        }

        return s
    }

    private fun mask(format: String, text: String): String {
        var maskedText = ""
        var i = 0
        for (m in format.toCharArray()) {
            if (m != '#') {
                maskedText += m
                continue
            }
            try {
                maskedText += text[i]
            } catch (e: Exception) {
                break
            }

            i++
        }

        return maskedText
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val str = unmask(s.toString(), symbolMask)
        val mascara: String

        if (updating) {
            old = str
            updating = false
            return
        }

        mascara = if (str.length > old.length) {
            mask(mask, str)
        } else {
            s.toString()
        }

        updating = true

        editText.setText(mascara)
        editText.setSelection(mascara.length)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable) {}
}