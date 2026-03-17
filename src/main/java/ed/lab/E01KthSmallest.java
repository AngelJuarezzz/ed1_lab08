package ed.lab;

public class E01KthSmallest {

    private int count;
    private int result;

    public int kthSmallest(TreeNode<Integer> root, int k) {
        count = 0;
        result = 0;
        inorder(root, k);
        return result;
    }

    private void inorder(TreeNode<Integer> node, int k) {
        if (node == null) return;

        inorder(node.left, k);

        count++;
        if (count == k) {
            result = node.value;
            return;
        }

        inorder(node.right, k);
    }
}