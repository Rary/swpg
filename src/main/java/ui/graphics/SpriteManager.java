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

import java.util.*;

public class SpriteManager {
    private final Map<String, Sprite> sprites;

    public SpriteManager() {
        this.sprites = new HashMap<>();
    }

    public Collection<Sprite> activeSprites() {
        return sprites.values();
    }

    public void placeSprite(String name, int x, int y, int phase) {
        Sprite sprite = findSprite(name);
        sprite.setX(x);
        sprite.setY(y);
        sprite.setPhase(phase);
    }

    private Sprite findSprite(String name) {
        if (! sprites.containsKey(name)) {
            throw new IllegalArgumentException(String.format("No sprite with name %s found", name));
        }
        return sprites.get(name);
    }
}
