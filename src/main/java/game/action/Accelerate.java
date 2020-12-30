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
import model.Movable;
import model.State;

public class Accelerate implements Action {
    private final Clock clock;
    private final State state;
    private final int acceleration;
    private final int maxVelocity;
    private final Movable movable;
    private final Action moveAction;

    public Accelerate(Clock clock, State state, int acceleration, int maxVelocity, Movable movable, Action moveAction) {
        this.clock = clock;
        this.state = state;
        this.acceleration = acceleration;
        this.maxVelocity = maxVelocity;
        this.movable = movable;
        this.moveAction = moveAction;
    }

    @Override
    public void act() {
        clock.stopAction(Action.DECELERATE);
        if (movable.getVelocity() < maxVelocity) {
            movable.changeVelocity(acceleration);
            moveAction.act();
            if (movable.getVelocity() >= maxVelocity) {
                clock.stopAction(Action.ACCELERATE);
            }
        }
    }
}
