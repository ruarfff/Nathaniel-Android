package com.nathaniel.game;

/*
 * This program is supposed to record the users touch input on screen and with that, move the sprite on screen
 * along an A* path. 
 * Some Useful resources
 * http://www.andengine.org/forums/development/tmx-and-touch-t1682.html
 * http://www.andengine.org/forums/updates/new-pathfinding-t1227.html
 * http://www.andengine.org/forums/tutorials/tmxtiledmapexample-w-a-pathing-t1982.html
 * http://www.andengine.org/forums/updates/new-pathfinding-t1227-20.html
 * http://www.andengine.org/forums/post14069.html#p14069
 * http://www.andengine.org/forums/post14421.html#p14421
 * 
 */
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSCounter;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.input.touch.controller.MultiTouch;
import org.anddev.andengine.extension.input.touch.controller.MultiTouchController;
import org.anddev.andengine.extension.input.touch.detector.PinchZoomDetector;
import org.anddev.andengine.extension.input.touch.detector.PinchZoomDetector.IPinchZoomDetectorListener;
import org.anddev.andengine.extension.input.touch.exception.MultiTouchException;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ClickDetector;
import org.anddev.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.anddev.andengine.input.touch.detector.SurfaceScrollDetector;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.constants.Constants;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.path.ITiledMap;
import org.anddev.andengine.util.path.astar.AStarPathFinder;

import com.nathaniel.game.entities.DrawableGameEntity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Toast;

public class GameActivity extends BaseGameActivity implements
		IOnSceneTouchListener, IScrollDetectorListener,
		IPinchZoomDetectorListener, IClickDetectorListener {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480; // 15
	private static final int CAMERA_HEIGHT = 320;
//	private static final int TILE_WIDTH = 32;
//	private static final int TILE_HEIGHT = 32;
//	private static final int DIALOG_ALLOWDIAGONAL_ID = 0;
	// Controls the speed the sprite will move at. Lower is faster
	private static final float mSpeed = (float) 0.3;

	// ===========================================================
	// Fields
	// ===========================================================

	private DrawableGameEntity _nathaniel;
<<<<<<< HEAD
=======
	private Map _map;
>>>>>>> upstream/master
	
	
	private ZoomCamera mCamera;
	private int cCounter = 0;
	// Touch scroll and pinch zoom
	private SurfaceScrollDetector mScrollDetector;
	private PinchZoomDetector mPinchZoomDetector;
	private float mPinchZoomStartedCameraZoomFactor;
	boolean clicked = false;

	// ===========================================================
	// Constructors
	// ===========================================================

	// HUD Text and HUD
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;
	HUD hud = new HUD();
	private float maxZoom;

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		// Initializes the Engine and sets the height and width
		this.mCamera = new ZoomCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// Sets the orientation of the screen along with the resolution(Multiple
		// displays will see the same image)
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		// Sets the touch interactions to get processed during the update
		// thread??
		engineOptions.getTouchOptions().setRunOnUpdateThread(true);
		// Initializes the Engine
		Engine engine = new Engine(engineOptions);
		// Attach HUD to the Camera
		mCamera.setHUD(this.hud);
		// Check for multitouch
		try {
			if (MultiTouch.isSupported(this)) {
				engine.setTouchController(new MultiTouchController());
			} else {
				Toast.makeText(
						this,
						"Sorry your device does NOT support MultiTouch!\n\n(No PinchZoom is possible!)",
						Toast.LENGTH_LONG).show();
			}
		} catch (final MultiTouchException e) {
			Toast.makeText(
					this,
					"Sorry your Android Version does NOT support MultiTouch!\n\n(No PinchZoom is possible!)",
					Toast.LENGTH_LONG).show();
		}

<<<<<<< HEAD
		
		_nathaniel = new DrawableGameEntity(this, "gfx/player.png");
		
		
=======
>>>>>>> upstream/master
		// Returns the Engine
		return engine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.anddev.andengine.ui.IGameInterface#onLoadResources() loads the
	 * reasources
	 */
	@Override
	public void onLoadResources() {
		

<<<<<<< HEAD
		_nathaniel.loadAssets();
=======
		_nathaniel = new DrawableGameEntity(this);
		_map = new Map(this);
		
>>>>>>> upstream/master
		
		// HUD text
		this.mFontTexture = new BitmapTextureAtlas(128, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = new Font(this.mFontTexture, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), 12, true, Color.BLACK);
		// this.mEngine.getTextureManager().loadTexture(this.mFontTexture);

		this.mEngine.getTextureManager().loadTextures(this.mFontTexture,
				_nathaniel.getTexture());
		this.mEngine.getFontManager().loadFont(this.mFont);
	}

	@Override
	public Scene onLoadScene() {

		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(2);
		scene.setOnAreaTouchTraversalFrontToBack();

		this.mScrollDetector = new SurfaceScrollDetector(this);
		if (MultiTouch.isSupportedByAndroidVersion()) {
			try {
				this.mPinchZoomDetector = new PinchZoomDetector(this);
			} catch (final MultiTouchException e) {
				this.mPinchZoomDetector = null;
			}
		} else {
			this.mPinchZoomDetector = null;
		}

		scene.setOnSceneTouchListener(this);
		scene.setTouchAreaBindingEnabled(true);
		
		try {
<<<<<<< HEAD
			MapHandler.setMapFromAssets(this, "tmx/desert_astar2.tmx");
			
			scene.getFirstChild().attachChild(MapHandler.getMapLayer());

			/* Make the camera not exceed the bounds of the TMXEntity. */
			this.mCamera.setBounds(0, MapHandler.getMapLayer().getWidth(), 0,
					MapHandler.getMapLayer().getHeight());
=======
			_map.setMapFromAssets("tmx/desert_astar2.tmx");
			
			
			
			scene.getFirstChild().attachChild(tmxLayer);

			/* Make the camera not exceed the bounds of the TMXEntity. */
			this.mCamera.setBounds(0, tmxLayer.getWidth(), 0,
					tmxLayer.getHeight());
>>>>>>> upstream/master
			this.mCamera.setBoundsEnabled(true);

			/*
			 * Calculate the coordinates for the face, so its centered on the
			 * camera.
			 */
<<<<<<< HEAD
			final int centerX = (CAMERA_WIDTH - _nathaniel.getTextureRegion()
					.getTileWidth()) / 2 + 10;
			final int centerY = (CAMERA_HEIGHT - _nathaniel.getTextureRegion()
=======
			final int centerX = (CAMERA_WIDTH - this.mPlayerTextureRegion
					.getTileWidth()) / 2 + 10;
			final int centerY = (CAMERA_HEIGHT - this.mPlayerTextureRegion
>>>>>>> upstream/master
					.getTileHeight()) / 2 + 10;

			// Declare the AStarPathFinder
			// First Param: above ITiledMap
			// Second Param: Max Search Depth - Care, if this is too small your
			// program will crash
			// Third Param: allow diagonal movement or not
			// Fourth Param: Heuristics you want to use in the A* algorithm
			// (optional)
			finder = new AStarPathFinder<TMXLayer>(tmap, 30, false);

			// Declare Animated sprite
			// First Param: defines the x pixel position the sprite will be
			// created at
			// Second Param: defines the y pixel position the sprite will be
			// created at
			// Third Param: Sets the texture to be used
			player = new AnimatedSprite(centerX, centerY,
					this.mPlayerTextureRegion);

			// Sets the camera to focus on the player enitity, removed to allow
			// scrolling.
			//XXX

			/*
			 * Now we are going to create a rectangle that will always highlight
			 * the tile below the feet of the pEntity.
			 */
			final Rectangle currentTileRectangle = new Rectangle(0, 0,
					this.mTMXTiledMap.getTileWidth(),
					this.mTMXTiledMap.getTileHeight());
			currentTileRectangle.setColor(1, 0, 0, 0.25f);
			scene.getLastChild().attachChild(currentTileRectangle);

			scene.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() {
				}

				@Override
				public void onUpdate(final float pSecondsElapsed) {
					/* Get the scene-coordinates of the tile center. */
					playerFootCordinates = player
							.convertLocalToSceneCoordinates(16, 16);

					/* Get the tile where the center of the current tile is. */
					tmxTile = tmxLayer.getTMXTileAt(
							playerFootCordinates[Constants.VERTEX_INDEX_X],
							playerFootCordinates[Constants.VERTEX_INDEX_Y]);
					if (tmxTile != null) {
						// tmxTile.setTextureRegion(null); <-- Rubber-style
						// removing of tiles =D
						currentTileRectangle.setPosition(tmxTile.getTileX(),
								tmxTile.getTileY());
					}
				}
			});

			// This is to make sure that you cannot pinch zoom the camera out of
			// bounds
			if (CAMERA_WIDTH / tmxLayer.getHeight() >= CAMERA_HEIGHT
					/ tmxLayer.getWidth())
				maxZoom = CAMERA_WIDTH / tmxLayer.getHeight();
			else
				maxZoom = CAMERA_HEIGHT / tmxLayer.getWidth();

			// TODO Auto-generated method stub (only here to make onSceneTouch
			// events easier to find)

			// Make a physicsHandler for player, to make it possible for the
			// OnScreenControl to move it
			final PhysicsHandler physicsHandler = new PhysicsHandler(player);
			player.registerUpdateHandler(physicsHandler);
			// Add player sprite to the scene
			scene.getLastChild().attachChild(player);

			
			// Allows the scene to be touched
			scene.setOnSceneTouchListener(this);

			// Initiate FPS counter
			final FPSCounter fpsCounter = new FPSCounter();
			this.mEngine.registerUpdateHandler(fpsCounter);

			final ChangeableText fpsText = new ChangeableText(5, 5, this.mFont,
					"FPS:", "FPS: XXXXX".length());
			// Attach FPS counter to HUD
			hud.attachChild(fpsText);

			scene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true,
					new ITimerCallback() {
						@Override
						public void onTimePassed(
								final TimerHandler pTimerHandler) {
							fpsText.setText("FPS: " + fpsCounter.getFPS());
						}
					}));

		} catch (Exception e) {
		}

		return scene;
	}

	@Override
	public void onLoadComplete() {
		clicked = false;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		// of the user pinches or dragtouches the screen then...
		if (this.mPinchZoomDetector != null) {
			this.mPinchZoomDetector.onTouchEvent(pSceneTouchEvent);

			if (this.mPinchZoomDetector.isZooming()) {
				this.mScrollDetector.setEnabled(false);
			} else {
				if (pSceneTouchEvent.isActionDown()) {
					this.mScrollDetector.setEnabled(true);
				}
				this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
			}
		} else {
			this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
		}

		// TODO Auto-generated method stub (only here to make onSceneTouch
		// events easier to find)

		// if the user touches the screen without scrolling or pinching...
		if (pSceneTouchEvent.isActionUp()) {
			if (clicked == true) {

				// Calls the Walk to method, which has two parameters
				// First & Second Param: are the touched x and y position
				// Third Param: is the scene
				walkTo(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), pScene);
			}
			clicked = true;
		}

		return true;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// This method controls the path the sprite will take once a location is
	// passed into it
	public void walkTo(final float pX, final float pY, Scene pScene) {
		this.mCamera.setChaseEntity(player);
		// Puts the touch events into an array
		final float[] pToTiles = pScene.convertLocalToSceneCoordinates(pX, pY);

		// Gets the tile at the touched location

		final TMXTile tmxTilePlayerTo = tmxLayer.getTMXTileAt(
				pToTiles[Constants.VERTEX_INDEX_X],
				pToTiles[Constants.VERTEX_INDEX_Y]);

		if (tmxTilePlayerTo != null) {

			/*********/
			// if is walking and there is a A_path ******************
			if (isWalking == true && A_path != null) {
				// Calls the Walk to next waypoint method,
				// First & Second Param: are the touched x and y position
				// Third Param: is the scene
				walkToNextWayPoint(pX, pY, pScene);
			}

			else if (A_path == null) {
				// Create the path with findPath()
				// First Param: TMXLayer in this case, templated through the
				// above ITiledMap and AStarPathFinder functions
				// Second Param: Max Cost of a complete path, NOT A SINGLE TILE!
				// - if value is too low your program will crash
				// Third & Fourth Param: From Column and From Row - Starting
				// tile location
				// Fifth & Sixth Param: To Column and To Row - Ending tile
				// location
				A_path = finder.findPath(tmxLayer, 100,
						// Sprite's initial tile location
						tmxTile.getTileColumn(), tmxTile.getTileRow(),
						// Sprite's final tile location
						tmxTilePlayerTo.getTileColumn(),
						tmxTilePlayerTo.getTileRow());

				// Calls the Load path method
				loadPathFound();
			}
		}
	}

	// If the sprite is moving and the screen is touched then this method will
	// be called.
	// It creates a sub path that creates a smooth transition to the center of a
	// tile before rerouting
	private void walkToNextWayPoint(final float pX, final float pY,
			final Scene pScene) {
		// Unregisters the current paths
		player.unregisterEntityModifier(mMoveModifier);
		player.unregisterEntityModifier(mPathTemp); // mPathTemp is another
													// global PathModifier

		// Creates a copy of the path currently being walked
		final Path lPath = mCurrentPath.clone();

		// create a new path with length 2 from current sprite position to next
		// original path waypoint
		final Path path = new Path(2);
		// Continues the path for one more tile
		path.to(player.getX(), player.getY()).to(
				lPath.getCoordinatesX()[mWaypointIndex + 1],
				lPath.getCoordinatesY()[mWaypointIndex + 1]);

		// recalculate the speed. TILE_WIDTH is the tmx tile width, use yours
		float lSpeed = path.getLength() * mSpeed / (mTMXTiledMap.getTileWidth());

		// Create the modifier of this subpath
		mPathTemp = new PathModifier(lSpeed, path,
				new IEntityModifierListener() {

					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {

					}

					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {

					}
				}, new IPathModifierListener() {

					public void onPathWaypointStarted(
							final PathModifier pPathModifier,
							final IEntity pEntity, int pWaypointIndex) {

					}

					public void onPathWaypointFinished(
							PathModifier pPathModifier, IEntity pEntity,
							int pWaypointIndex) {

					}

					public void onPathStarted(PathModifier pPathModifier,
							IEntity pEntity) {

					}

					public void onPathFinished(PathModifier pPathModifier,
							IEntity pEntity) {
						// Once the subpath is finished the a new one is created
						// at the new touched location
						A_path = null;
						walkTo(pX, pY, pScene);
						// Stops the animation of the player once it stops
						player.stopAnimation();
					}
				});
		// Registers the newly created path modifier
		player.registerEntityModifier(mPathTemp);
	}

	// Creates the A* Path and executes it
	private void loadPathFound() {

		if (A_path != null) {
			// Global var
			mCurrentPath = new Path(A_path.getLength());
			int tilewidth = mTMXTiledMap.getTileWidth();
			int tileheight = mTMXTiledMap.getTileHeight();

			for (int i = 0; i < A_path.getLength(); i++) {
				mCurrentPath.to(A_path.getTileColumn(i) * tilewidth,
						A_path.getTileRow(i) * tileheight);
			}
			doPath();
		}
	}

	// Creates the modifier for the A*Path
	private void doPath() {

		// Create this mMoveModifier as Global, there is mSpeed too -> player
		// speed
		mMoveModifier = new PathModifier(mSpeed * A_path.getLength(),
				mCurrentPath, new IEntityModifierListener() {

					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {

					}

					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {

					}
				}, new PathModifier.IPathModifierListener() {

					public void onPathWaypointStarted(
							final PathModifier pPathModifier,
							final IEntity pEntity, int pWaypointIndex) {

						// Character animation are controlled here
						switch (A_path.getDirectionToNextStep(pWaypointIndex)) {
						case DOWN:
							player.animate(new long[] { 200, 200, 200 }, 6, 8,
									true);
							break;
						case RIGHT:
							player.animate(new long[] { 200, 200, 200 }, 3, 5,
									true);
							break;
						case UP:
							player.animate(new long[] { 200, 200, 200 }, 0, 2,
									true);
							break;
						case LEFT:
							player.animate(new long[] { 200, 200, 200 }, 9, 11,
									true);
							break;
						default:
							break;
						}

						// Keep the waypointIndex in a Global Var
						mWaypointIndex = pWaypointIndex;

					}

					public void onPathWaypointFinished(
							PathModifier pPathModifier, IEntity pEntity,
							int pWaypointIndex) {

					}

					public void onPathStarted(PathModifier pPathModifier,
							IEntity pEntity) {

						// Set a global var
						isWalking = true;
					}

					public void onPathFinished(PathModifier pPathModifier,
							IEntity pEntity) {

						// Stop walking and set A_path to null
						isWalking = false;
						A_path = null;
						player.stopAnimation();
					}

				});

		player.registerEntityModifier(mMoveModifier);
	}

	// Check for scroll, and scroll the camera
	@Override
	public void onScroll(final ScrollDetector pScollDetector,
			final TouchEvent pTouchEvent, final float pDistanceX,
			final float pDistanceY) {
		this.mCamera.setChaseEntity(null);
		final float zoomFactor = this.mCamera.getZoomFactor();
		this.mCamera.offsetCenter(-pDistanceX / zoomFactor, -pDistanceY
				/ zoomFactor);
		clicked = false; // stop the sprite from walking to the touched area
							// when scrolling
	}

	// Pinch zoom controllers
	@Override
	public void onPinchZoomStarted(final PinchZoomDetector pPinchZoomDetector,
			final TouchEvent pTouchEvent) {
		this.mPinchZoomStartedCameraZoomFactor = this.mCamera.getZoomFactor();
		clicked = false; // stop the sprite from walking to the touched area
							// when zooming
	}

	@Override
	public void onPinchZoom(final PinchZoomDetector pPinchZoomDetector,
			final TouchEvent pTouchEvent, final float pZoomFactor) {
		// zoom, but make sure the camera does not zoom outside the TMX bounds
		// by using maxZoom
		this.mCamera.setZoomFactor(Math.min(
				Math.max(maxZoom, this.mPinchZoomStartedCameraZoomFactor
						* pZoomFactor), 2));
		clicked = false; // stop the sprite from walking to the touched area
							// when zooming
	}

	@Override
	public void onPinchZoomFinished(final PinchZoomDetector pPinchZoomDetector,
			final TouchEvent pTouchEvent, final float pZoomFactor) {
		// zoom, but make sure the camera does not zoom outside the TMX bounds
		// by using maxZoom
		this.mCamera.setZoomFactor(Math.min(
				Math.max(maxZoom, this.mPinchZoomStartedCameraZoomFactor
						* pZoomFactor), 2));
		clicked = false; // stop the sprite from walking to the touched area
							// when zooming
	}

	@Override
	public void onClick(ClickDetector pClickDetector, TouchEvent pTouchEvent) {
		// TODO Auto-generated method stub
		// I thought I could use this, but not for click moving on click, but it
		// did not work.
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}