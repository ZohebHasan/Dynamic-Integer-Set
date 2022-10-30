package cse214hw2;
import java.util.List;
import java.util.ArrayList;
public class DynamicIntegerSet implements DynamicSet {
    private Node root;
    private int length;

    public DynamicIntegerSet(){
        this.root = null;
        this.length = 0;
    }

    public Node root() {
        return this.root;
    }
   
    public static class Node implements PrintableNode {
        Integer data;
        Node left, right;

        Node(int x) {
            this(x, null, null);
        }

        Node(int x, Node left, Node right) {
            this.data = x;
            this.left = left;
            this.right = right;
        }

        @Override
        public String getValueAsString() {
            return data.toString();
        }

        @Override
        public PrintableNode getLeft() {
            return left;
        }

        @Override
        public PrintableNode getRight() {
            return right;
        }
    }


    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean contains(Integer x) { // Theta log base 2 n  // functional
        Node temp = root();
        while(temp != null){
            if(x.equals(temp.data))
                return true;
            else if(x < temp.data){
                temp = temp.left;
            }
            else if(x > temp.data){
                temp = temp.right;
            }
        }
        return false;
    }

    @Override
    public boolean add(Integer x) { // Theta log base 2 n  // functional
        Node temp = root();
        Node prev = null;
        Node newNode = new Node(x);
        if(temp== null){
            root = newNode;
            length++;
            return true;
        }
        while( temp != null) {
            if (temp.data > x) {
                prev = temp;
                temp = temp.left;
            } else if (temp.data < x) {
                prev = temp;
                temp = temp.right;
            } else if (x.equals(temp.data)){
                return false;
            }
        }
        if( prev.data > x) {
            prev.left = newNode;
            length++;
            return true;
        }
        else {
            prev.right = newNode;
            length++;
            return true;
        }
    }



    @Override
    public boolean remove(Integer x){ // Theta log base 2 n  // functional
        Node curr = root();
        Node prev = null;
        if(curr == null)
            return false;
        else
            while(curr != null && !x.equals(curr.data) ){
                prev = curr;
                if(x < curr.data)
                    curr = curr.left;
                else
                    curr = curr.right;
            }
        //checking if the node to be deleted has at most one child.
        if(curr.left == null  || curr.right == null){
           Node newCurr;
           if(curr.left == null)
               newCurr = curr.right;
           else
               newCurr = curr.left;
           //checking if the node to be deleted is the root.
           if(prev == null){
               this.root = newCurr;
               length--;
               return true;
           }
           if(curr == prev.left) {
               prev.left = newCurr;
               length--;
               return true;
           }
           else {
               prev.right = newCurr;
               length--;
               return true;
           }
        }
        //Node to be deleted has two child
        else{
            Node prev2 = null;
            Node temp;
            //Compute the inorder successor
            temp = curr.right;
            while(temp.left != null){
                prev2 = temp;
                temp = temp.left;
            }
            if(prev2 != null){
                prev2.left = temp.right;
            }
            else{
                curr.right = temp.right;
            }
            curr.data = temp.data;
            length--;
            return true;
        }
    }
    // Keeping it for grader's convenience.
    public static void printTree(PrintableNode node) {
        List<List<String>> lines = new ArrayList<>();
        List<PrintableNode> level = new ArrayList<>();
        List<PrintableNode> next = new ArrayList<>();

        level.add(node);
        int nn = 1;
        int widest = 0;
        while (nn != 0) {
            List<String> line = new ArrayList<>();
            nn = 0;
            for (PrintableNode n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.getValueAsString();
                    line.add(aa);
                    if (aa.length() > widest)
                        widest = aa.length();
                    next.add(n.getLeft());
                    next.add(n.getRight());
                    if (n.getLeft() != null)
                        nn++;
                    if (n.getRight() != null)
                        nn++;
                }
            }
            if (widest % 2 == 1)
                widest++;
            lines.add(line);
            List<PrintableNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perPiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perPiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perPiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (String f : line) {
                if (f == null) f = "";
                final float a = perPiece / 2f - f.length() / 2f;
                int gap1 = (int) Math.ceil(a);
                int gap2 = (int) Math.floor(a);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            perPiece /= 2;
        }
    }
}

