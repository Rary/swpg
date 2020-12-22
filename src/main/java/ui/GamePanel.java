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

import model.Movable;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class GamePanel extends JPanel implements DisplayListener {
    private final GameRenderer gameRenderer;

    public GamePanel(GameRenderer gameRenderer) {
        this.gameRenderer = gameRenderer;
        this.setBackground(new Color(0x000000));
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        Dimension windowSize = this.getParent().getSize();
        gameRenderer.render(graphics, windowSize.width, windowSize.height);
    }

    @Override
    public void redraw(Set<Movable> movables) {
        movables.forEach(movable -> gameRenderer.placeSprite(movable.getName(), movable.getX(), movable.getY(), movable.getPhase()));
        this.getParent().repaint();
    }
}
