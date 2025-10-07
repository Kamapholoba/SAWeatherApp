package com.saweatherplus.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "Message received: ${remoteMessage.notification?.body}")
    }
    override fun onNewToken(token: String) {
        Log.d("FCM", "New token: $token")
    }
}
