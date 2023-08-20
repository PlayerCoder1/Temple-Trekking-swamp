package com.playercoder1;

import net.runelite.api.Client;
import net.runelite.api.GroundObject;
import net.runelite.api.Tile;
import net.runelite.api.MenuAction;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import javax.inject.Inject;

@PluginDescriptor(
		name = "Temple Trekking Swamp Helper",
		description = "Tell you which way to go in the swamp without having to check manually",
		tags = {"Temple","Trekking","Temple Trekking","Morton,Shades"}
)
public class SwampPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private SwampOverlay groundObjectMarkerOverlay;

	@Override
	protected void startUp()
	{
		overlayManager.add(groundObjectMarkerOverlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(groundObjectMarkerOverlay);
	}

	@Subscribe
	public void onGroundObjectSpawned(GroundObjectSpawned event)
	{
		GroundObject groundObject = event.getGroundObject();
		if (groundObject.getId() == 13838)
		{
			Tile tile = event.getTile();
			groundObjectMarkerOverlay.addTileToMark(tile);
		}
	}

	@Subscribe
	public void onGroundObjectDespawned(GroundObjectDespawned event)
	{
		GroundObject groundObject = event.getGroundObject();
		if (groundObject.getId() == 13838)
		{
			Tile tile = event.getTile();
			groundObjectMarkerOverlay.removeTileToMark(tile);
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (event.getMenuAction() == MenuAction.GAME_OBJECT_FIRST_OPTION &&
				(event.getId() == 13831 || event.getId() == 13832))
		{
			groundObjectMarkerOverlay.clearAllTiles();
		}
	}
}
