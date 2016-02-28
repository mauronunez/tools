package cl.chileindica.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;



public class Main {


	
	public static void main(String args[]) throws Exception{
		query(args);
	}
	
	public static void query(String args[]) throws Exception{
		MongoClient mongoClient=new MongoClient();
		MongoDatabase db=mongoClient.getDatabase("demo");
		FindIterable<Document> iterable=db.getCollection("restaurants").find(new Document("address.zipcode", "10075"));
		
		iterable.forEach(new Block<Document>() {
		    public void apply(final Document document) {
		        System.out.println(document);
		    }
		});
		
	}
	
	public static void insert(String args[]) throws Exception{
		
		MongoClient mongoClient=new MongoClient();
		MongoDatabase db=mongoClient.getDatabase("demo");
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		db.getCollection("restaurants").insertOne(
		        new Document("address",
		                new Document()
		                        .append("street", "2 Avenue")
		                        .append("zipcode", "10075")
		                        .append("building", "1480")
		                        .append("coord", asList(-73.9557413, 40.7720266)))
		                .append("borough", "Manhattan")
		                .append("cuisine", "Italian")
		                .append("grades", asList(
		                        new Document()
		                                .append("date", format.parse("2014-10-01T00:00:00Z"))
		                                .append("grade", "A")
		                                .append("score", 11),
		                        new Document()
		                                .append("date", format.parse("2014-01-16T00:00:00Z"))
		                                .append("grade", "B")
		                                .append("score", 17)))
		                .append("name", "Vella")
		                .append("restaurant_id", "41704620"));
		
	}
	
}
