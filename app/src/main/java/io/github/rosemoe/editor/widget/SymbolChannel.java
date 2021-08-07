/*
 *   Copyright 2020-2021 Rosemoe
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package io.github.rosemoe.editor.widget;

import io.github.rosemoe.editor.text.Cursor;
import java.util.ArrayList;
import java.util.List;

/**
 * A channel to insert symbols in {@link CodeEditor}
 * @author Rosemoe
 */
public class SymbolChannel {

    private CodeEditor mEditor;
    
    private final List<Character> pairs;

    protected SymbolChannel(CodeEditor editor) {
        mEditor = editor;
        pairs = new ArrayList<>();
        pairs.add('}');
        pairs.add(')');
        pairs.add(']');
        pairs.add('"');
        pairs.add('\'');
        pairs.add('>');
    }

    /**
     * Inserts the given text in the editor.
     * <p>
     * This method allows you to insert texts externally to the content of editor.
     * The content of {@param symbolText} is not checked to be exactly characters of symbols.
     *
     * @throws IllegalArgumentException If the {@param selectionRegion} is invalid
     * @param symbolText Text to insert, usually a text of symbols
     * @param selectionOffset New selection position relative to the start of text to insert.
     *                        Ranging from 0 to symbolText.length()
     */
    public void insertSymbol(String text, int selectionOffset) {
        if (selectionOffset < 0 || selectionOffset > text.length()) {
            throw new IllegalArgumentException("selectionOffset is invalid");
        }
        Cursor cur = mEditor.getText().getCursor();
        if (cur.isSelected()) {
            cur.onDeleteKeyPressed();
            mEditor.notifyExternalCursorChange();
        }
        
        if(cur.getLeftColumn() < mEditor.getText().getColumnCount(cur.getLeftLine())
           && text.length() == 1
           && text.charAt(0) == mEditor.getText().charAt(cur.getLeftLine(), cur.getLeftColumn())
           && pairs.contains(text.charAt(0))) {
               mEditor.moveSelectionRight();
        } else {
            mEditor.getText().insert(cur.getRightLine(), cur.getRightColumn(), text);
            mEditor.notifyExternalCursorChange();
            if (selectionOffset != text.length()) {
                mEditor.setSelection(cur.getRightLine(), cur.getRightColumn() - (text.length() - selectionOffset));
            }
        }
    }

}
