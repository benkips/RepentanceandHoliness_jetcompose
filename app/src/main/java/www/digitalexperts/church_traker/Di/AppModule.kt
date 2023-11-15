package www.digitalexperts.church_traker.Di

import android.content.Context
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import www.digitalexperts.church_traker.BackgroundServices.MusicNotificationManager
import www.digitalexperts.church_traker.BackgroundServices.MusicServiceHandler
import www.digitalexperts.church_traker.Network.ApiInterface
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okclients = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder().client(okclients)
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun providesApiInterface(retrofit: Retrofit):ApiInterface=
        retrofit.create(ApiInterface::class.java)


    @Provides
    @Singleton
    @UnstableApi
    fun provideExoPlayer(
        @ApplicationContext context: Context
    ): ExoPlayer = ExoPlayer.Builder(context)
        .setHandleAudioBecomingNoisy(true)
        .setTrackSelector(DefaultTrackSelector(context))
        .build()

    @Provides
    @Singleton
    fun providesMediaSession(
        @ApplicationContext context: Context,
        exoPlayer: ExoPlayer
    ): MediaSession = MediaSession.Builder(context, exoPlayer)
        .build()

    @Provides
    @Singleton
    fun providesMusicNotificationManager(
        @ApplicationContext context: Context,
        exoPlayer: ExoPlayer
    ): MusicNotificationManager = MusicNotificationManager(
        context = context,
        exoPlayer = exoPlayer
    )

    @Provides
    @Singleton
    fun providesMusicServiceHandler(
        exoPlayer: ExoPlayer
    ): MusicServiceHandler =MusicServiceHandler(exoPlayer)





}