/** a generic class that implements the dynamic array.
 * list used as column in the game
 * @param <T> hold any item to add to the grid
 * @author Fatemah Bahzad
 */

public class Column<T> {
	/** a generic class that implements the dynamic array.
 	* list used as column in the game
 	* @param DEFAULT_CAPACITY the  default capacity for the array, data
 	*/
	//default initial capacity / minimum capacity
	private static final int DEFAULT_CAPACITY = 2;
	
	/**
	 data is an array to hold tokens in it.
	 */
	private T[] data;
	/**
	 number of tokens in the column.
	 */
	private int size;
	/**
	 use to return items.
	 */
	private T temp;

	/**
	  * create an empty column with the DEFAULT_CAPACITY and size zero.
	  */
	@SuppressWarnings("unchecked")
	public Column() {
		data= (T[]) new Object[DEFAULT_CAPACITY];
		size=0;
	
	}
	/**
	  * create an empty column with the initialCapacity and size =initialCapacity .
	  * @param initialCapacity how many items data can hold
	  * @return 
	  */
	@SuppressWarnings("unchecked")
	public Column(int initialCapacity) {
		if (initialCapacity<1) {
			throw new IllegalArgumentException("Capacity must be positive");
		}
		data= (T[]) new Object[initialCapacity];
		size=initialCapacity;
	}
	
	/**
	  * get the current number of elements.
	  * @return int current number of elements
	  */
	public int size() {	

		return size; 
	}  
	/**
	  * get the capacity of the array list.
	  * @return capacity of the array list.
	*/
	public int capacity() { 
		if (size==0) {
			return DEFAULT_CAPACITY;
		}
		return data.length; 
	}

	/**
	  * Change the item at the given index to be the given value.
	  * @param index the place of the value to change 
	  * @param value of the new value in replacement 
	  * @return old item at that index.
	  */
	public T set(int index, T value) {
		if (index<0|| index>size) {
			throw new IndexOutOfBoundsException( "Index: " + index + " out of bounds!");
		}
		temp=data[index];//to return the old value
		data[index]=value;
		return temp; 
		
	}
	/**
	  * get the item from the index.
	  * @param index of the value 
	  * @return return item from the index
	  */
	public T get(int index) {
		if (index<0|| index>size) {
			throw new IndexOutOfBoundsException( "Index: " + index + " out of bounds!");
		}
		return data[index]; 
				
	}
	/**
	  * add an element to the end of the array. 
	  * @param value to add 
	  * @return 
	  */
	@SuppressWarnings("unchecked")
	public void add(T value) {
		if (size==data.length) { //if the list is full
			//double cap
			T[] data2=(T[]) new Object[size*2];
			
			for (int i=0; i<size; i++) {
				data2[i]=data[i];
			}
			data=data2;
		}
		data[size]=value; //add it to the end after changing the capacity
		size++; 
	}

		
	/**
	  * Insert the given value at the given index. 
	  * @param index to insert the value in 
	  *@param value to add 
	  * @return 
	  */
	@SuppressWarnings("unchecked")
	public void add(int index, T value) {
		//check the index
		if (index<0|| index>size) {
			throw new IndexOutOfBoundsException("out of bound");
		}
		//make sure we have enough space
		if (size==data.length) {
			//double cap
			T[] data2=(T[]) new Object[size*2];
			for (int i=0; i<size; i++) {
				data2[i]=data[i];
			}
			data=data2;
		}
				
		//shifting 
		//try using the same thing for remove
		for (int i=size;i>index; i--) //start from the end
			data[i]=data[i-1];
		data[index]=value;// insert 
		size++;
	}


	
	/**
	  * remove the item at the index.
	  * @param index to remove the value from 
	  * @return the deleted item 
	  */
//other way to remove the index by shifting 
// 	for(int i=index;i<size-1;i++){
// 	        data[i]=data[i+1];
// 	      }
// 	      data[size-1]=null;
// 	      size--;
// 	    
	@SuppressWarnings("unchecked")
	public T delete(int index) {
		temp=get(index); //get the element to return it 
		if (index<0|| index>=size) {
			throw new IndexOutOfBoundsException( "Index: " + index + " out of bounds!");
		}
		T[] data2=(T[]) new Object[data.length];//create a new array with the same length
		for(int i=0,j=0; i<data.length;i++) {
			if (i==index) {//skip the index
				size--; //remove it from the size
				continue;
			}
			data2[j++]=data[i];
		}
		
		data=data2;
		if ((size<(double)data.length/3) && data.length/2>DEFAULT_CAPACITY ) {
			data2=(T[]) new Object[data.length/2]; //cutting the capacity in half if the size is 
			//less then 1/3 of it and if it is not less then DEFAULT_CAPACITY when halved
			for (int i=0; i<data2.length; i++) {
				data2[i]=data[i];
			
			}
			data=data2;
		}
		return temp;
	}  

	
	
	
	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//*******      Edit it as much as you'd like!    *******
	//*******		Remember to add JavaDoc			 *******
	//******************************************************
	/**
	*This method is provided for debugging purposes.
	*(use/modify as much as you'd like), it just prints out the column for easy viewing.
	*@return String
	*/
	
	public String toString() {

		StringBuilder s = new StringBuilder("Column with " + size()
			+ " items and a capacity of " + capacity() + ":");
		for (int i = 0; i < size(); i++) {
			s.append("\n  ["+i+"]: " + get(i));
		}
		return s.toString();
		
	}
	/**
	*These are _sample_ tests. If you're seeing all the "yays" that's
	*an excellent first step! But it might not mean your code is 100%
	*working... You may edit this as much as you want, so you can add
	*own tests here, modify these tests, or whatever you need!
	*@param args call the class
	*/
	public static void main(String args[]){
		//create a column of integers
		Column<Integer> nums = new Column<>();
		if((nums.size() == 0) && (nums.capacity() == 2)){
			System.out.println("Yay 1");
		}

		//append some numbers 
		for(int i = 0; i < 3; i++) {
			nums.add(i*2);
		}
		
		if(nums.size() == 3 && nums.get(2) == 4 && nums.capacity() == 4){
			System.out.println("Yay 2");
		}
		
		//create a column of strings
		Column<String> msg = new Column<>();
		
		//insert some strings
		msg.add(0,"world");
		msg.add(0,"hello");
		msg.add(1,"new");
		msg.add(3,"!");
		
		//checking
		if (msg.get(0).equals("hello") && msg.set(1,"beautiful").equals("new") 
			&& msg.size() == 4 && msg.capacity() == 4){
			System.out.println("Yay 3");
		}
		
		//delete 
		if (msg.delete(1).equals("beautiful") && msg.get(1).equals("world")  
			&& msg.size() == 3 ){
			System.out.println("Yay 4");
		}

		//shrinking
		nums.add(100);
		nums.add(0, -10);
		if (nums.delete(0) == -10 && nums.delete(1) == 2 && nums.delete(2) == 100
			&& nums.size() == 2 && nums.capacity() == 4) {
			System.out.println("Yay 5");
		}
		
	}
	

	

}