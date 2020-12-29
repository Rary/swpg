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
        clock.scheduleAction(Action.COUNTER, this::count, gameConfig.FRAME_DURATION);
    }

    @Override
    public void eventCompleted(InputEvent event) {
        Input input = event.payload();
        input.directionPressed().ifPresent(this::directionPressed);
        input.directionReleased().ifPresent(direction -> directionReleased());
    }

    private void count() {
        state.incrementCounter();
        eventManager.publish(new StateEvent(state));
    }

    private void directionPressed(Direction direction) {
        state.inputDirection(direction);
        accelerate();
        eventManager.publish(new StateEvent(state));
        clock.scheduleAction(Action.ACCELERATE, this::accelerate, gameConfig.PLAYER_ACCELERATION_RATE);
        movePlayer();
    }

    private void directionReleased() {
        decelerate();
        eventManager.publish(new StateEvent(state));
        clock.scheduleAction(Action.DECELERATE, this::decelerate, gameConfig.PLAYER_DECELERATION_RATE);
        movePlayer();
    }

    private void accelerate() {
        clock.stopAction(Action.DECELERATE);
        if (state.player().getVelocity() < gameConfig.PLAYER_MAX_VELOCITY) {
            state.player().changeVelocity(gameConfig.PLAYER_ACCELERATION);
            movePlayer();
            if (state.player().getVelocity() >= gameConfig.PLAYER_MAX_VELOCITY) {
                clock.stopAction(Action.ACCELERATE);
            }
        }
    }

    private void decelerate() {
        clock.stopAction(Action.ACCELERATE);
        if (state.player().getVelocity() > 0) {
            state.player().changeVelocity(gameConfig.PLAYER_DECELERATION);
            movePlayer();
            if (state.player().getVelocity() <= 0) {
                clock.stopAction(Action.DECELERATE);
            }
        }
    }

    private void movePlayer() {
        Player player = state.player();
        if (player.getVelocity() == 0) {
            state.inputDirection(Direction.NONE);
            clock.stopAction(Action.MOVE_PLAYER);
            return;
        }
        Direction direction = state.inputDirection();
        player.setDirection(direction);
        clock.scheduleAction(Action.MOVE_PLAYER, () -> {
            int deltaX = state.player().getDirection().deltaX();
            int deltaY = state.player().getDirection().deltaY();
            state.player().changeX(deltaX);
            state.player().changeY(deltaY);
        }, clock.ONE_SECOND / state.player().getVelocity());
    }
}
