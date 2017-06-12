package gov.nih.nci.evs.subsetdiff;

public class DiffElement implements Comparable<DiffElement> {

    private String elementChanged;
    private String oldValue;
    private String newValue;
    private SubsetElement element;
    private String typeOfChange;

    public String getElementChanged() {
        return this.elementChanged;
    }

    public void setElementChanged(String elementChanged) {
        this.elementChanged = elementChanged;
    }

    public String getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return this.newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public SubsetElement getElement() {
        return this.element;
    }

    public void setElement(SubsetElement element) {
        this.element = element;
    }

    public String getTypeOfChange() {
        return this.typeOfChange;
    }

    public void setTypeOfChange(String typeOfChange) {
        this.typeOfChange = typeOfChange;
    }

    public DiffElement(SubsetElement se) {
        this.element = se;

    }

    @Override
    public int compareTo(DiffElement o) {
        return this.element.compareTo(o.getElement());

    }

    @Override
    public String toString() {
        String deString = "";
        deString = deString + this.typeOfChange + "\t";
        deString = deString + this.element.getSubsetCode() + "\t";
        deString = deString + this.element.getConceptCode() + "\t";
        deString = deString + this.elementChanged + "\t";
        deString = deString + this.oldValue + "\t";
        deString = deString + this.newValue;

        return deString;
    }
}
