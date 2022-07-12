@file:Suppress("SpellCheckingInspection")

object Google {

    object Material {
        /**
         * @see <a href="https://material.io/develop/android/docs/getting-started">Material docs</a>
         */
        private const val version = "1.6.1"
        const val material = "com.google.android.material:material:$version"
    }

    object Maps {
        /**
         * @see <a href="https://developers.google.com/android/reference/com/google/android/gms/common/package-summary">Maps docs</a>
         */
        private const val version = "18.0.2"
        const val gms = "com.google.android.gms:play-services-maps:$version"
    }
}