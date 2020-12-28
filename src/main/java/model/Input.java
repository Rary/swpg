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
package model;

import java.util.Optional;

public class Input {
    private final Direction directionPressed;
    private final Direction directionReleased;

    public static Input forDirectionPressed(Direction direction) {
        return new Input(direction, null);
    }

    public static Input forDirectionReleased(Direction direction) {
        return new Input(null, direction);
    }

    private Input(Direction directionPressed, Direction directionReleased) {
        this.directionPressed = directionPressed;
        this.directionReleased = directionReleased;
    }

    public Optional<Direction> directionPressed() {
        return Optional.ofNullable(directionPressed);
    }

    public Optional<Direction> directionReleased() {
        return Optional.ofNullable(directionReleased);
    }
}
