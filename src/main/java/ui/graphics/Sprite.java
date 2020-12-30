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
package ui.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sprite {
    private final String name;
    private final Map<String, List<BufferedImage>> images;

    private int x;
    private int y;
    private String direction;
    private int phase;

    public Sprite(String name) {
        this.name = name;
        this.images = new HashMap<>();
        this.images.put("none", new ArrayList<>());
        this.images.put("n", new ArrayList<>());
        this.images.put("ne", new ArrayList<>());
        this.images.put("e", new ArrayList<>());
        this.images.put("se", new ArrayList<>());
        this.images.put("s", new ArrayList<>());
        this.images.put("sw", new ArrayList<>());
        this.images.put("w", new ArrayList<>());
        this.images.put("nw", new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public BufferedImage image() {
        return this.images.get(direction).get(phase);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDirection() {
        return direction;
    }

    public int getPhase() {
        return phase;
    }

    public void addImage(String direction, BufferedImage image) {
        this.images.get(direction).add(image);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }
}
