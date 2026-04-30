package devilstudio.com.farmerfriend

import android.content.res.AssetManager
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class Classifier(assetManager: AssetManager, modelPath: String, labelPath: String, inputSize: Int) {

    private var INTERPRETER: Interpreter
    private var LABEL_LIST: List<String>

    private val INPUT_SIZE: Int = inputSize
    private val PIXEL_SIZE: Int = 3
    private val MAX_RESULTS = 3

    // Düşük tutuyoruz ki sonuç tamamen boş dönmesin
    private val THRESHOLD = 0.01f

    data class Recognition(
        var id: String = "",
        var title: String = "",
        var confidence: Float = 0F
    ) {
        override fun toString(): String {
            return "Title = $title, Confidence = $confidence"
        }
    }

    init {
        INTERPRETER = Interpreter(loadModelFile(assetManager, modelPath))
        LABEL_LIST = loadLabelList(assetManager, labelPath)
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun loadLabelList(assetManager: AssetManager, labelPath: String): List<String> {
        return assetManager.open(labelPath).bufferedReader().useLines { it.toList() }
    }

    fun recognizeImage(bitmap: Bitmap): List<Recognition> {
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false)
        val byteBuffer = bitmapToByteBuffer(scaledBitmap)

        val result = Array(1) { FloatArray(LABEL_LIST.size) }

        INTERPRETER.run(byteBuffer, result)

        return getSortedResult(result)
    }

    private fun bitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        var pixel = 0

        for (i in 0 until INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val value = intValues[pixel++]

                val r = value shr 16 and 0xFF
                val g = value shr 8 and 0xFF
                val b = value and 0xFF

                // ÖNEMLİ:
                // Modelin içine EfficientNet preprocess_input eklendiyse /255 YAPMIYORUZ.
                byteBuffer.putFloat(r.toFloat())
                byteBuffer.putFloat(g.toFloat())
                byteBuffer.putFloat(b.toFloat())
            }
        }

        return byteBuffer
    }

    private fun getSortedResult(labelProbArray: Array<FloatArray>): List<Recognition> {
        val pq = PriorityQueue(
            MAX_RESULTS,
            Comparator<Recognition> { r1, r2 ->
                java.lang.Float.compare(r2.confidence, r1.confidence)
            }
        )

        for (i in LABEL_LIST.indices) {
            val confidence = labelProbArray[0][i]

            if (confidence >= THRESHOLD) {
                pq.add(
                    Recognition(
                        id = i.toString(),
                        title = if (LABEL_LIST.size > i) LABEL_LIST[i] else "Unknown",
                        confidence = confidence
                    )
                )
            }
        }

        val recognitions = ArrayList<Recognition>()
        val recognitionsSize = Math.min(pq.size, MAX_RESULTS)

        for (i in 0 until recognitionsSize) {
            recognitions.add(pq.poll())
        }

        return recognitions
    }
}