@file:Suppress("SpellCheckingInspection")

object AndroidX {

    object Lifecycle {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/lifecycle"> Lifecycle Doc</a>
         */
        private const val version = "2.4.1"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx::$version"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
    }

    object Fragment {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/fragment"> Fragment Doc</a>
         */

        private const val version = "1.4.1"
        const val fragment = "androidx.fragment:fragment-ktx:$version"
    }

    object Activity {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/activity"> Activity Doc</a>
         */

        private const val version = "1.4.0"
        const val activity = "androidx.activity:activity-ktx:$version"
    }

    object Core {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/core"> Core Doc</a>
         */

        private const val version = "1.8.0"
        const val core = "androidx.core:core-ktx:$version"
    }

    object Appcompat {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/appcompat"> Appcompat Doc</a>
         */

        private const val version = "1.4.2"
        const val appcompat = "androidx.appcompat:appcompat:$version"
    }

    object ConstraintLayout {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/constraintlayout"> Appcompat Doc</a>
         */

        private const val version = "2.1.4"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$version"
    }

    object Room {
        /**
         * @see <a href="https://developer.android.com/jetpack/androidx/releases/room"> Room Doc</a>
         */
        private const val version = "2.4.2"
        const val runtime = "androidx.room:room-runtime:$version"
        const val compiler = "androidx.room:room-compiler:$version"
        const val ktx = "androidx.room:room-ktx:$version"
    }
}