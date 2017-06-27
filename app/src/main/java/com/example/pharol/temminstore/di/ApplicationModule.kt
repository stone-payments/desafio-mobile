package pharol.com.br.temminstore.di

import android.app.Application
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule( val mApplication : Application){

    @Provides
    fun provideApplication(): Application = mApplication
}
