import java.io.DataOutputStream;
import java.io.IOException;


public class MaxTreeHeap {
    private static int heap_size;
    private Node root;

    MaxTreeHeap(int index) {
        heap_size = index;
    }

    private static int parent(int i) {
        return (i - 1) / 2;
    }

    private static int left(int i) {
        return (i * 2) + 1;
    }

    private static int right(int i) {
        return (i * 2) + 2;
    }

    public int num_of_Nodes() {
        return heap_size;
    }

    public int ShowMax(){
        return root.key;
    }

    public static MaxTreeHeap BuildHeapT(int[] A) {
        BuildHeap(A);
        Node[] B = new Node[A.length];
        for (int i = 0; i < A.length; i++) {
            Node x = new Node(A[i]);
            B[i] = x;
        }
        for (int i = 0; i < A.length; i++) {
            B[i].parent = B[parent(i)];
            if (left(i) < A.length) {
                B[i].left = B[left(i)];
            }
            if (right(i) < A.length) {
                B[i].right = B[right(i)];
            }
        }
        B[0].parent = null;
        MaxTreeHeap T = new MaxTreeHeap(A.length);
        T.root = B[0];
        return T;
    }


    public static void BuildHeap(int A[]) {
        for (int i = A.length - 1; i >= 0; i--) {
            Heapify(A, i);
        }
    }


    public static void Heapify(int[] A, int i) {

        int l = left(i);
        int biggest = i;
        if (l < A.length && A[l] > A[i]) {
            biggest = l;
        }
        int r = right(i);
        if (r < A.length && A[r] > A[biggest]) {
            biggest = r;
        }
        if (biggest != i) {
            Swap(A, i, biggest);
            Heapify(A, biggest);
        }
    }


    public static void Swap(int A[], int a, int b) {
        int tmp = A[a];
        A[a] = A[b];
        A[b] = tmp;
    }

    public void HeapifyT(Node x) {
        Node smallest = x;
        if (x.left != null && x.left.key > x.key)
            smallest = x.left;
        if (x.right != null && x.right.key > smallest.key)
            smallest = x.right;
        if (smallest != x) {
            SwapKeys(x, smallest);
            HeapifyT(smallest);
        }
    }

    public void HeapInsert(int k) {
        heap_size++;
        Node x = new Node(k);
        if (root == null) {
            root = x;
        } else {
            int[] path = pathFromRoot(heap_size);
            Node y = root;
            for (int j = 0; j < path.length - 1; j++) {
                x = y;
                if (path[j] == 0)
                    y = x.left;
                else
                    y = x.right;

            }
            x = y;
            y = new Node(k);
            y.parent = x;
            if (path[path.length - 1] == 0) {
                x.left = y;
            } else {
                x.right = y;
            }
            while (y.key > x.key) {
                SwapKeys(y, x);
                y = x;
                x = x.parent;
                if (x==null || y==null)
                    return;
            }
        }
    }

    private void SwapKeys(Node a, Node b) {
        int x = a.key;
        a.key = b.key;
        b.key = x;
    }

    private int[] pathFromRoot(int i) {
        int index = 0;
        int[] path_to_root = new int[i];
        while (i > 1) {
            path_to_root[index] = i % 2;
            i = i / 2;
            index++;
        }
        int[] path_from_root = new int[index];
        for (int j = 0; index > 0; j++, index--) {
            path_from_root[j] = path_to_root[index - 1];
        }
        return path_from_root;
    }


    public int HeapExtractMax() throws IOException {
        if (heap_size < 1) {
            throw new IOException();
        }
        int max = this.root.key;
        if (heap_size == 1) {
            root = null;
            return max;
        }
        int[] path_to_last = pathFromRoot(heap_size);
        Node last = root;
        for (int j : path_to_last) {
            if (j == 0)
                last = last.left;
            else
                last = last.right;
        }
        root.key = last.key;
        if (path_to_last[path_to_last.length - 1] == 0)
            last.parent.left = null;
        else
            last.parent.right = null;
        heap_size--;
        HeapifyT(root);
        return max;
    }

    public void printByLayer(DataOutputStream out) throws IOException {
        // Base Case
        if (root == null)
            return;
        Node[] q = new Node[heap_size];
        int queue_size = 0;
        int front = 0, end = 0;
        q[end++] = root;
        queue_size++;
        while (true) {
            // nodeCount (queue size) indicates number of nodes
            // at current level.
            int nodeCount = queue_size;
            if (nodeCount == 0)
                break;
            // Dequeue all nodes of current level and Enqueue all
            // nodes of next level
            while (nodeCount > 0) {
                Node current = q[front];
                nodeCount--;
                queue_size--;
                front++;
                out.writeBytes(Integer.toString(current.key));
                if (current.left != null) {
                    q[end++] = current.left;
                    queue_size++;
                }
                if (current.right != null) {
                    q[end++] = current.right;
                    queue_size++;
                }
                if (nodeCount != 0)
                    out.writeBytes(",");
            }
            out.writeBytes(System.lineSeparator());
        }
    }
}


