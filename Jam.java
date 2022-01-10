package assignment1;

public class Jam extends MarketProduct{
	
	//fields
	private int jarsNumber = 0;
	
	private int jarsPrice = 0;
	
	//constructor
	public Jam(String name, int n, int p) {
		super(name);
		this.jarsNumber = n;
		this.jarsPrice = p;
	}

	//methods
	@Override
	public int getCost() {
		return jarsNumber * jarsPrice ;
	}

	@Override
	public boolean equals (Object j) {

		boolean sameType = j.getClass() == this.getClass();
		if (sameType) {
			boolean sameName = ((MarketProduct) j).getName().equals(this.getName());
			boolean sameCost = ((MarketProduct) j).getCost() == this.getCost();
			boolean sameNumber = ((Jam) j).jarsNumber == this.jarsNumber;
			if (sameName && sameCost && sameNumber)
				return true;
			else 
				return false;
			}
		else 
			return false;
		
	}


}
