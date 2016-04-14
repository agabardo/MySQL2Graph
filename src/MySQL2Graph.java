import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class MySQL2Graph {

	public static String url = "jdbc:mysql://localhost:8889/Amazon";
	public static String username = "root";
	public static String password = "1234";

	public static void main(String[] args) {

		FileWriter fw;
		try {
			fw = new FileWriter("graph.net");

			String opening = "*Vertices ";

			// Connecting.
			Connection connection = DriverManager.getConnection(url, username, password);
			// Creating an SQL Statement.
			Statement stmt1 = connection.createStatement();

			// Counting the nodes.
			ResultSet rs1 = stmt1.executeQuery("SELECT count(*) as numberOfNodes FROM Amazon.allNodes LIMIT 1");
			while (rs1.next())
				opening += rs1.getInt("numberOfNodes");
			rs1.close();

			fw.append(opening);

			// Reading the nodes.
			ResultSet rs2 = stmt1.executeQuery("SELECT id,label FROM Amazon.allNodes");

			while (rs2.next())
				fw.append("\n" + rs2.getString("id") + " \"" + rs2.getString("label").replace("\n", " ") + "\"");
			rs2.close();

			fw.append("\n*Edges");

			// reading the edges.
			ResultSet rs3 = stmt1.executeQuery("SELECT source,target,weight FROM Amazon.allEdges");
			while (rs3.next())
				fw.append(rs3.getString("source") + " " + rs3.getString("target") + " " + rs3.getString("weight"));
			rs3.close();

			fw.close();
			stmt1.close();
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		Graph viewer = new SingleGraph("Viewer");
		try {
			viewer.read("graph.net");
			viewer.addAttribute("ui.quality");
			viewer.addAttribute("ui.antialias");
			// viewer.addAttribute("ui.stylesheet","url('style.css')");
			viewer.display();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
