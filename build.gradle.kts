// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.compose) apply false
<<<<<<< HEAD
  alias(libs.plugins.roborazzi) apply false
=======
  alias(libs.plugins.google.devtools.ksp) apply false
  alias(libs.plugins.roborazzi) apply false
  alias(libs.plugins.secrets) apply false
  alias(libs.plugins.google.services) apply false
>>>>>>> 2fe01b030831f670b9caa8486d7e34065d6b3e14
}
