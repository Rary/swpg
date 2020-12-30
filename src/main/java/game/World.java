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
import game.action.Accelerate;
import game.action.Action;
import game.action.Decelerate;
import game.action.Move;
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
        Action move = new Move(clock, state, state.player());
        Action accelerate = new Accelerate(clock, state, gameConfig.PLAYER_ACCELERATION, gameConfig.PLAYER_MAX_VELOCITY, state.player(), move);
        accelerate.act();
        eventManager.publish(new StateEvent(state));
        clock.scheduleAction(Action.ACCELERATE, accelerate, gameConfig.PLAYER_ACCELERATION_RATE);
        move.act();
    }

    private void directionReleased() {
        Action decelerate = new Decelerate(clock, state, gameConfig.PLAYER_DECELERATION, state.player());
        decelerate.act();
        eventManager.publish(new StateEvent(state));
        clock.scheduleAction(Action.DECELERATE, decelerate, gameConfig.PLAYER_DECELERATION_RATE);
        new Move(clock, state, state.player()).act();
    }
}
