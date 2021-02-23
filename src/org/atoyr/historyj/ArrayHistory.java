package org.atoyr.historyj;

import java.util.*;

/**
 * <p>Historyインターフェースの配列の実装です。</p>
 * <p>nullを含む全ての要素を許容しますが、
 * 要素の削除は{@link Collection#clear()}を除いて実装されていません。また、
 * 要素の追加を行う際、要素の追加により要素の数が容量を超えた場合は、容量と等しくなるまで
 * 追加されている要素の古い方から順に削除されます。</p>
 *
 * <p>この実装は同期されません。複数のスレッドが平行してArrayHistoryオブジェクトにアクセスし
 * それらのスレッドの少なくとも一つが履歴を構造的に変更する場合は外部でその同期を取る必要があります。
 * そのような実装を行う際は、{@link Histories#synchronizedHistory(History)}メソッドを
 * 使用して履歴をラップすると便利です。</p>
 */
public class ArrayHistory<E> extends AbstractHistory<E> {
    /**
     * 履歴の容量を指定して新しいArrayHistoryオブジェクトを構築します
     * @param capacity 容量
     */
    public ArrayHistory(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity <= 0");
        }
        this.capacity = capacity;
    }

    private final List<E> undoStack = new ArrayList<>();
    private final List<E> redoStack = new ArrayList<>();

    private final int capacity;

    public int getCapacity() {
        return capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayHistoryIterator<>(undoStack.iterator(), redoStack.iterator());
    }

    @Override
    public int size() {
        return undoStack.size() + redoStack.size();
    }

    @Override
    public boolean canUndo() {
        return undoStack.size() >= 2;
    }

    @Override
    public boolean canRedo() {
        return redoStack.size() >= 1;
    }

    @Override
    public E getCurrentElement() {
        return undoStack.get(getCurrentElementIndex());
    }

    @Override
    public int getCurrentElementIndex() {
        return undoStack.size() - 1;
    }

    @Override
    public E redo() {
        if (!canRedo()) {
            throw new IllegalStateException("Can't redo!");
        }
        final int index = redoStack.size() - 1;
        E value = redoStack.remove(index);
        undoStack.add(value);
        return value;
    }

    @Override
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }

    @Override
    public E undo() {
        if (!canUndo()) {
            throw new IllegalStateException("Can't undo!");
        }
        final int index = undoStack.size() - 1;
        E value = undoStack.remove(index);
        redoStack.add(value);
        return getCurrentElement();
    }

    @Override
    public boolean add(E element) {
        undoStack.add(element);
        redoStack.clear();
        while (size() > getCapacity()) {
            undoStack.remove(0);
        }
        return true;
    }

    private static class ArrayHistoryIterator<E> implements Iterator<E> {
        private final Iterator<E> undoItr;
        private final Iterator<E> redoItr;
        public ArrayHistoryIterator(Iterator<E> undoItr, Iterator<E> redoItr) {
            this.undoItr = undoItr;
            this.redoItr = redoItr;
        }

        @Override
        public boolean hasNext() {
            return undoItr.hasNext() || redoItr.hasNext();
        }

        @Override
        public E next() {
            if (undoItr.hasNext()) {
                return undoItr.next();
            }
            if (redoItr.hasNext()) {
                return redoItr.next();
            }
            throw new NoSuchElementException();
        }
    }
}
