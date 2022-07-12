package com.pipe.codebox.data.common

import android.annotation.SuppressLint
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.pipe.codebox.data.models.HealthDataResult
import com.pipe.codebox.data.request.DataProvider
import com.pipe.codebox.domain.entity.HealthData
import com.pipe.codebox.extensions.APP_TAG
import com.pipe.codebox.extensions.HEALTH_DATA_COLLECTION
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class FirestoreDataProvider @Inject constructor(
    private val store: FirebaseFirestore
) : DataProvider {

    override suspend fun subscribeToHealthData(): HealthDataResult =
        suspendCoroutine { continuation ->
            var result: HealthDataResult
            try {
                store.collection(HEALTH_DATA_COLLECTION)
                    .get()
                    .addOnCompleteListener { task ->
                        val healthDataList: MutableList<HealthData> = mutableListOf()
                        if (task.isSuccessful) {
                            for (documents in task.result) {
                                Log.d(APP_TAG, documents.id + " => " + documents.data)
                                healthDataList.add(documents.toObject(HealthData::class.java))
                            }
                            result = HealthDataResult.Success(healthDataList)
                            continuation.resume(result)
                        } else {
                            Log.w(APP_TAG, "Error getting documents.", task.exception)
                            result = HealthDataResult.Error(Throwable(task.exception))
                            continuation.resume(result)
                        }
                    }
            } catch (e: Throwable) {
                result = HealthDataResult.Error(e)
                continuation.resume(result)
            }
        }


    override suspend fun saveHealthData(healthData: HealthData): HealthData =
        suspendCoroutine { continuation ->
            try {
                store.collection(HEALTH_DATA_COLLECTION)
                    .add(healthData)
                    .addOnSuccessListener { documentReference ->
                        Log.i(APP_TAG, "DocumentSnapshot added with ID: " + documentReference.id)
                        continuation.resume(healthData)
                    }
                    .addOnFailureListener(OnFailureListener { e ->
                        Log.i(APP_TAG, "Error adding document", e)
                        continuation.resumeWithException(e)
                    })
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }

    override suspend fun deleteHealthData(id: Long): Unit = suspendCoroutine { continuation ->
        try {
            val response = store.collection(HEALTH_DATA_COLLECTION)
            response.get().addOnSuccessListener { task ->
                for (ruta in task.documents) {
                    if ((ruta.toObject(HealthData::class.java))?.id == id){
                        response.document(ruta.id).delete()
                            .addOnSuccessListener { documentReference ->
                                Log.i(APP_TAG, "DocumentSnapshot added with ID: $documentReference")
                                continuation.resume(Unit)
                            }
                            .addOnFailureListener { e ->
                                Log.i(APP_TAG, "Error adding document", e)
                                continuation.resumeWithException(e)
                            }
                        return@addOnSuccessListener
                    }
                }
            }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun getHealthDataById(id: String): HealthData =
        suspendCoroutine { continuation ->
            try {
                store.collection(HEALTH_DATA_COLLECTION)
                    .document(id)
                    .get()
                    .addOnSuccessListener { documentReference ->
                        Log.i(APP_TAG, "DocumentSnapshot added with ID: $documentReference")
                        val note: HealthData? =
                            documentReference.toObject(HealthData::class.java)
                        if (note != null) continuation.resume(note)
                    }
                    .addOnFailureListener(OnFailureListener { e ->
                        Log.i(APP_TAG, "Error adding document", e)
                        continuation.resumeWithException(e)
                    })
            } catch (e: Throwable) {
                continuation.resumeWithException(e)
            }
        }
}