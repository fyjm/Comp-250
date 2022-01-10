package assignment1;

public class Fruit extends MarketProduct{

	//private fields
	private double fruitWeight = 0;
	
	private int fruitPrice = 0;
	
	//public methods
	public Fruit(String name, double w, int p) {
		
		super(name);
		this.fruitWeight = w;
		this.fruitPrice = p;
		
	}

	@Override
	public int getCost() {
		return  (int) (this.fruitWeight * this.fruitPrice);
	}

	@Override
	public boolean equals (Object f) {
		
		boolean sameType = f.getClass() == this.getClass();
		if (sameType) {
			boolean sameName = ((MarketProduct) f).getName().equals(this.getName());
			boolean sameCost = ((MarketProduct) f).getCost() == this.getCost();
			boolean sameWeight = ((Fruit) f).fruitWeight == this.fruitWeight;
			
			if (sameName && sameCost && sameWeight)
				return true;
			else
				return false;
			}
		else 
			return false;
		//return null != null;
		}

}
