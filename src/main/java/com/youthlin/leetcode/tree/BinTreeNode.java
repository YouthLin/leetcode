package com.youthlin.leetcode.tree;

/**
 * 二叉树结点
 *
 * @param <T> the value type
 * @param <N> this node type
 * @author : youthlin.chen @ 2019-06-15 22:06
 */
public interface BinTreeNode<T, N extends BinTreeNode<T, N>> {

    void setData(T data);

    T getData();

    void setLeft(N left);

    N getLeft();

    N getRight();

    void setRight(N right);

    /**
     * 用于 {@link TreePrinter#printTree printTree} 中序遍历时设置结点的偏移量
     * 建议实现类用一个字段接受该值，并在 {@link #getOffset() getOffset} 方法中正确返回
     */
    void setOffset(int offset);

    /**
     * {@link TreePrinter#printTree printTree} 输出二叉树时会调用 {@link #setOffset(int) setOffset} 设置值
     * 这里返回设置的值即可
     */
    int getOffset();

    /**
     * 结点值输出为文本 每次调用应该是同一个结果
     * 在 {@link TreePrinter#printTree printTree} 时会调用多次该方法以获得本结点的占用宽度
     */
    default String printData() {
        return String.valueOf(getData());
    }

}
