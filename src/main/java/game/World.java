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

import config.GameConfig;
import event.EventListener;
import event.EventManager;
import event.InputEvent;
import event.StateEvent;
import model.Player;
import model.Direction;
import model.Input;
import model.State;

public class World implements EventListener<InputEvent> {
    private final EventManager eventManager;
    private final GameConfig gameConfig;
    private final State state;
    private final Clock clock;

    public World(EventManager eventManager, GameConfig gameConfig, State state, Clock clock) {
        this.eventManager = eventManager;
        this.gameConfig = gameConfig;
        this.state = state;
        this.clock = clock;
    }

    public void start() {
        clock.scheduleAction(Action.COUNTER, () -> {
            state.incrementCounter();
            eventManager.publish(new StateEvent(state));
        }, 25);
    }

    @Override
    public void eventCompleted(InputEvent event) {
        Input input = event.payload();
        input.directionPressed().ifPresent(direction -> {
            switch (direction) {
                case E:
                    rightPressed();
                    break;
                case W:
                    leftPressed();
                    break;
            }
        });
        input.directionReleased().ifPresent(direction -> {
            switch (direction) {
                case E:
                    rightReleased();
                    break;
                case W:
                    leftReleased();
                    break;
            }
        });
    }

    private void rightPressed() {
        state.inputDirection(Direction.E);
        state.player().setVelocity(20);
        eventManager.publish(new StateEvent(state));
        clock.scheduleAction(Action.RIGHT_HELD, () -> {
            if (state.player().getVelocity() < 200) {
                state.player().changeVelocity(20);
                applyInputDirection();
            }
        }, 20);
        applyInputDirection();
    }

    private void rightReleased() {
        state.inputDirection(Direction.NONE);
        state.player().setVelocity(0);
        clock.stopAction(Action.RIGHT_HELD);
        applyInputDirection();
    }

    private void leftPressed() {
        state.inputDirection(Direction.W);
        state.player().setVelocity(20);
        eventManager.publish(new StateEvent(state));
        clock.scheduleAction(Action.LEFT_HELD, () -> {
            if (state.player().getVelocity() < 200) {
                state.player().changeVelocity(20);
                applyInputDirection();
            }
        }, 20);
        applyInputDirection();
    }

    private void leftReleased() {
        state.inputDirection(Direction.NONE);
        state.player().setVelocity(0);
        clock.stopAction(Action.LEFT_HELD);
        applyInputDirection();
    }

    private void applyInputDirection() {
        Player player = state.player();
        if (player.getVelocity() == 0) {
            clock.stopAction(Action.MOVE_PLAYER);
            return;
        }
        int heading = state.inputDirection().heading();
        player.setHeading(heading);
        clock.scheduleAction(Action.MOVE_PLAYER, () -> {
            int deltaX = state.player().getHeading() > Direction.N.heading() && state.player().getHeading() < Direction.S.heading() ? 1 : state.player().getHeading() < 360 + Direction.N.heading() || state.player().getHeading() > Direction.S.heading() ? -1 : 0;
            int deltaY = state.player().getHeading() > Direction.E.heading() && state.player().getHeading() < Direction.W.heading() ? 1 : state.player().getHeading() < Direction.E.heading() || state.player().getHeading() > Direction.W.heading() ? -1 : 0;
            state.player().changeX(deltaX);
            state.player().changeY(deltaY);
        }, 1000 / state.player().getVelocity());
    }
}
