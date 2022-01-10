package assignment1;


public class Basket {
	//private fields
	private MarketProduct[] p_arr;
	
	//constructors
	public Basket() {
		this.p_arr = new MarketProduct[0];
	}
	
	
	public MarketProduct[] getProducts() {
		return this.p_arr.clone();//shallow copy
	}

	public int getNumOfProducts() {
		return this.p_arr.length;
	}
	
	public int getSubTotal() {
		int subtotal = 0;
		for (int i=0; i<this.p_arr.length; i++) {
			subtotal +=  this.p_arr[i].getCost();
		}
		return subtotal;
	}
	
	public int getTotalTax() {
		int totaltax = 0;
		int jamsubtotal = 0;
		
		for (int i=0; i<this.p_arr.length; i++) {
			//if is Jam, compute it's SubTotal
			if (this.p_arr[i].getClass().getName().equals("assignment1.Jam") )  {
				jamsubtotal +=  this.p_arr[i].getCost();
			}
			else 
				jamsubtotal += 0;
			
			totaltax = (int) (jamsubtotal * 0.15);
		}
		
		
		return totaltax;
	}
	
	public int getTotalCost() {
		int totalcost = 0;
		int totaltax = 0;
		int jamsubtotal = 0;
		for (int i = 0; i < p_arr.length; i++) {
			if (this.p_arr[i].getClass().getName().equals("assignment1.Jam")) {
				jamsubtotal +=  this.p_arr[i].getCost();
			}
			else 
				jamsubtotal += 0;
			
			totaltax = (int) (jamsubtotal * 0.15);
		}
		
		int subtotal = 0;
		for (int i=0; i<this.p_arr.length; i++) {
			subtotal +=  this.p_arr[i].getCost();
		}
		
		totalcost = subtotal - jamsubtotal + totaltax;
		
		return totalcost;
	}
	
	//public custom methods
	public void add(MarketProduct p)  {
		/*
		//new array with one more length
		MarketProduct[] p_arr2 = new MarketProduct[p_arr.length + 1];
		//copy the old in the new array
		System.arraycopy(p_arr, 0, p_arr2, 0, p_arr.length);
		//add element to new array
		p_arr2[p_arr.length] = p;
		//optional: set old array to new array
		p_arr = p_arr2;
		//https://www.codegrepper.com/code-examples/r/append+element+in+string+array+java
		*/
		
		
		MarketProduct[] copy = new MarketProduct[p_arr.length + 1];
		
		copy[copy.length-1] = p;
		
		for (int i = 0; i < p_arr.length; i++) {
			copy[i] = p_arr[i]; //shallow copy of each element in the array
		}
	
		MarketProduct[] shallowP = p_arr;//shallow copy of array
		int i = shallowP.length;
		p_arr = copy;
		
	}

	public boolean remove(MarketProduct p) {
		int memory = 0;
		int count = 0;
		MarketProduct[] copy2 = new MarketProduct[p_arr.length - 1];
		
		//if what you are trying to remove isn't in the array, return the original Unchanged array
		/*
		for (int i = 1; i < this.p_arr.length ; i++) {
			copy2[i-1] = p_arr[i];
			if (p_arr[p_arr.length-1].equals(p))
				break;
			else if (p_arr[i].equals(p)) {
				copy2[i-1] = p_arr[i];//shallow copy of each element in the array,element removed
			}
		}
		*/
		//0. check if the product is in the p_arr, if not, return false immediately
		for(MarketProduct pi : p_arr) {
			if(pi.equals(p)) {
				count ++;
				continue;
			}
			continue;
		}
			
		if (count == 0)
			return false;
								

		//1.take note of the first occurrence index
		for (int i = 0; i < p_arr.length ; i++) {
			if (p_arr[i].equals(p)) { 
				break;
			}
			else
				memory ++;
				continue;
		}
		//2.if it's the last element of the array, remove it without moving others behind forward
		//  if it's not the last element, remove it and moving others behind forward
		for (int i = 0, k = 0; i < p_arr.length; i++) {
			//copy2[i] = p_arr[i];
			if (i == memory) 
				continue;
			copy2[k++] = p_arr[i];
		}
		
		boolean removed = true;
		boolean not_removed = false;
		boolean result = true;
		//check if it's removed 
		for(MarketProduct p1 : copy2){
			if(p1.equals(p) && (p_arr.length == copy2.length)) {
				result = not_removed;
				break;//if still found equal one, and length is not shorter
			}
			else
				result = removed;								
		}
		p_arr = copy2;
		return result;
	}
				//check if p_arr[i] is to be removed, and don't copy it over	
				//return if p_arr[i] is removed

	public void clear() {
		//reinitialize an array
		p_arr = new MarketProduct[0];
	}
	
	/*
	 * I coded mine such that the customer's balance goes up after 
	 * they checkout with a negative total cost (I said to myself 
	 * that this case would correspond to a scenario where the customer 
	 * returned previously purchased products and got refunded).
	 */
	private double getDollars(int p) {
		int cents = p;
		double dollars = 0;
		//Assume only use cents < 1000
		if (cents > 0 && cents <= 99)
			dollars += cents * 0.01;
		else if (cents >= 100 && cents < 1000)
			dollars += (cents/100 + cents%100*0.01);
		
		return dollars;
		
	}
	public String toString() {
		
		String receipt = "";
		
		for (int i=0; i<p_arr.length; i++) {
			receipt += p_arr[i].getName();
			receipt += "\t";
			receipt += String.format("%.2f",getDollars(p_arr[i].getCost()));
			receipt += "\n";
			//System.out.println(p_arr[i].getName());
		}
		receipt += "\n";
		
		receipt += "Subtotal";
		receipt += "\t";
		receipt += String.format("%.2f",getDollars(getSubTotal()));
		receipt += "\n";
		
		receipt += "Total Tax";
		receipt += "\t";
		if (getTotalTax() != 0) {
			receipt += String.format("%.2f",getDollars(getTotalTax()));
		}
		else 
			receipt += "-";
		receipt += "\n";
		receipt += "\n";
		
		receipt += "Total Cost";
		receipt += "\t";
		receipt += String.format("%.2f",getDollars(getTotalCost()));
		System.out.println(receipt);
		
		return receipt;
		
	}
	
}
