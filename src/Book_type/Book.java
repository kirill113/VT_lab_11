package Book_type;

import java.util.Comparator;



public class Book {
	private String name;
	private int book_id;
	private int count;
	private String genre;
	private int tipe;
	 public static Comparator<Book> NameComparator = new Comparator<Book>() {
		 
	        @Override
	        public int compare(Book e1, Book e2) {
	            return e1.getName().compareTo(e2.getName());
	        }
	    };
	    public static Comparator<Book> IdComparator = new Comparator<Book>() {
	    	 
	        @Override
	        public int compare(Book e1, Book e2) {
	            return e1.getBook_id() - e2.getBook_id();
	        }
	    }; 
	    
	    public static Comparator<Book> CountComparator = new Comparator<Book>() {
	    	 
	        @Override
	        public int compare(Book e1, Book e2) {
	            return e1.getCount() - e2.getCount();
	        }
	    }; 
	   
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getTipe() {
		return tipe;
	}
	public void setTipe(int tipe) {
		this.tipe = tipe;
	}
	


}
