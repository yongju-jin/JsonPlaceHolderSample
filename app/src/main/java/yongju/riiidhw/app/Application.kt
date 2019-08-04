package yongju.riiidhw.app

import android.app.Application
import com.facebook.stetho.Stetho
import yongju.riiidhw.BuildConfig

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) run {
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build()
            )
        }
    }
}