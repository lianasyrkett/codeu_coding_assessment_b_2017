// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.mathlang.impl;

import java.io.IOException;

import com.google.codeu.mathlang.core.tokens.Token;
import com.google.codeu.mathlang.parsing.TokenReader;

// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {

  public MyTokenReader(String source) {
    private static final ArrayList<Character> SYMBOLS = new ArrayList<Character>(Arrays.asList(';', '+', '=', '-'));

private static final char SPACE_CHAR = ' ';
private static final char NEWLINE_CHAR = '\n';
private static final char QUOTE_CHAR = '\"';

private String source;
private int currentIndex = 0;

public MyTokenReader(String source) {
  // Your token reader will only be given a string for input. The string will
  // contain the whole source (0 or more lines).
this.source = source;
}

String GOAL = "note \"comment 1\"\n     \"comment 2\";";

@Override
public Token next() throws IOException {
  // Most of your work will take place here. For every call to |next| you should
  // return a token until you reach the end. When there are no more tokens, you
  // should return |null| to signal the end of input.

  // If for any reason you detect an error in the input, you may throw an IOException
  // which will stop all execution.

if (source.length() <= currentIndex) return null;

Character currentChar = getCurrentChar();
if (currentChar == null) return null;

if (currentChar == QUOTE_CHAR) {
  // RETURN A STRING
  int endQuoteIndex = source.indexOf(QUOTE_CHAR, currentIndex + 1);
  String str = source.substring(currentIndex + 1, endQuoteIndex).trim();

  currentIndex = endQuoteIndex + 1;

  return new StringToken(str);
}

int symbolIndex = -1;
int spaceIndex = source.indexOf(SPACE_CHAR, currentIndex + 1);
for (Character symbol : SYMBOLS) {
    int potentialCutoff = source.indexOf(symbol, currentIndex + 1);
    if (potentialCutoff != -1) {
    if (symbolIndex == -1) symbolIndex = potentialCutoff;
    else symbolIndex = Math.min(potentialCutoff, symbolIndex);
    }
  }

int cutoffIndex = -1;
if (spaceIndex != -1 && symbolIndex != -1) {
  cutoffIndex = Math.min(symbolIndex, spaceIndex);
} else {
  cutoffIndex = Math.max(symbolIndex, spaceIndex);
}

if (cutoffIndex != -1 && currentChar != '+') {
  Double number = null;
  try {
  number = Double.valueOf(source.substring(currentIndex, cutoffIndex));
  } catch (NumberFormatException nfe) {
  number = null;
  }

  if (number != null) {
  currentIndex = cutoffIndex;
  return new NumberToken(number);
  }
}

if (SYMBOLS.contains(currentChar)) {
  currentIndex++;
  return new SymbolToken(currentChar);
} else {
  String name = source.substring(currentIndex, cutoffIndex);

  currentIndex = cutoffIndex;
  return new NameToken(name);
}

}

private Character getCurrentChar() {
if (currentIndex >= source.length()) return null;
  char currentChar = source.charAt(currentIndex);
  if (currentChar == SPACE_CHAR || currentChar == NEWLINE_CHAR) {
    currentIndex++;
    return getCurrentChar();
  }
  return currentChar;
}
}
}
