package com.stone.starwarsstore

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

abstract class Mask {
    companion object {
        fun unmask(s: String): String {
            return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
                    .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "")
                    .replace("[ ]".toRegex(), "").replace("[)]".toRegex(), "")
        }


        fun format(ediTxt: EditText): TextWatcher {

            return object : TextWatcher {
                internal var isUpdating: Boolean = false
                internal var old = ""

                override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                           count: Int) {

                    if (isUpdating) {
                        isUpdating = false
                        old = s.toString()
                        return
                    }

                    if (!s.toString().isEmpty() && s.toString().length > old.length) {
                        val str = Mask.unmask(s.toString())
                        var mask = ""

                        var i = 0
                        for (m in "##/##".toCharArray()) {
                            if (m != '#') {
                                mask += m
                                continue
                            }
                            try {
                                mask += str[i]
                            } catch (e: Exception) {
                                break
                            }

                            i++
                        }
                        isUpdating = true
                        ediTxt.setText(mask)
                        ediTxt.setSelection(mask.length)
                    } else {
                        old = s.toString()
                    }
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                               after: Int) {
                }

                override fun afterTextChanged(s: Editable) {}
            }
        }
    }
}