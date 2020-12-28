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
package ui;

import event.EventManager;
import event.InputEvent;
import model.Direction;
import model.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class Keyboard implements KeyListener {
    private final EventManager eventManager;
    private final Set<Integer> keyPresses;

    public Keyboard(EventManager eventManager) {
        this.eventManager = eventManager;
        this.keyPresses = new HashSet<>();
    }

    @Override
    public void keyTyped(KeyEvent event) {
        // TODO:
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyPresses.contains(keyCode)) {
            return;
        }
        keyPresses.add(keyCode);
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                eventManager.publish(new InputEvent(Input.forDirectionPressed(Direction.E)));
                break;
            case KeyEvent.VK_LEFT:
                eventManager.publish(new InputEvent(Input.forDirectionPressed(Direction.W)));
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (! keyPresses.contains(keyCode)) {
            return;
        }
        keyPresses.remove(keyCode);
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                eventManager.publish(new InputEvent(Input.forDirectionReleased(Direction.E)));
                break;
            case KeyEvent.VK_LEFT:
                eventManager.publish(new InputEvent(Input.forDirectionReleased(Direction.W)));
                break;
            default:
                break;
        }
    }
}
