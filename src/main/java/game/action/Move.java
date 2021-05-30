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
package game.action;

import game.Clock;
import model.Direction;
import model.Movable;
import model.State;

public class Move implements Action {
    private final Clock clock;
    private final State state;
    private final Movable movable;

    public Move(Clock clock, State state, Movable movable) {
        this.clock = clock;
        this.state = state;
        this.movable = movable;
    }

    @Override
    public void act() {
        if (movable.getVelocity() == 0) {
            state.inputDirection(Direction.NONE);
            clock.stopAction(Action.MOVE_PLAYER);
            return;
        }
        Direction direction = state.inputDirection();
        movable.setDirection(direction);
        clock.scheduleAction(Action.MOVE_PLAYER, () -> {
            int deltaX = movable.getDirection().deltaX();
            int deltaY = movable.getDirection().deltaY();
            movable.changeX(deltaX);
            movable.changeY(deltaY);
        }, clock.ONE_SECOND / Math.min(movable.getVelocity(), clock.ONE_SECOND));
    }
}
