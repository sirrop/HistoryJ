package org.atoyr.historyj;

import java.util.Collection;

/**
 * 履歴の管理を行うための機能を提供します。
 * @param <E>
 */
public interface History<E> extends Collection<E> {
    /**
     * {@link History#undo()}が実行可能であるかどうか判定します。
     * 現在参照している要素が、最も古いものでない場合trueを返し、
     * 最も古いものだった場合falseを返します。
     * @return 現在の参照が最も古いものか
     */
    boolean canUndo();

    /**
     * {@link History#redo()}が実行可能であるかどうか判定します。
     * 現在参照している要素が、最も新しいものではない場合trueを返し、
     * 最も新しいものだった場合falseを返します。
     * @return 現在の参照が最も新しいものか
     */
    boolean canRedo();

    /**
     * 現在参照中の要素を返します
     * @return 現在参照中の要素
     */
    E getCurrentElement();

    /**
     * 現在参照中の要素の位置を返します
     * @return 現在参照中の要素の位置
     */
    int getCurrentElementIndex();

    /**
     * 履歴をひとつ先に進め、新しい現在の参照を返します。
     * このメソッドが呼び出された時点で登録されている最も新しい要素を参照している場合、
     * IllegalStateExceptionが投げられます。
     * @return 新しい現在の参照
     * @exception IllegalStateException
     * このメソッドが呼び出された時点で、履歴に登録されている最も新しい要素を参照している場合
     */
    E redo();

    /**
     * 履歴をひとつ後に戻し、新しい現在の参照を返します。
     * このメソッドが呼び出された時点で登録されている最も古い要素を参照している場合、
     * IllegalStateExceptionが投げられます。
     * @return 新しい現在の参照
     * @exception IllegalStateException
     * このメソッドが呼び出された時点で、履歴に登録されている最も古い要素を参照している場合
     */
    E undo();
}
