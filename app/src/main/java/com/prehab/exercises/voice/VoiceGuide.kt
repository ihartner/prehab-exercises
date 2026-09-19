package com.prehab.exercises.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import java.util.UUID
import kotlin.coroutines.resume

/**
 * Thin coroutine-friendly wrapper around [TextToSpeech] for spoken exercise cues.
 */
class VoiceGuide(context: Context) {

    private val readyDeferred = CompletableDeferred<Boolean>()
    private val tts: TextToSpeech = TextToSpeech(context.applicationContext) { status ->
        val success = status == TextToSpeech.SUCCESS
        if (success) tts.language = Locale.getDefault()
        readyDeferred.complete(success)
    }

    /** Suspends until the TTS engine has finished initializing. */
    suspend fun awaitReady(): Boolean = readyDeferred.await()

    /** Speaks [text] without waiting for it to finish. */
    fun speak(text: String) {
        if (!readyDeferred.isCompleted || readyDeferred.getCompleted() != true) return
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, UUID.randomUUID().toString())
    }

    /** Speaks [text] and suspends until speech playback completes. */
    suspend fun speakAndWait(text: String) {
        if (!awaitReady()) return
        val utteranceId = UUID.randomUUID().toString()
        suspendCancellableCoroutine<Unit> { continuation ->
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) = Unit
                override fun onDone(utteranceId: String?) {
                    if (continuation.isActive) continuation.resume(Unit)
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    if (continuation.isActive) continuation.resume(Unit)
                }
            })
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
            continuation.invokeOnCancellation { tts.stop() }
        }
    }

    fun stop() {
        if (readyDeferred.isCompleted) tts.stop()
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}
