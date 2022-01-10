package assignment1;

public class Egg extends MarketProduct{

	//fields
	private int eggNumber = 0;
	
	private int eggPrice = 0;
	
	//constructor
	public Egg(String name, int n, int p) {
		
		super(name);
		this.eggNumber = n;
		this.eggPrice = p;
		//The constructor uses the inputs to create a MarketProduct and initialize the corresponding fields.
	}
	
	//methods

	@Override
	public int getCost() {
		return (this.eggNumber * this.eggPrice)/12;//cost per egg
	}

	@Override
	public boolean equals (Object e) {

		boolean sameType = e.getClass() == this.getClass();//if not the sameType first, comparing others will have error
		if (sameType) {
			boolean sameName = ((MarketProduct) e).getName().equals(this.getName());
			boolean sameCost = ((MarketProduct) e).getCost() == this.getCost();
			boolean sameNumber = ((Egg) e).eggNumber == this.eggNumber;
			if (sameName && sameCost && sameNumber)
				return true;
			else
				return false;
		}
		else 
			return false;
		
	}
	
}

	