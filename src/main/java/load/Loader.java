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
import ui.GamePanel;
import ui.GameRenderer;
import ui.GameWindow;
import ui.Keyboard;
import ui.graphics.SpriteManager;

import java.awt.*;

public class Loader {
    public void run(ArgumentParser args) {
        Messages messages = buildMessages(args);
        EventManager eventManager = buildEventManager();
        GameConfig gameConfig = buildGameConfig(messages);
        World world = buildWorld(eventManager, gameConfig);
        SpriteManager spriteManager = buildSpriteManager();
        GamePanel gamePanel = buildGamePanel(spriteManager);
        GameWindow gameWindow = buildGameWindow(gameConfig);
        Keyboard keyboard = buildKeyboard(eventManager);
        initializeComponents(gameWindow, gamePanel, keyboard, world, eventManager);
        startGame(spriteManager, gameWindow, world);
    }

    private Messages buildMessages(ArgumentParser args) {
        return new Messages(args.stringArgument("messages", "m").orElse("i18n/messages"));
    }

    private EventManager buildEventManager() {
        return new EventManager();
    }

    private GameConfig buildGameConfig(Messages messages) {
        return new GameConfig(messages);
    }

    private World buildWorld(EventManager eventManager, GameConfig gameConfig) {
        return new World(eventManager, gameConfig, new State(new Player(2, 800, 800)), new Clock());
    }

    private SpriteManager buildSpriteManager() {
        return new SpriteManager();
    }

    private GamePanel buildGamePanel(SpriteManager spriteManager) {
        return new GamePanel(new GameRenderer(spriteManager, 1900.0, 1000.0));
    }

    private GameWindow buildGameWindow(GameConfig gameConfig) {
        return new GameWindow(gameConfig.windowTitle());
    }

    private Keyboard buildKeyboard(EventManager eventManager) {
        return new Keyboard(eventManager);
    }

    private void initializeComponents(GameWindow gameWindow, GamePanel gamePanel, Keyboard keyboard, World world, EventManager eventManager) {
        gameWindow.add(gamePanel, BorderLayout.CENTER);
        gameWindow.addKeyListener(keyboard);
        eventManager.subscribeToInput(world);
        eventManager.subscribeToState(gamePanel);
    }

    private void startGame(SpriteManager spriteManager, GameWindow gameWindow, World world) {
        spriteManager.load("sprites");
        gameWindow.launch();
        world.start();
    }
}
