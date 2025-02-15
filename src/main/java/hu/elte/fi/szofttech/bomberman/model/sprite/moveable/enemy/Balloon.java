package hu.elte.fi.szofttech.bomberman.model.sprite.moveable.enemy;

import java.util.HashSet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import hu.elte.fi.szofttech.bomberman.model.GameModel;
import hu.elte.fi.szofttech.bomberman.model.sprite.Sprite;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.MoveableSprite;
import hu.elte.fi.szofttech.bomberman.model.sprite.moveable.player.Player;
import hu.elte.fi.szofttech.bomberman.model.util.PlayerAction;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class Balloon extends MoveableSprite {

    private int otherDirection;

    public Balloon(GameModel model, Point2D imagePoint, String imagePath) {
        this(model, new PlayerAction(true, false, false, false), imagePoint, imagePath);
    }

    public Balloon(GameModel model, PlayerAction action, Point2D imagePoint, String imagePath) {
        this(model, action, imagePoint, model.getCubeSize(), imagePath, 150);
    }

    private Balloon(GameModel model, PlayerAction action, Point2D imagePoint, Dimension imageSize, String imagePath,
            int speed) {
        super(model, null, action, speed, imagePoint, null, imageSize, imagePath);
        this.areaPoint = new Point2D.Double(imagePoint.getX(),
                imagePoint.getY());
        this.area = newArea();
        setOtherDirection();
    }

    private void setOtherDirection() {
        Random rnd = new Random();
        int N = (int) (model.getBoardIndexSize().getWidth() * model.getBoardIndexSize().getHeight() * 10);
        int n = rnd.nextInt(N);
        otherDirection = n;
    }

    private Area newArea() {
        return new Area(new Rectangle2D.Double(
                imagePoint.getX() + 10, imagePoint.getY() + 2,
                model.getCubeSize().getWidth() * 0.75, model.getCubeSize().getHeight() * 0.9));
    }

    @Override
    public void move(double deltaTime) {
        Point2D previousAreaPoint = (Point2D) areaPoint.clone();
        double division = 1;
        if (action.up && action.left || action.up && action.right || action.down && action.left
                || action.down && action.right) {
            division = Math.sqrt(2);
        }

        if (action.up) {
            Point2D newAreaPoint = new Point2D.Double(areaPoint.getX(),
                    areaPoint.getY() - deltaTime * speed / division);
            Point2D newImagePoint = new Point2D.Double(imagePoint.getX(),
                    imagePoint.getY() - deltaTime * speed / division);
            Area newArea = newArea();
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint;
                imagePoint = newImagePoint;
                area = newArea;
            }
        }

        if (action.down) {
            Point2D newAreaPoint = new Point2D.Double(areaPoint.getX(),
                    areaPoint.getY() + deltaTime * speed / division);
            Point2D newImagePoint = new Point2D.Double(imagePoint.getX(),
                    imagePoint.getY() + deltaTime * speed / division);
            Area newArea = newArea();
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint;
                imagePoint = newImagePoint;
                area = newArea;
            }
        }

        if (action.left) {
            Point2D newAreaPoint = new Point2D.Double(areaPoint.getX() - deltaTime * speed / division,
                    areaPoint.getY());
            Point2D newImagePoint = new Point2D.Double(imagePoint.getX() - deltaTime * speed / division,
                    imagePoint.getY());
            Area newArea = newArea();
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint;
                imagePoint = newImagePoint;
                area = newArea;
            }
        }

        if (action.right) {
            Point2D newAreaPoint = new Point2D.Double(areaPoint.getX() + deltaTime * speed / division,
                    areaPoint.getY());
            Point2D newImagePoint = new Point2D.Double(imagePoint.getX() + deltaTime * speed / division,
                    imagePoint.getY());
            Area newArea = newArea();
            if (isMoveable(newAreaPoint)) {
                areaPoint = newAreaPoint;
                imagePoint = newImagePoint;
                area = newArea;
            }
        }
        model.changeSpriteMovementOnBoard(this, previousAreaPoint);
    }

    @Override
    public boolean isMoveable(Point2D point) {
        HashSet<Sprite> sprites = model.getBoardSprites(point, model.getCubeSize().getWidth());
        sprites.remove(this);
        for (Sprite sprite : sprites) {
            if (isIntersect(sprite, point)) {
                action.changeDirection();
                if (sprite instanceof Player) {
                    Player player = (Player) sprite;
                    if (!player.getInvulnerability()) {
                        sprite.destructor();
                    }
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void update(double deltaTime) {
        move(deltaTime);
        otherDirection--;
        if (otherDirection == 0) {
            setOtherDirection();
            action.changeDirection();
        }
    }

}