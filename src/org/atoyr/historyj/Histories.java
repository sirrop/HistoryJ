package org.atoyr.historyj;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class Histories {
    private Histories() {}

    /**
     * <p>指定された履歴に連動するスレッドセーフな履歴のビューを返します。</p>
     *
     * <p>確実なスレッドセーフを実現するためには、指定された履歴へのアクセスは
     * すべてこのメソッドで返された履歴を介して行ってください。また、反復処理を
     * 行う場合は、手動で同期をとる必要があります。</p>
     *
     * <p>これを行わない場合、動作は保証されません</p>
     * @param history ラップされる履歴
     * @param <E> 履歴内のオブジェクトのクラス
     * @return 指定された履歴の同期ビュー
     */
    public static <E> History<E> synchronizedHistory(History<E> history) {
        return new SynchronizedHistory<>(history);
    }

    private static class SynchronizedHistory<E> implements History<E> {
        private final History<E> h;
        private final Object mutax;
        public SynchronizedHistory(History<E> h, Object mutax) {
            this.h = h;
            this.mutax = mutax;
        }
        public SynchronizedHistory(History<E> h) {
            this.h = h;
            mutax = this;
        }

        @Override
        public boolean canUndo() {
            synchronized(mutax) {
                return h.canUndo();
            }
        }

        @Override
        public boolean canRedo() {
            synchronized (mutax) {
                return h.canUndo();
            }
        }

        @Override
        public E getCurrentElement() {
            synchronized (mutax) {
                return h.getCurrentElement();
            }
        }

        @Override
        public int getCurrentElementIndex() {
            synchronized (mutax) {
                return h.getCurrentElementIndex();
            }
        }

        @Override
        public E redo() {
            synchronized (mutax) {
                return h.redo();
            }
        }

        @Override
        public E undo() {
            synchronized (mutax) {
                return h.undo();
            }
        }

        @Override
        public int size() {
            synchronized (mutax) {
                return h.size();
            }
        }

        @Override
        public boolean isEmpty() {
            synchronized (mutax) {
                return h.isEmpty();
            }
        }

        @Override
        public boolean contains(Object o) {
            synchronized (mutax) {
                return h.contains(o);
            }
        }

        @Override
        public Iterator<E> iterator() {
            return h.iterator();
        }

        @Override
        public Object[] toArray() {
            synchronized (mutax) {
                return h.toArray();
            }
        }

        @Override
        public <T> T[] toArray(T[] a) {
            synchronized (mutax) {
                return h.toArray(a);
            }
        }

        @Override
        public boolean add(E e) {
            synchronized (mutax) {
                return h.add(e);
            }
        }

        @Override
        public boolean remove(Object o) {
            synchronized (mutax) {
                return h.remove(o);
            }
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            synchronized (mutax) {
                return h.containsAll(c);
            }
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            synchronized (mutax) {
                return h.addAll(c);
            }
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            synchronized (mutax) {
                return h.removeAll(c);
            }
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            synchronized (mutax) {
                return h.retainAll(c);
            }
        }

        @Override
        public void clear() {
            synchronized (mutax) {
                h.clear();
            }
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            synchronized (mutax) {
                h.forEach(consumer);
            }
        }

        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            synchronized (mutax) {
                return h.removeIf(filter);
            }
        }

        @Override
        public Spliterator<E> spliterator() {
            return h.spliterator();
        }

        @Override
        public Stream<E> stream() {
            return h.stream();
        }

        @Override
        public Stream<E> parallelStream() {
            return h.parallelStream();
        }
    }
}
