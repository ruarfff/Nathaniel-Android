package com.nathaniel.game;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.path.ITiledMap;

import android.content.Context;

public class Map {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private Context _context;
	private TMXTiledMap _tmxTiledMap;
	private TMXLayer _tmxLayer;
	private TMXTile _tmxTile;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Map(Context context) {
		_context = context;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public TMXTiledMap getMap() {
		return _tmxTiledMap;
	}

	public void setMapFromAssets(String pathToMap) {
		try {
			TMXLoader tmxLoader = new TMXLoader(_context,
					((GameActivity) _context).getEngine().getTextureManager(),
					TextureOptions.BILINEAR_PREMULTIPLYALPHA,
					new ITMXTilePropertiesListener() {
						@Override
						public void onTMXTileWithPropertiesCreated(
								final TMXTiledMap pTMXTiledMap,
								final TMXLayer pTMXLayer,
								final TMXTile pTMXTile,
								final TMXProperties<TMXTileProperty> pTMXTileProperties) {
							if (pTMXTileProperties.containsTMXProperty(
									"Collide", "true")) {
								// I tried to put the Tile ids in an array here
								// but the program kept hanging
							}
						}
					});

			_tmxTiledMap = tmxLoader.loadFromAsset(_context, pathToMap);
			_tmxTiledMap = setTiledMap(_tmxTiledMap);
			// Add the non-object layers to the scene
			_tmxLayer = this._tmxTiledMap.getTMXLayers().get(0);

		} catch (Exception e) {

		}
	}

	private TMXTiledMap setTiledMap(final TMXTiledMap tmxMap) {
		// These must be defined for the findpath() method to work
		final ITiledMap<TMXLayer> tmap = new ITiledMap<TMXLayer>() {

			// Pretty self explanatory
			@Override
			public int getTileColumns() {
				return tmxMap.getTileColumns();
			}

			// Pretty self explanatory
			@Override
			public int getTileRows() {
				return tmxMap.getTileRows();
			}

			// Lets you customize what blocks you want to be considered
			// blocked
			@Override
			public boolean isTileBlocked(TMXLayer pTile,
					final int pToTileColumn, final int pToTileRow) {
				// This is the array of all the tiles that can be collided
				// with
				// I added them manually. It makes it easier if you've
				// organized your tile maps
				// since you wouldn't need the for loop
				final int[] CollideTiles = { 31, 32, 47 };
				TMXTile block = _tmxLayer.getTMXTile(pToTileColumn, pToTileRow);
				for (int tempCollide : CollideTiles) {
					if (block.getGlobalTileID() == tempCollide) {
						return true;
					}
				}
				// Return false by default = no tiles blocked
				return false;
			}

			// This is the key function to understand for AStar pathing
			// Returns the cost of the next tile in the path
			@Override
			public float getStepCost(final TMXLayer pTile,
					final int pFromTileColumn, final int pFromTileRow,
					final int pToTileColumn, final int pToTileRow) {
				// I coded this it because it caused more problems than it
				// was worth, but this is where the path value is decided

				// grab the first property from a tile at pToTileColumn x
				// pToTileRow
				TMXProperty cost = _tmxLayer
						.getTMXTile(pToTileColumn, pToTileRow)
						.getTMXTileProperties(tmxMap).get(0);
				// Gets the value of the string
				// return Float.parseFloat(cost.getValue());
				return 0;

			}

			// If the tile is processed by findpath(), any extra code you
			// might want goes here
			@Override
			public void onTileVisitedByPathFinder(int pTileColumn, int pTileRow) {
				// Do Nothing
			}
		};

		return tmxMap;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
