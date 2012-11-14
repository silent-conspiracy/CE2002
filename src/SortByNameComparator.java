import java.util.Comparator;


public class SortByNameComparator implements Comparator<SortByName> {
	// Used mainly for sorting, when passed to Collections.sort()
	public int compare(SortByName x, SortByName y) {
		return x.getName().compareTo(y.getName());
	}

}
