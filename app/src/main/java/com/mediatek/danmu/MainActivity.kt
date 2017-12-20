package com.mediatek.danmu

import android.content.pm.ActivityInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import kotterknife.bindView
import tv.danmaku.ijk.media.example.widget.media.IRenderView
import tv.danmaku.ijk.media.example.widget.media.IjkVideoView
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class MainActivity : AppCompatActivity() {

    private val TAG : String = "DanmuActivity"

    private val mVideoView : IjkVideoView by bindView(R.id.video_view)
    private val toolBar : Toolbar by bindView(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)

        playerConfig()
        startPlay()
    }

    override fun onResume() {
        super.onResume()
        if (!mVideoView.isPlaying) {
            mVideoView.start()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause!");
        if (mVideoView.isPlaying && mVideoView.canPause()) {
            mVideoView.pause();
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy!");
        if (mVideoView.isPlaying) {
            mVideoView.display
            mVideoView.release(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_app, menu)
        return true
    }

    private fun startPlay() {
        //mVideoView.setVideoURI(Uri.parse("http://vod.cntv.lxdns.com/flash/mp4video61/TMS/2017/08/17/63bf8bcc706a46b58ee5c821edaee661_h264818000nero_aac32-5.mp4"))
        mVideoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.hebe))
        mVideoView.start()
    }

    private fun playerConfig() {
        IjkMediaPlayer.loadLibrariesOnce(null)
        IjkMediaPlayer.native_profileBegin("libijkplayer.so")
        setFullScreen(true)
        mVideoView.setAspectRatio(IRenderView.AR_16_9_FIT_PARENT);
        mVideoView.setLoop(true)
    }

    private fun setFullScreen(fullScreen: Boolean) {
        if (fullScreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
