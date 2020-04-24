package com.example.animetracker.utility

import android.text.InputFilter
import android.text.Spanned

class InputFilterMinMax(_min: Int, _max: Int): InputFilter {

    val min = _min
    val max = _max

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            var newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend, dest.toString().length)
            newVal = newVal.substring(0,dstart) + source.toString() + newVal.substring(dstart,newVal.length)
            val input = newVal.toInt()
            if (isInRange(min, max, input))
                return null
        } catch (nfe: NumberFormatException) { }
        return ""
    }

    private fun isInRange(min: Int, max: Int, input:Int):Boolean {
        return if (max > min) input in min..max else input in max..min
    }
}