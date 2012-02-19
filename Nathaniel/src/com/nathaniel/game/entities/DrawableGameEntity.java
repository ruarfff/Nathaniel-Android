package com.nathaniel.game.entities;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.path.astar.AStarPathFinder;

<<<<<<< HEAD
import com.nathaniel.game.ImageHandler;

=======
>>>>>>> upstream/master
import android.content.Context;

public class DrawableGameEntity {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
<<<<<<< HEAD

	private org.anddev.andengine.util.path.Path A_path;
	private AStarPathFinder<TMXLayer> finder;
	private float[] playerFootCordinates;
	private Path _currentPath;
	private boolean _isWalking;
	private int _waypointIndex;
	private PathModifier _moveModifier;
	private IEntityModifier _pathTemp;
	
	
	private AnimatedSprite entitySprite;
=======
	private BitmapTextureAtlas mTexture;
	private TiledTextureRegion mPlayerTextureRegion;

	private AnimatedSprite player;
	private org.anddev.andengine.util.path.Path A_path;
	private AStarPathFinder<TMXLayer> finder;
	private float[] playerFootCordinates;
	private Path mCurrentPath;
	private boolean isWalking;
	private int mWaypointIndex;
	private PathModifier mMoveModifier;
	private IEntityModifier mPathTemp;
>>>>>>> upstream/master
	
	//private String 
	
	private Context _context;
<<<<<<< HEAD
	private String _pathToSpriteSheet;
	private TiledTextureRegion _entityTextureRegion;
	// ===========================================================
	// Constructors
	// ===========================================================
	public DrawableGameEntity(Context context, String pathToSpriteSheet) {
			_context = context;
			_pathToSpriteSheet = pathToSpriteSheet;
=======
	// ===========================================================
	// Constructors
	// ===========================================================
	public DrawableGameEntity(Context context, String...assetPaths) {
			_context = context;
			for(String assetPath : assetPaths){
				
			}
>>>>>>> upstream/master
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
<<<<<<< HEAD
	public TiledTextureRegion getTextureRegion(){
		return _entityTextureRegion;
=======
	public BitmapTextureAtlas getTexture(){
		return mTexture;
>>>>>>> upstream/master
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
<<<<<<< HEAD
	public void loadAssets(){
		_entityTextureRegion = ImageHandler.loadTextureRegion(_context, _pathToSpriteSheet, 128, 128, 0, 0, 3, 4);
		
=======
	public void loadTexture() {
		this.mTexture = new BitmapTextureAtlas(128, 128, TextureOptions.DEFAULT);

		this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mTexture, _context, "gfx/player.png", 0,
						0, 3, 4); // 72x128
>>>>>>> upstream/master
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
