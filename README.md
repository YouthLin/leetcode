# leetcode
[![Build Status](https://travis-ci.org/YouthLin/leetcode.svg?branch=master)](https://travis-ci.org/YouthLin/leetcode)

## 二叉树
构造、打印
```
TreeBuilder builder = new TreeBuilder();
TreeNode<String> strRoot = builder
                .buildLevelOrder(
                        Arrays.asList("文学",
                                "小说", "戏剧",
                                "长篇", "短篇", "外文", "中文",
                                null, null, null, null, "罗密欧", "哈姆雷特", "窦娥冤", "范进中举",
                                "男主角", "女主角", null, null, null, null, "买肉", "杀鸡"),
                        TreeNode::new);
System.out.println(TreePrinter.printTree(strRoot, 0, "　", "＿", "｜"));
```
<pre>

　　　＿＿＿＿文学＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿＿
　　｜　　　　　　　　　　　　　　　　　　　　　　　｜
　＿小说＿　　　　　　　　　　　　　　　＿＿＿＿＿＿戏剧＿＿＿
｜　　　　｜　　　　　　　　　　　　　｜　　　　　　　　　　　｜
长篇　　　短篇　　　　　　＿＿＿＿＿＿外文＿　　　　　　　＿＿中文＿＿
　　　　　　　　　　　　｜　　　　　　　　　｜　　　　　｜　　　　　　｜
　　　　　　　　　　＿＿罗密欧＿　　　　　　哈姆雷特　　窦娥冤　　　＿范进中举＿
　　　　　　　　　｜　　　　　　｜　　　　　　　　　　　　　　　　｜　　　　　　｜
　　　　　　　　　男主角　　　　女主角　　　　　　　　　　　　　　买肉　　　　　杀鸡
</pre>

## todo
