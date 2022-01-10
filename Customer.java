package assignment1;

public class Customer {
	//private fields
	private String name;//customer's name
	private int balance = 0;
	private Basket buy;
	
	//constructors
	public Customer(String n, int ib) {
		buy = new Basket();
		this.name = n;
		this.balance = ib;
	}
	
	//get methods
	public String getName() {
		return this.name;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public Basket getBasket() {
		return this.buy;
	}
	
	public int addFunds(int ba) {
		if (ba < 0) 
			throw new IllegalArgumentException("input funds cannot be negative"); 
		else
			balance += ba;
		return balance;
	}
	
	public void addToBasket(MarketProduct p) {
		buy.add(p);
	}
	
	public boolean removeFromBasket(MarketProduct p) {
		return buy.remove(p);
		
	}
	
	public String checkOut() {
		if (balance < this.buy.getTotalCost()) 
			throw new IllegalStateException("customer balance not enough");
		else
			balance -= this.buy.getTotalCost();
			return this.buy.toString();
	}



}
