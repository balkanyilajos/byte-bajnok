package hu.elte.fi.szofttech.bomberman.model.sprite.powerup;

import hu.elte.fi.szofttech.bomberman.GeneralPath;
import hu.elte.fi.szofttech.bomberman.gui.game.BoardPanel;
import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.model.sprite.Sprite;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.player.Player;
import hu.elte.fi.szofttech.bomberman.GeneralPath;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Detonator extends PowerUp {

    public Detonator(GameModel model, Point2D point) {
        this(model, point, model.getCubeSize(), new Point2D.Double(point.getX(), point.getY()),
                new Dimension((int) (model.getCubeSize().width), (int) (model.getCubeSize().height)));
    }

    public Detonator(GameModel model, Point2D areaPoint, Dimension areaSize, Point2D imagePoint, Dimension imageSize) {
        super(model,
                new Area(new Ellipse2D.Double(areaPoint.getX() + areaSize.width * 0.125,
                        areaPoint.getY() + areaSize.height * 0.125, areaSize.width * 0.75, areaSize.height * 0.75)),
                imagePoint, areaPoint, imageSize, GeneralPath.getPath() + "/picture/powerup/detonator.png", 30);
    }

    @Override
    public void effect(Player player) {
        this.player = player;
        player.setDetonator(this);
    }

    @Override
    public boolean decreaseTime(double deltaTime) {
        time = time - deltaTime;
        if (time <= 0) {
            player.unsetDetonator();
            this.player = null;
            return false;
        }
        return true;
    }
}
