package com.xaviplacidpol.blindbloodblade.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

public class SoundAssetsManager implements Disposable {

    public static final String M_BACKGROUND = "background";
    public static final String M_LEVEL_FAST = "fastlevel";
    public static final String M_LEVEL_THRILLER = "thriller";
    public static final String M_LEVEL_SAKURA = "sakura";
    public static final String M_SCORE_SCREEN = "scorescreen";

    public static final String S_SWORD_SLASH = "swordslash";
    public static final String S_BLOOD_SPLASH = "bloodsplash";
    public static final String S_JUMP = "jump";
    public static final String S_ATTACK = "attack";
    public static final String S_ATTACKING = "attacking";
    public static final String S_SPIKE_DEAD = "spiketrap";

    public static Sound bloodSplashSound;
    public static Sound swordSlashSound;
    public static Sound jumpSound;
    public static Sound attackSound;
    public static Sound attackingSound;
    public static Sound spikeTrapSound;

    public static Music sakuraAmbienceStage;
    public static Music superFastLevel;
    public static Music thrillerStage;
    public static Music backgroundMusic;
    public static Music scorescreenMusic;


    public static final Map<String, Music> bbbmusics;
    static{

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/backgroundmusic.ogg"));
        backgroundMusic.setLooping(true);

        superFastLevel = Gdx.audio.newMusic(Gdx.files.internal("sounds/nessuperfastlevel.ogg"));
        superFastLevel.setLooping(true);

        thrillerStage = Gdx.audio.newMusic(Gdx.files.internal("sounds/thrillerstage.ogg"));
        thrillerStage.setLooping(true);

        sakuraAmbienceStage = Gdx.audio.newMusic(Gdx.files.internal("sounds/sakuraambiencestage.ogg"));
        sakuraAmbienceStage.setLooping(true);

        scorescreenMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/quietambiance.ogg"));
        scorescreenMusic.setLooping(true);

        Hashtable<String, Music> musics = new Hashtable<>();
        musics.put(M_BACKGROUND, backgroundMusic);
        musics.put(M_LEVEL_FAST, superFastLevel);
        musics.put(M_LEVEL_THRILLER, thrillerStage);
        musics.put(M_LEVEL_SAKURA, sakuraAmbienceStage);
        musics.put(M_SCORE_SCREEN, scorescreenMusic);
        bbbmusics = Collections.unmodifiableMap(musics);
    }

    public static final Map<String, Sound> bbbsounds;
    static{
        bloodSplashSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bloodhitting.ogg"));
        swordSlashSound = Gdx.audio.newSound(Gdx.files.internal("sounds/swordslash.ogg"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump1.ogg"));
        attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/drawingswordfromscabbard.ogg"));
        attackingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/swordcuttingair.ogg"));
        spikeTrapSound = Gdx.audio.newSound(Gdx.files.internal("sounds/spiketrap.ogg"));

        Hashtable<String, Sound> sounds = new Hashtable<>();
        sounds.put(S_SWORD_SLASH, swordSlashSound);
        sounds.put(S_BLOOD_SPLASH, bloodSplashSound);
        sounds.put(S_JUMP, jumpSound);
        sounds.put(S_ATTACK, attackSound);
        sounds.put(S_ATTACKING, attackingSound);
        sounds.put(S_SPIKE_DEAD, spikeTrapSound);
        bbbsounds = Collections.unmodifiableMap(sounds);
    }


    @Override
    public void dispose() {
        backgroundMusic.dispose();
        bloodSplashSound.dispose();
        swordSlashSound.dispose();
        superFastLevel.dispose();
        thrillerStage.dispose();
        sakuraAmbienceStage.dispose();
        scorescreenMusic.dispose();
        jumpSound.dispose();
        attackSound.dispose();
        attackingSound.dispose();
        spikeTrapSound.dispose();
    }
}
