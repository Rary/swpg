/*
 * MIT License
 *
 * Copyright (c) 2020 Rarysoft Enterprises
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ui;

import ui.graphics.Sprite;
import ui.graphics.SpriteManager;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class GameRenderer {
    private final SpriteManager spriteManager;
    private final double preferredWidth;
    private final double preferredHeight;

    public GameRenderer(SpriteManager spriteManager, double preferredWidth, double preferredHeight) {
        this.spriteManager = spriteManager;
        this.preferredWidth = preferredWidth;
        this.preferredHeight = preferredHeight;
    }

    public void placeSprite(String name, int x, int y, int phase) {
        spriteManager.placeSprite(name, x, y, phase);
    }

    public void render(Graphics graphics, int windowWidth, int windowHeight) {
        AffineTransform originalTransform = scaleToWindowSize(graphics, windowWidth, windowHeight);
        for (Sprite sprite : spriteManager.activeSprites()) {
            graphics.drawImage(sprite.image(), sprite.getX(), sprite.getY(), null);
        }
        restoreOriginalTransform(graphics, originalTransform);
    }

    private AffineTransform scaleToWindowSize(Graphics graphics, int windowWidth, int windowHeight) {
        double xScale = windowWidth / preferredWidth;
        double yScale = windowHeight / preferredHeight;
        Graphics2D graphics2D = (Graphics2D) graphics;
        AffineTransform originalTransform = graphics2D.getTransform();
        AffineTransform transform = graphics2D.getTransform();
        transform.scale(xScale, yScale);
        graphics2D.setTransform(transform);
        return originalTransform;
    }

    private void restoreOriginalTransform(Graphics graphics, AffineTransform originalTransform) {
        ((Graphics2D) graphics).setTransform(originalTransform);
    }
}
