package Library;

import java.util.Comparator;

public class User {
	private String name;
	private String pasword;
	private int user_id;
	private String book_t_id;
	private int user_class;
	
	public User() {
		
		
	}
	public User(String name,String pasword,int user_id,int user_class) {
		this.name = name;
		this.pasword = pasword;
		this.user_id = user_id;
		this.user_class = user_class;
		book_t_id="-";
		
	}
	public User(String name,String pasword,int user_id,int user_class,String book_t_id) {
		this.name = name;
		this.pasword = pasword;
		this.user_id = user_id;
		this.user_class = user_class;
		this.book_t_id = book_t_id;		
	}
	 public static Comparator<User> NameComparator = new Comparator<User>() {
		 
	        @Override
	        public int compare(User e1, User e2) {
	            return e1.getName().compareTo(e2.getName());
	        }
	    };
	    public static Comparator<User> IdComparator = new Comparator<User>() {
	    	 
	        @Override
	        public int compare(User e1, User e2) {
	            return e1.getUser_id() - e2.getUser_id();
	        }
	    }; 
	    public static Comparator<User> ClassComparator = new Comparator<User>() {
	    	 
	        @Override
	        public int compare(User e1, User e2) {
	            return e1.getUser_class() - e2.getUser_class();
	        }
	    };
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpasword() {
		return pasword;
	}
	public void setpasword(String pasword) {
		this.pasword = pasword;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getBook_t_id() {
		return book_t_id;
	}
	public void setBook_t_id(String book_t_id) {
		this.book_t_id = book_t_id;
	}
	public int getUser_class() {
		return user_class;
	}
	public void setUser_class(int user_class) {
		this.user_class = user_class;
	}

	
}
