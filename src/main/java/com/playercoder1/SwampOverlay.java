package com.playercoder1;

import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Tile;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.HashSet;
import java.util.Set;

public class SwampOverlay extends Overlay
{
    private final Client client;
    private final Set<Tile> tilesToMark = new HashSet<>();

    @Inject
    SwampOverlay(Client client)
    {
        this.client = client;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    public void addTileToMark(Tile tile)
    {
        tilesToMark.add(tile);
    }

    public void removeTileToMark(Tile tile)
    {
        tilesToMark.remove(tile);
    }

    public void clearAllTiles()
    {
        tilesToMark.clear();
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        for (Tile tile : tilesToMark)
        {
            Polygon polygon = Perspective.getCanvasTilePoly(client, tile.getLocalLocation());
            if (polygon != null)
            {
                OverlayUtil.renderPolygon(graphics, polygon, Color.RED);
            }
        }
        return null;
    }
}
