package lab3;

import java.util.Scanner;

class DLLNode<E> {
    protected E element;
    protected DLLNode<E> pred, succ;
    public DLLNode(E elem, DLLNode<E> pred, DLLNode<E> succ) {
        this.element = elem;
        this.pred = pred;
        this.succ = succ;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}

class DLL<E> {
    private DLLNode<E> first, last;

    public DLL() {
        // Construct an empty SLL
        this.first = null;
        this.last = null;
    }

    public void insertFirst(E o) {
        DLLNode<E> ins = new DLLNode<E>(o, null, first);
        if (first == null)
            last = ins;
        else
            first.pred = ins;
        first = ins;
    }

    public void insertLast(E o) {
        if (first == null)
            insertFirst(o);
        else {
            DLLNode<E> ins = new DLLNode<E>(o, last, null);
            last.succ = ins;
            last = ins;
        }
    }

    public void insertAfter(E o, DLLNode<E> after) {
        if (after == last) {
            insertLast(o);
            return;
        }
        DLLNode<E> ins = new DLLNode<E>(o, after, after.succ);
        after.succ.pred = ins;
        after.succ = ins;
    }

    public void insertBefore(E o, DLLNode<E> before) {
        if (before == first) {
            insertFirst(o);
            return;
        }
        DLLNode<E> ins = new DLLNode<E>(o, before.pred, before);
        before.pred.succ = ins;
        before.pred = ins;
    }

    public E deleteFirst() {
        if (first != null) {
            DLLNode<E> tmp = first;
            first = first.succ;
            if (first != null) first.pred = null;
            if (first == null)
                last = null;
            return tmp.element;
        } else
            return null;
    }

    public E deleteLast() {
        if (first != null) {
            if (first.succ == null)
                return deleteFirst();
            else {
                DLLNode<E> tmp = last;
                last = last.pred;
                last.succ = null;
                return tmp.element;
            }
        } else
            return null;
    }

    public E delete(DLLNode<E> node) {
        if (node == first) {
            return deleteFirst();
        }
        if (node == last) {
            return deleteLast();
        }
        node.pred.succ = node.succ;
        node.succ.pred = node.pred;
        return node.element;

    }

    public DLLNode<E> find(E o) {
        if (first != null) {
            DLLNode<E> tmp = first;
            while (!tmp.element.equals(o) && tmp.succ != null)
                tmp = tmp.succ;
            if (tmp.element.equals(o)) {
                return tmp;
            } else {
                System.out.println("Elementot ne postoi vo listata");
            }
        } else {
            System.out.println("Listata e prazna");
        }
        return null;
    }

    public void deleteList() {
        first = null;
        last = null;
    }

    public int getSize() {
        int listSize = 0;
        DLLNode<E> tmp = first;
        while(tmp != null) {
            listSize++;
            tmp = tmp.succ;
        }
        return listSize;
    }

    @Override
    public String toString() {
        String ret = new String();
        if (first != null) {
            DLLNode<E> tmp = first;
            ret += tmp.toString();
            while (tmp.succ != null) {
                tmp = tmp.succ;
                ret += "<->" + tmp.toString();
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public String toStringR() {
        String ret = new String();
        if (last != null) {
            DLLNode<E> tmp = last;
            ret += tmp.toString();
            while (tmp.pred != null) {
                tmp = tmp.pred;
                ret += "<->" + tmp.toString();
            }
        } else
            ret = "Prazna lista!!!";
        return ret;
    }

    public DLLNode<E> getFirst() {
        return first;
    }

    public DLLNode<E> getLast() {

        return last;
    }

    public void setFirst(DLLNode<E> node) {
        this.first = node;
    }

    public void setLast(DLLNode<E> node) {
        this.last = node;
    }

    public void mirror() {

        DLLNode<E> tmp = null;
        DLLNode<E> current = first;
        last = first;
        while(current!=null) {
            tmp = current.pred;
            current.pred = current.succ;
            current.succ = tmp;
            current = current.pred;
        }

        if(tmp!=null && tmp.pred!=null) {
            first=tmp.pred;
        }
    }
}

public class DLLVojska {

    public static DLL<Integer> vojska(DLL<Integer> lista, int a, int b, int c, int d) {
        //Vasiot kod tuka
        DLLNode<Integer> firstNodeFirstInterval =  lista.find(a);
        DLLNode<Integer> lastNodeFirstInterval =  lista.find(b);
        DLLNode<Integer> firstNodeSecondInterval =  lista.find(c);
        DLLNode<Integer> lastNodeSecondInterval =  lista.find(d);
        DLLNode<Integer> secondIntervalSuccessor = new DLLNode<>(null, null, lastNodeSecondInterval.succ);
        DLLNode<Integer> secondIntervalPredecessor = new DLLNode<>(null, firstNodeSecondInterval.pred, null);
        DLLNode<Integer> firstIntervalPredecessor = new DLLNode<>(null, firstNodeFirstInterval.pred, null);

        boolean intervalsTogether = lastNodeFirstInterval.succ.equals(firstNodeSecondInterval);
        boolean fNFIisFirst = firstNodeFirstInterval.equals(lista.getFirst());
        boolean lNSIisLast = lastNodeSecondInterval.equals(lista.getLast());
        //lnsi
        if(!intervalsTogether)
            lastNodeSecondInterval.succ = lastNodeFirstInterval.succ;
        else
            lastNodeSecondInterval.succ = firstNodeFirstInterval;
        lastNodeSecondInterval.succ.pred = lastNodeSecondInterval;
        //fnsi
        if(!fNFIisFirst) {
            if(!intervalsTogether)
                firstNodeSecondInterval.pred = firstNodeFirstInterval.pred;
            else
                firstNodeSecondInterval.pred = firstIntervalPredecessor.pred;
            firstNodeSecondInterval.pred.succ = firstNodeSecondInterval;
        } else {
            firstNodeSecondInterval.pred = null;
        }
        //lnfi
        if(!lNSIisLast) {
            lastNodeFirstInterval.succ = secondIntervalSuccessor.succ;
            lastNodeFirstInterval.succ.pred = lastNodeFirstInterval;
        } else {
            lastNodeFirstInterval.succ = null;
        }
        //fnfi
        if(!intervalsTogether) {
            firstNodeFirstInterval.pred = secondIntervalPredecessor.pred;
            firstNodeFirstInterval.pred.succ = firstNodeFirstInterval;
        }
        //if first or last
        if(fNFIisFirst) lista.setFirst(firstNodeSecondInterval);
        if(lNSIisLast) lista.setLast(lastNodeFirstInterval);
        return lista;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int n = input.nextInt();

        DLL<Integer> lista = new DLL<>();
        for(int i=0;i<n;i++) {
            lista.insertLast(input.nextInt());
        }

        int a = input.nextInt();
        int b = input.nextInt();

        int c = input.nextInt();
        int d = input.nextInt();

        DLL<Integer> result = vojska(lista, a, b, c, d);

        System.out.println(result);

    }
}
