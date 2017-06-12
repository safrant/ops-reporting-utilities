package gov.nih.nci.evs.hierarchy;

import java.util.Collections;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

@SuppressWarnings({ "serial", "rawtypes" })
public class MyNode extends DefaultMutableTreeNode implements Comparable {
    public MyNode(String name) {
        super(name);
    }
    @SuppressWarnings("unchecked")
	@Override
    public void insert(final MutableTreeNode newChild, final int childIndex) {
        super.insert(newChild, childIndex);
        Collections.sort(this.children);
    }
    public int compareTo(final Object o) {
        return this.toString().compareToIgnoreCase(o.toString());
    }
}
