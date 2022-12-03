package brite.outdoor.utils.compressor

import android.graphics.BitmapFactory
import com.paynetone.counter.utils.calculateInSampleSize
import com.paynetone.counter.utils.compressor.Compression
import com.paynetone.counter.utils.compressor.Constraint
import com.paynetone.counter.utils.decodeSampledBitmapFromFile
import com.paynetone.counter.utils.determineImageRotation
import com.paynetone.counter.utils.overWrite

import java.io.File

class ResolutionConstraint(private val width: Int, private val height: Int) : Constraint {

    override fun isSatisfied(imageFile: File): Boolean {
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(imageFile.absolutePath, this)
            calculateInSampleSize(this, width, height) <= 1
        }
    }

    override fun satisfy(imageFile: File): File {
        return decodeSampledBitmapFromFile(imageFile, width, height).run {
            determineImageRotation(imageFile, this).run {
                overWrite(imageFile, this)
            }
        }
    }
}

fun Compression.resolution(width: Int, height: Int) {
    constraint(ResolutionConstraint(width, height))
}