package com.pyxis.androidAgilelyTimer;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


public class SoundManager {
	
	static private SoundManager _instance;
	private static SoundPool mSoundPool; 
	private static HashMap<Integer, Integer> mSoundPoolMap; 
	private static AudioManager  mAudioManager;
	private static Context mContext;
	
	private SoundManager()
	{   
	}
	
	/**
	 * Requests the instance of the Sound Manager and creates it
	 * if it does not exist.
	 * 
	 * @return Returns the single instance of the SoundManager
	 */
	static synchronized public SoundManager getInstance() 
	{
	    if (_instance == null) 
	      _instance = new SoundManager();
	    return _instance;
	 }
	
	/**
	 * Initialises the storage for the sounds
	 * 
	 * @param theContext The Application context
	 */
	public static  void initSounds(Context theContext) 
	{ 
		 mContext = theContext;
	     mSoundPool = new SoundPool(4, AudioManager.STREAM_ALARM, 0);
	     mSoundPoolMap = new HashMap<Integer, Integer>(); 
	     mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE); 	    
	} 
	
	/**
	 * Add a new Sound to the SoundPool
	 * 
	 * @param Index - The Sound Index for Retrieval
	 * @param SoundID - The Android ID for the Sound asset.
	 */
	public static void addSound(int Index,int SoundID)
	{
        if(mSoundPoolMap !=null )
		    mSoundPoolMap.put(Index, mSoundPool.load(mContext, SoundID, 1));
	}
	
	/**
	 * Loads the various sound assets
	 * Currently hardcoded but could easily be changed to be flexible.
	 */
	public static void loadSounds()
	{
        if(mSoundPoolMap !=null && mSoundPool != null ){
    		mSoundPoolMap.put(1, mSoundPool.load(mContext, com.pyxis.androidAgilelyTimer.R.raw.smallwineglassshort, 1));
            mSoundPoolMap.put(2, mSoundPool.load(mContext, com.pyxis.androidAgilelyTimer.R.raw.clocktickingshort, 1));
        }

	}
	
	/**
	 * Plays a Sound
     * @param index blabla
     * @param loop  blabla
     */
	public static void playSound(int index,int loop)
	{ 		
         float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
         streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        if(mSoundPoolMap !=null && mSoundPool != null ){
             mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, loop, 1);
        }
	}
	
	/**
	 * Stop a Sound
	 * @param index - index of the sound to be stopped
	 */
	public static void stopSound(int index)
	{
        if(mSoundPoolMap !=null && mSoundPool != null ){
            mSoundPool.stop(mSoundPoolMap.get(index));
        }
	}
	
	public static void cleanup()
	{
        if(mSoundPoolMap !=null && mSoundPool != null ){
            mSoundPool.release();
            mSoundPool = null;
            mSoundPoolMap.clear();
        }
	    mAudioManager.unloadSoundEffects();
	    _instance = null;
	    
	}

	
}