object AndroidSdk {
  const val min = 23
  const val compile = 28
  const val target = compile
  const val buildTools = "28.0.3"
}

object Versions {
  const val kotlin = "1.3.31"

  //Plugins
  const val androidBuildTools = "3.4.1"
  const val versions = "0.21.0"

  //Common
  const val crashlytics = "2.2.3"
  const val picasso = "2.71828"
  const val leakCanary = "1.6.3"
  const val timber = "4.7.1"
  const val javaxInject = "1"
  const val javaxAnnotation = "1.0"

  //Android
  const val jetpack = "1.1.0"
  const val recyclerView = "1.0.0"
  const val androidAnnotations = "1.0.0"
  const val materialDesign = "1.1.0-alpha06"
  const val constraintLayout = "2.0.0-beta1"
  const val ktx = "1.0.1"
  const val room = "2.1.0-alpha07"
  const val navigation = "2.1.0-alpha04"
  const val lifecycle = "2.2.0-alpha01"

  //Dagger
  const val dagger = "2.22.1"

  //RxJava
  const val rxJava = "2.2.8"
  const val rxAndroid = "2.1.1"
  const val rxKotlin = "2.1.0"
  const val rxRelay = "2.1.0"

  //Network
  const val moshi = "1.4.0"
  const val okhttp = "3.9.1"
  const val retrofit = "2.3.0"

  //Testing
  const val junit = "4.13-beta-3"
  const val mockito = "2.27.0"
  const val mockitoKt = "2.1.0"
  const val robolectric = "3.8"
  const val robolectricSupport = "3.8"
  const val espresso = "3.2.0-beta01"
  const val assertJ = "3.12.2"
}

object ProjectModules {
  const val core = ":core"
  const val domain = ":domain"
  const val data = ":data"
}

object BuildPlugins {
  const val androidGradle = "com.android.tools.build:gradle:${Versions.androidBuildTools}"
  const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
  const val gradleVersions = "com.github.ben-manes.versions"
  const val safeArgsPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"

  const val androidApplication = "com.android.application"
  const val androidLibrary = "com.android.library"
  const val kotlin = "kotlin"
  const val kotlinAndroid = "kotlin-android"
  const val kotlinAndroidExtensions = "kotlin-android-extensions"
  const val kotlinKapt = "kotlin-kapt"
  const val safeArgs = "androidx.navigation.safeargs.kotlin"
}

object CommonDependencies {
  const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}@aar"
  const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
  const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
  const val leakCanaryFragment = "com.squareup.leakcanary:leakcanary-support-fragment:${Versions.leakCanary}"
  const val leakCanaryNoOp = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakCanary}"
  const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
  const val javaxInject = "javax.inject:javax.inject:${Versions.javaxInject}"
  const val javaxAnnotation = "javax.annotation:jsr250-api:${Versions.javaxAnnotation}"
}

object AndroidDependencies {
  const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
  const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
  const val androidAnnotations = "androidx.annotation:annotation:${Versions.androidAnnotations}"
  const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
  const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
  const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
  const val navigation = "androidx.navigation:navigation-runtime:${Versions.navigation}"
  const val navigationFragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
  const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
  const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
  const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
  const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
  const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
  const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
  const val lifecycleReactiveStreams = "android.arch.lifecycle:reactivestreams:${Versions.lifecycle}"
  const val roomRuntime = "android.arch.persistence.room:runtime:${Versions.room}"
  const val roomCompiler = "android.arch.persistence.room:compiler:${Versions.room}"
  const val roomRxJava = "android.arch.persistence.room:rxjava2:${Versions.room}"
}

object KotlinDependencies {
  const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
}

object DaggerDependencies {
  const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
  const val daggerProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
  const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
  const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
  const val daggerSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
}

object RxJavaDependencies {
  const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
  const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
  const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
  const val rxRelay = "com.jakewharton.rxrelay2:rxrelay:${Versions.rxRelay}"
}

object NetworkDependencies {
  const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
  const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
  const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
  const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
  const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
  const val retrofitRxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
}

object TestingDependencies {
  const val archTesting = "android.arch.core:core-testing:${Versions.lifecycle}"
  const val mockitoKt = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKt}"
  const val junit = "junit:junit:${Versions.junit}"
  const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
  const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockito}"
  const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"
  const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
  const val robolectricShadowsSupport = "org.robolectric:shadows-supportv4:${Versions.robolectricSupport}"
  const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
  const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
  const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
  const val assertJ = "org.assertj:assertj-core:${Versions.assertJ}"
  const val roomTesting = "android.arch.persistence.room:testing:${Versions.room}"
  const val runner = "androidx.test:runner:${Versions.jetpack}"
  const val rules = "androidx.test:rules:${Versions.jetpack}"
  const val androidJUnit = "androidx.test.ext:junit:${Versions.jetpack}"
}