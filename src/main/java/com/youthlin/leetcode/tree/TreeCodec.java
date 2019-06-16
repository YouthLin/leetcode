package com.youthlin.leetcode.tree;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author : youthlin.chen @ 2019-06-16 19:29
 */
public class TreeCodec {
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

    public <T, N extends BinTreeNode<T, N>> byte[] serialize(N root, Function<T, byte[]> serializer) {
        if (root == null) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        new TreeVisitor().levelOrder(root, true,
                node -> {
                    if (node != null) {
                        byte[] bytes = serializer.apply(node.getData());
                        int length = bytes.length;
                        writeInt(bos, length);
                        bos.write(bytes, 0, length);
                    } else {
                        writeInt(bos, 0);
                    }
                },
                count -> true);
        return bos.toByteArray();
    }

    public <T, N extends BinTreeNode<T, N>> N deSerialize(byte[] data, Function<byte[], T> deSerializer,
            Function<T, N> creator) {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int eof = -1;
        List<T> values = new ArrayList<>();
        do {
            int len = readInt(bis);
            if (len == -1) {
                eof = -1;
            }
            if (len == 0) {
                values.add(null);
            }
            if (len > 0) {
                byte[] nodeByte = new byte[len];
                eof = bis.read(nodeByte, 0, len);
                T value = deSerializer.apply(nodeByte);
                values.add(value);
            }
        } while (eof != -1);
        return new TreeBuilder().buildLevelOrder(values, creator);
    }

    private static void writeInt(ByteArrayOutputStream bos, int data) {
        bos.write(data & 0xff000000);
        bos.write(data & 0x00ff0000);
        bos.write(data & 0x0000ff00);
        bos.write(data & 0x000000ff);
    }

    private static int readInt(ByteArrayInputStream bis) {
        int read = bis.read();
        read = (read << 8) | bis.read();
        read = (read << 8) | bis.read();
        read = (read << 8) | bis.read();
        return read;
    }

    public static void main(String[] args) {
        TreeBuilder builder = new TreeBuilder();
        TreeNode<Integer> root = builder.buildLevelOrder(Arrays.asList(1, 2, 3, null, null, 4, 5, 6, 7), TreeNode::new);
        System.out.println(root);
        TreeCodec codec = new TreeCodec();
        String str = codec.serialize(root);
        System.out.println(str);
        TreeNode<Integer> newRoot = codec.deSerialize(str);
        System.out.println(newRoot);

        byte[] bytes = codec.serialize(root, val -> String.valueOf(val).getBytes());
        TreeNode<Integer> deRoot = codec
                .deSerialize(bytes, b -> Integer.parseInt(new String(b)), TreeNode::new);
        System.out.println(deRoot);

        TreeNode<String> strRoot = builder
                .buildLevelOrder(
                        Arrays.asList("文学",
                                "小说", "戏剧",
                                "长篇", "短篇", "外文", "中文",
                                null, null, null, null, "罗密欧", "哈姆雷特", "窦娥冤", "范进中举",
                                "男主角", "女主角", null, null, null, null, "买肉", "杀鸡"),
                        TreeNode::new);
        //全角符号
        System.out.println(TreePrinter.printTree(strRoot, 0, "　", "＿", "｜"));
        bytes = codec.serialize(strRoot, String::getBytes);
        strRoot = codec.deSerialize(bytes, String::new, TreeNode::new);
        System.out.println(TreePrinter.printTree(strRoot, 0, "　", "＿", "｜"));
    }

}
