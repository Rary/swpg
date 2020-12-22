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

import java.util.Optional;

public class ArgumentParser {
    private final String[] args;

    public ArgumentParser(String[] args) {
        this.args = args;
    }

    public Optional<String> stringArgument(String name, String shortcut) {
        for (int index = 0; index < args.length; index ++) {
            String arg = args[index];
            if (arg.equals(String.format("--%s", name)) || arg.equals(String.format("-%s", shortcut))) {
                if (index >= args.length - 1) {
                    throw new IllegalArgumentException(String.format("Arg %s has no value", arg));
                }
                return Optional.of(args[index + 1]);
            }
            else if (arg.startsWith(String.format("--%s=", name))) {
                return Optional.of(arg.substring(String.format("--%s=", name).length() + 1));
            }
            else if (arg.startsWith(String.format("-%s=", shortcut))) {
                return Optional.of(arg.substring(String.format("-%s=", shortcut).length() + 1));
            }
        }
        return Optional.empty();
    }
}
