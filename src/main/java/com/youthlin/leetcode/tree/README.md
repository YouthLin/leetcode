# 二叉树
https://leetcode-cn.com/explore/learn/card/data-structure-binary-tree/

## 遍历
[`com.youthlin.leetcode.tree.TreeVisitor`](https://github.com/YouthLin/leetcode/blob/master/src/main/java/com/youthlin/leetcode/tree/TreeVisitor.java)
### 前序遍历
递归版本的太简单了，只贴前序遍历方式。
前序递归是先访问本结点，然后递归调用左子树，再递归调用右子树。
中序和后序都差不多，只是把访问结点那行移动一下就行。
```
    public <T, N extends BinTreeNode<T, N>> void preOrderTraversalRecursion(BinTreeNode<T, N> root,
            Consumer<BinTreeNode<T, N>> action) {
        if (root == null) {
            return;
        }
        action.accept(root);
        preOrderTraversalRecursion(root.getLeft(), action);
        preOrderTraversalRecursion(root.getRight(), action);
    }
```
这里我传入了一个 `Consumer`, 而不是在方法中直接打印结点值，或者加到链表、数组中。
这样，遍历过程是方法写好的，但是具体的怎么访问一个结点，由调用者自行决定。
`Consumer` 是一个仅接收一个参数且无返回值的接口，用在这里刚好合适。
类似的接口还有 `Supplier` 无参但返回一个值，可用于创建对象；
`Function` 一入参一返回值，常用于类型转换；
`Predicate` 一个参数返回布尔型，相当于一个判断谓词，可用于控制流程是否继续。

非递归方式，通常做这种题目时，我都会先自己演算一遍，然后把演算的过程具体写下来，就成了算法。
比如你看到一棵二叉树：
```
 _1_____
|       |
2     __3_
     |    |
    _4_   5
   |   |
   6   7
```
然后想象前序遍历的过程：
1. 从根结点 1 开始，访问它
2. 往左结点 2，访问它
3. 左结点 2 结束，转向根结点 1 的右结点 3，访问它
4. 往左结点 4，访问它
5. 往做结点 6，访问它
6. 左结点 6 结束，转向根结点 4 的右结点 7，访问它
7. 右结点 4 结束，转向根结点 4 的右结点 5，访问它

这个过程有个重要的动作，就是需要向上回溯到已经访问的结点：访问一个结点 a 之后，访问 a 的左结点，然后还需要获得 a 结点，以便得到 a 的右结点。
所以在遍历过程中，需要有个东西把遍历的结点都保存起来，然后在过程中需要从容器中取出。最符合这个过程的容器就是`栈`了。

根据这个过程，不难写出：
```
    public <T, N extends BinTreeNode<T, N>> void preOrderTraversal(BinTreeNode<T, N> root,
            Consumer<BinTreeNode<T, N>> action) {
        Stack<BinTreeNode<T, N>> stack = new Stack<>();
        do {
            while (root != null) {
                action.accept(root);
                stack.push(root);
                root = root.getLeft();
            }
            if (!stack.empty()) {
                root = stack.pop().getRight();
            }
        } while (root != null || !stack.isEmpty());
    }
```
### 中序遍历
中序遍历非递归，同样需要用到栈，而且和前序有点像。
```
 _1_____
|       |
2     __3_
     |    |
    _4_   5
   |   |
   6   7
```
1. 先从根结点开始，一直往左子树找，访问最左结点；
2. 然后向上回溯到父结点访问之；（这里对应栈的弹出，所以上一步需要压栈）
3. 转向右结点，但是不能直接访问，又得往最左结点。（这里回到 1 了，因此应该有个循环）
```
    public <T, N extends BinTreeNode<T, N>> void inOrderTraversal(BinTreeNode<T, N> root,
            Consumer<BinTreeNode<T, N>> action) {
        Stack<BinTreeNode<T, N>> stack = new Stack<>();
        do {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
            if (!stack.empty()) {
                root = stack.pop();
                action.accept(root);
                root = root.getRight();
            }
        } while (root != null || !stack.isEmpty());
    }
```
### 后序遍历
后序遍历比较恶心，如果你再在草纸上演示一遍的话，会发现每个结点好像都访问了两次，难道要用两个栈吗
```
 _1_____
|       |
2     __3_
     |    |
    _4_   5
   |   |
   6   7
```
你很显然知道，它的后序遍历为：`[2, 6, 7, 4, 5, 3, 1]`
以结点 4 为例，访问 6 时，需要把 4 压栈，然后 6 访问结束，会回到 4, 
但这时不能弹出 4 也不能立即访问 4，而是要先访问右边 7 然后发现 7 已经访问了，4 才能访问并弹出。
所以其实可以用一个标志变量来记录当前栈顶元素是否可以直接访问并弹出：
当 前一个访问的 是栈顶的右结点时，说明栈顶元素左右都访问过了，可以直接访问之并弹出了；
否则就只能 peek 之，然后处理它的右结点。
```
    public <T, N extends BinTreeNode<T, N>> void postOrderTraversal(BinTreeNode<T, N> root,
            Consumer<BinTreeNode<T, N>> action) {
        Stack<BinTreeNode<T, N>> stack = new Stack<>();
        BinTreeNode<T, N> pre = root;
        while (true) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
            if (stack.empty()) {
                break;
            }
            root = stack.peek();
            if (root.getRight() != null && root.getRight() != pre) {
                root = root.getRight();
            } else {
                root = stack.pop();
                action.accept(root);
                pre = root;
                root = null;
            }
        }
    }
```
### 层次遍历
层次遍历可能是写的比较多，觉得比较简单。直接贴代码：
```
    /**
     * @param root                 树的根结点
     * @param visitLeaf            是否访问叶子结点
     * @param action               访问每个结点的动作
     * @param shouldVisitNextLevel 是否继续访问下一层 输入是从 0 开始的层数 通常用于每层结束后做一些事 并且允许提前结束遍历
     * @return true 如果完成了所有结点的访问
     */
    public <T, N extends BinTreeNode<T, N>> boolean levelOrder(N root, boolean visitLeaf,
            Consumer<N> action, Predicate<Integer> shouldVisitNextLevel) {
        if (root == null) {
            return false;
        }
        Queue<N> q1 = new LinkedList<>();
        Queue<N> q2 = new LinkedList<>();
        q1.offer(root);
        int level = 0;
        do {
            while (!q1.isEmpty()) {
                root = q1.poll();
                action.accept(root);
                if (root != null) {
                    if (root.getLeft() != null || visitLeaf) {
                        q2.offer(root.getLeft());
                    }
                    if (root.getRight() != null || visitLeaf) {
                        q2.offer(root.getRight());
                    }
                }
            }
            if (!shouldVisitNextLevel.test(level++)) {
                return false;
            }
            Queue<N> tmp = q1;
            q1 = q2;
            q2 = tmp;
        } while (!q1.isEmpty());
        return true;
    }

```
这里用了两个队列，其实也可以只用一个，但是判断层结束稍微麻烦一点点，我就按最舒服的样子的写了。
这里还传入了一个 `Predicate<Integer> shouldVisitNextLevel` 
本来是没这个的，但之后做到了判断是否对称二叉树的题目，可以用层次遍历，而且如果在中间的层就不对称了的话，可以直接终止遍历，所以加了个参数。

## 创建
[`com.youthlin.leetcode.tree.TreeBuilder`](https://github.com/YouthLin/leetcode/blob/master/src/main/java/com/youthlin/leetcode/tree/TreeBuilder.java)
### 从前序和中序创建
```
pre order [1, 2, 3, 4, 6, 7, 5]
in  order [2, 1, 6, 4, 7, 3, 5]

 _1_____
|       |
2     __3_
     |    |
    _4_   5
   |   |
   6   7
```
先找前序第一个 1 肯定是根结点，然后在中序里找 1，1 左边就是左子树，右边就是右子树。
```
[1]是根
前序剩下[2,3,4,6,7,5]
左[2]  右[6,4,7,3,5]
```
子树递归这样创建，需要注意每生成一个结点才会消费一个前序数组元素。
```
    public <T> TreeNode<T> buildTreeViaPreInOrder(T[] preOrder, T[] inOrder) {
        Wrapper<Integer> preIndex = new Wrapper<>();
        preIndex.setData(0);
        return buildNodePreInOrder(preOrder, inOrder, 0, inOrder.length, preIndex);
    }

    private <T> TreeNode<T> buildNodePreInOrder(T[] preOrder, T[] inOrder, int beg, int end, Wrapper<Integer> preIndex) {
        if (preIndex.getData() >= preOrder.length) {
            return null;
        }
        T val = preOrder[preIndex.getData()];
        int nodeIndex = indexOf(inOrder, val);
        if (nodeIndex < beg || nodeIndex >= end) {
            return null;
        }
        TreeNode<T> node = new TreeNode<>(val);
        preIndex.setData(preIndex.getData() + 1);
        node.left = buildNodePreInOrder(preOrder, inOrder, beg, nodeIndex, preIndex);
        node.right = buildNodePreInOrder(preOrder, inOrder, nodeIndex + 1, end, preIndex);
        return node;
    }
```
所以这里将 `preIndex` 包了一层，因为 int/Integer 不可变, 同时不想用另外的实例或静态变量，所以直接传进去了。
### 从中序和后序创建
类似的，不过是从后序数组的尾部开始看：
```
    public <T> TreeNode<T> buildTreeViaInPostOrder(T[] inOrder, T[] postOrder) {
        int len = inOrder.length;
        Wrapper<Integer> postEnd = new Wrapper<>();
        postEnd.setData(len);
        return buildNodeInPostOrder(inOrder, 0, len, postOrder, postEnd);
    }
    private <T> TreeNode<T> buildNodeInPostOrder(T[] inOrder, int beg, int end, T[] postOrder, Wrapper<Integer> postEnd) {
        //找根结点
        if (postEnd.getData() <= 0 || beg >= end) {
            return null;
        }
        int nodeIndex = indexOf(inOrder, postOrder[postEnd.getData() - 1]);
        if (nodeIndex < beg || nodeIndex >= end) {
            return null;
        }
        TreeNode<T> node = new TreeNode<>(inOrder[nodeIndex]);
        postEnd.setData(postEnd.getData() - 1);
        //
        TreeNode<T> right = buildNodeInPostOrder(inOrder, nodeIndex + 1, end, postOrder, postEnd);
        TreeNode<T> left = buildNodeInPostOrder(inOrder, beg, nodeIndex, postOrder, postEnd);
        node.right = right;
        node.left = left;
        return node;
    }
```
### 从层序创建
如果是满二叉树，我们可以发现一个规律：
```
   _0___
  |     |
 _1_   _2
|   | |
3   4 5
```
可以发现 `left(i) = 2i + 1`, `right(i) = 2i + 2`, `parent(i) = (i - 1) / 2`
于是可以在创建每层的结点时，将创建好的结点保存在数组中，然后根据下标关系连接起来：
```
    /**
     * 输入是满二叉树按层次遍历的顺序 空结点用 null 表示(所以结点的 data 值永远不能为 null)
     *
     * @param creator 由结点值 T 构造 结点 N. 通常可以传入结点类型 N 的带参数 data 的构造函数
     * @param <T>     结点值类型
     * @param <N>     结点类型
     */
    public <I, T, N extends BinTreeNode<T, N>> N buildViaFullLevelOrder(Iterable<I> input, Function<I, N> creator) {
        Iterator<I> iterator = input.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        List<N> list = new ArrayList<>();
        int index = 0;
        int parentIndex;
        N parent, node;
        while (iterator.hasNext()) {
            I next = iterator.next();
            if (next == null) {
                node = null;
            } else {
                node = creator.apply(next);
            }
            list.add(node);
            parentIndex = (index - 1) / 2;
            if (parentIndex >= 0) {
                parent = list.get(parentIndex);
                if (index % 2 == 0) {
                    parent.setRight(node);
                } else {
                    parent.setLeft(node);
                }
            }
            index++;

        }
        return list.get(0);
    }

```
但是如果不是满二叉树的话，就比较烦：
```
 _1_____
|       |
2     __3_
     |    |
    _4_   5
   |   |
   6   7
```
不过思路可以复用，可以把结点保存起来，
因为设置父结点的左右孩子时，都是顺序设置的，而且在设置完右结点时，才需要移动 "需要设置左右结点的父结点" 的指针。
```
    /**
     * 输入是按层次遍历的顺序 空结点用 null 表示(所以结点的 data 值永远不能为 null)
     *
     * @param <I> input value type
     * @param <T> node value type
     * @param <N> node type
     */
    public <I, T, N extends BinTreeNode<T, N>> N buildLevelOrder(Iterable<I> input, Function<I, N> creator) {
        Iterator<I> iterator = input.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        List<N> list = new ArrayList<>();
        N parent, node;
        int parentIndex = 0, index = 0;
        while (iterator.hasNext()) {
            I next = iterator.next();
            if (next == null) {
                node = null;
            } else {
                node = creator.apply(next);
            }
            if (node != null) {
                list.add(node);
            }
            if (index > 0) {
                parent = list.get(parentIndex);
                if (index % 2 == 1) {
                    parent.setLeft(node);
                } else {
                    parent.setRight(node);
                    ++parentIndex;
                }
            }
            index++;
        }
        return list.get(0);
    }

```
## 序列化与反序列化
这里给出 `TreeNode<Integer>` 序列化为 String 的版本
```
    public String serialize(TreeNode<Integer> root) {
        if (root == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        new TreeVisitor().levelOrder(root, true,
                node -> {
                    if (node == null) {
                        sb.append(" ");
                    } else {
                        sb.append(node.getData());
                    }
                    sb.append(",");
                },
                count -> true);
        return sb.toString();
    }

    public TreeNode<Integer> deSerialize(String data) {
        if (data == null) {
            return null;
        }
        String[] input = data.split(",");
        return new TreeBuilder().buildLevelOrder(Arrays.asList(input), val -> {
            if (" ".equals(val)) {
                return null;
            }
            return new TreeNode<>(Integer.parseInt(val));
        });
    }
```
得益于之前的遍历方法和 build 方法的通用性，这里少写了很多重复模式代码。
针对任意类型的二叉树序列化为 byte[] ，可以参考 [`com.youthlin.leetcode.tree.TreeCodec#serialize(N, java.util.function.Function<T,byte[]>)`
和 `#deSerialize`](https://github.com/YouthLin/leetcode/blob/master/src/main/java/com/youthlin/leetcode/tree/TreeCodec.java)

## 输出

以前写过 C++ 语言的 [《二叉树的文本模式输出》](https://youthlin.com/?p=868)
这次用 Java 重写了一遍，感觉比当时写得更好吧。
主要思想还是一致的，先中序遍历，计算每个结点的偏移量，然后层序遍历真正输出每个结点。
效果如上述的例子，中文宽度不一样，所以后来加了参数，可以自定义空格/下划线/竖线，
传入全角格式的空格，下划线，竖线，效果如下：
```

　　　＿＿＿＿文学＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿
　　｜　　　　　　　　　　　　　　　　　　　　　　　｜
　＿小说＿　　　　　　　　　　　　　　　＿＿＿＿＿＿戏剧＿＿＿
｜　　　　｜　　　　　　　　　　　　　｜　　　　　　　　　　　｜
长篇　　　短篇　　　　　　＿＿＿＿＿＿外文＿　　　　　　　＿＿中文＿＿
　　　　　　　　　　　　｜　　　　　　　　　｜　　　　　｜　　　　　　｜
　　　　　　　　　　＿＿罗密欧＿　　　　　　哈姆雷特　　窦娥冤　　　＿范进中举＿
　　　　　　　　　｜　　　　　　｜　　　　　　　　　　　　　　　　｜　　　　　　｜
　　　　　　　　　男主角　　　　女主角　　　　　　　　　　　　　　买肉　　　　　杀鸡

```

## 其他题目
递归问题，树的最大深度、对称二叉树、路径总和 比较简单，没贴出来。
代码在 [`com.youthlin.leetcode.tree.TreeRecursion`](https://github.com/YouthLin/leetcode/blob/master/src/main/java/com/youthlin/leetcode/tree/TreeRecursion.java)

填充每个结点的下一个右侧结点指针，使用层次遍历： [`com.youthlin.leetcode.tree.TreeNodeConnector.connect`](https://github.com/YouthLin/leetcode/blob/master/src/main/java/com/youthlin/leetcode/tree/TreeNodeConnector.java)

二叉树的最近公共祖先，先分别找到根到两个结点的路径，然后比较路径： [`com.youthlin.leetcode.tree.TreeNodeAncestorFinder`](https://github.com/YouthLin/leetcode/blob/master/src/main/java/com/youthlin/leetcode/tree/TreeNodeAncestorFinder.java)
