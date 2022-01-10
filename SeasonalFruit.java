package assignment1;

public class SeasonalFruit extends Fruit{
	
	//no fields
	//constructor
	public SeasonalFruit(String name, double w, int p) {
		super(name, w, p);
	}
	
	//methods
	public int getCost() {
		return  (int) (super.getCost() * 0.85);
	}

}
