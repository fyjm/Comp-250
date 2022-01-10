package assignment1;

public abstract class MarketProduct {

	//private fields
	private String name;
	
	//public methods
	
	/*A constructor that takes a String as input indicating the name of product and uses it
	to initialize the corresponding attribute. */
	public MarketProduct(String name) {
		this.name = name;
		
	}
	

	public final String getName() {
		return this.name;
	}
	
	public abstract int getCost();
	
	public abstract boolean equals(Object a);
	
}
