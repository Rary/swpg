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
package load;

import config.GameConfig;
import config.i18n.Messages;
import event.EventManager;
import game.Clock;
import model.Player;
import game.World;
import model.State;
import ui.GameWindow;
import ui.Keyboard;

public class Loader {
    public void run(ArgumentParser args) {
        Messages messages = new Messages(args.stringArgument("messages", "m").orElse("i18n/messages"));
        EventManager eventManager = new EventManager();
        GameConfig gameConfig = new GameConfig(messages);
        World world = new World(eventManager, gameConfig, new State(new Player(100, 100)), new Clock());
        GameWindow window = new GameWindow(gameConfig.windowTitle());
        window.addKeyListener(new Keyboard(eventManager));
        eventManager.subscribeToInput(world);
        eventManager.subscribeToState(window.eventListener());
        window.launch();
        world.start();
    }
}
