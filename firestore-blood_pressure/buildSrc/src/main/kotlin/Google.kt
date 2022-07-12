@file:Suppress("SpellCheckingInspection")

object Google {

    object Material {
        private const val version = "1.6.1"
        const val material = "com.google.android.material:material:$version"
    }

    object Maps {
        private const val version = "18.0.2"
        const val gms = "com.google.android.gms:play-services-maps:$version"
    }

    object Firebase {
        private const val versionUiAuth = "8.0.1"
        private const val versionAuth = "21.0.6"
        private const val versionfirestore = "24.2.0"

        const val uiAuth = "com.firebaseui:firebase-ui-auth:$versionUiAuth"
        const val auth = "com.google.firebase:firebase-auth:$versionAuth"
        const val authKtx = "com.google.firebase:firebase-auth-ktx:$versionAuth"
        const val firestore  = "com.google.firebase:firebase-firestore:$versionfirestore"

    }

}