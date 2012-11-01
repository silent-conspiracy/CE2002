import java.util.Comparator;


public class SortByNameComparator implements Comparator<SortByName> {
	
	public int compare(SortByName x, SortByName y) {
		return x.getName().compareTo(y.getName());
	}

}
