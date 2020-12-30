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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpriteManager {
    private final Map<String, Sprite> sprites;

    public SpriteManager() {
        this.sprites = new HashMap<>();
    }

    public void load(String directory) {
        File spritesDirectory = new File(String.format("%s/%s", this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), directory));
        if (! spritesDirectory.isDirectory()) {
            throw new RuntimeException("Missing sprites directory");
        }
        Sprite sprite = null;
        for (File spriteFile : Objects.requireNonNull(spritesDirectory.listFiles())) {
            String filename = spriteFile.getName();
            String[] tokens = filename.split("-");
            if (tokens.length < 2) {
                throw new RuntimeException(String.format("Unrecognizable sprite filename format: %s", filename));
            }
            String name = tokens[0].toLowerCase();
            String direction = tokens[1].toLowerCase();
            if (sprite == null || ! sprite.getName().equals(name)) {
                sprite = new Sprite(name);
                sprites.put(name, sprite);
            }
            try {
                BufferedImage image = ImageIO.read(spriteFile);
                sprite.addImage(direction, image);
            }
            catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    public Collection<Sprite> activeSprites() {
        return sprites.values();
    }

    public void placeSprite(String name, int x, int y, String direction, int phase) {
        Sprite sprite = findSprite(name);
        sprite.setX(x);
        sprite.setY(y);
        sprite.setDirection(direction);
        sprite.setPhase(phase);
    }

    private Sprite findSprite(String name) {
        if (! sprites.containsKey(name)) {
            throw new IllegalArgumentException(String.format("No sprite with name %s found", name));
        }
        return sprites.get(name);
    }
}
