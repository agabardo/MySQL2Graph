import java.io.FileWriter;

public class MySQL2Graph {

	public static String url = "jdbc:mysql://localhost:8889/wine";
	public static String username = "root";
	public static String password = "1234";
	
	public static void main(String[] args) {
		
		FileWriter fw;
		try {
			fw = new FileWriter("graph.net");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
