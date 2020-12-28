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

public class Movable {
    private final String name;
    private int x;
    private int y;
    private int heading;
    private int velocity;

    public Movable(String name) {
        this.name = name;
        this.x = -1;
        this.y = -1;
        this.heading = 0;
        this.velocity = 0;
    }

    public Movable(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.heading = 0;
        this.velocity = 0;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPhase() {
        return 0;
    }

    public int getHeading() {
        return heading;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void changeX(int deltaX) {
        this.x += deltaX;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void changeY(int deltaY) {
        this.y += deltaY;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public void changeHeading(int deltaHeading) {
        this.heading += deltaHeading;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void changeVelocity(int deltaVelocity) {
        this.velocity += deltaVelocity;
    }
}
