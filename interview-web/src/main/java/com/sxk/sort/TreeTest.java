package com.sxk.sort;

import java.util.Collections;
import java.util.LinkedList;

import lombok.Data;

public class TreeTest {
    @Data
    static class TreeNode {
        private int      key      = 0;
        private String   data     = null;
        private boolean  isVisted = false;
        private TreeNode left     = null;
        private TreeNode right    = null;
    }

    public static void main(String[] args) {
        TreeTest t=new TreeTest();
        //TreeNode
        //t.helper(n, p, );
    }

    public LinkedList<TreeNode> helper(TreeNode n, TreeNode p, TreeNode q) {
        if (n == null) {
            return null;
        }
        LinkedList<TreeNode> finalPath = new LinkedList<>();
        LinkedList<TreeNode> left = helper(n.left, p, q);
        LinkedList<TreeNode> right = helper(n.right, p, q);

        // 当左右都为空时  
        if (left == null && right == null) {
            // 如果当前节点是目标节点，开启一条新路径  
            if (n == p || n == q) {
                LinkedList<TreeNode> l = new LinkedList<>();
                l.add(n);
                return l;
            } else {
                // 否则标记为空  
                return null;
            }
            // 如果左右节点都不为空，说明是最小公共祖先节点，合并两条路径  
        } else if (left != null && right != null) {
            finalPath.addAll(left);
            finalPath.add(n);
            Collections.reverse(right);
            finalPath.addAll(right);
            return left;
            // 如果当前节点是目标结点，且某一个子树不为空时，说明最小公共祖先是节点自身  
        } else if (left != null) {
            left.add(n);
            if (n == p || n == q) {
                finalPath.addAll(left);
            }
            return left;
        } else {
            right.add(n);
            if (n == p || n == q) {
                finalPath.addAll(right);
            }
            return right;
        }
    }
}
