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
package game;

import model.Movable;
import model.Player;
import ui.DisplayListener;

import java.util.HashSet;
import java.util.Set;

public class World {
    private final DisplayListener displayListener;
    private final Clock clock;
    private final Player player;
    private final Set<Movable> movables;

    public World(DisplayListener displayListener, Clock clock, Player player) {
        this.displayListener = displayListener;
        this.clock = clock;
        this.player = player;
        this.movables = new HashSet<>();
        movables.add(player);
    }

    public void start() {
        clock.scheduleEvent("tick", () -> displayListener.redraw(movables), 15);
    }

    public void movePlayer(int heading, int velocity) {
        player.setHeading(heading);
        player.setVelocity(velocity);
        clock.scheduleEvent("movePlayer", () -> player.setX(player.getX() + 1), 100 - player.getVelocity());
    }
}
