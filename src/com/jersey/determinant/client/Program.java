package com.jersey.determinant.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.jersey.determinant.Determinant;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Determinant deter = new Determinant();
		int order =4;
		deter.setOrder(order);
		double [][] array = new double[order][order];
		for(int i=0;i<order;i++)
		{			
			array[i][0]=array[i][1]=array[i][2]=array[i][3]=1;
			if(i>0)
			{
				array[i][0]=array[i-1][0]*1;
				array[i][1]=array[i-1][1]*-1;
				array[i][2]=array[i-1][2]*2;
				array[i][3]=array[i-1][3]*-2;
			}
		}
		deter.setArray(array);
		System.out.println("before change :");
		printArray(array);
		
		ClientConfig config = new ClientConfig();	
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		
		Response res = target.path("/rest/deter/calc").request().accept(MediaType.APPLICATION_JSON).post(Entity.entity(deter,MediaType.APPLICATION_JSON));
		//Response res = target.path("/rest/deter/calc").request().accept(MediaType.APPLICATION_XML).post(Entity.entity(deter,MediaType.APPLICATION_XML));
		Determinant d = res.readEntity(Determinant.class);
		
		System.out.println("alter change :");
		printArray(d.getArray());
		
		System.out.format("the determinant value :%.2f",d.getValue());
	}
	private static URI getBaseURI() {
	    //return UriBuilder.fromUri("http://localhost:8080/com.vogella.jersey.first").build();
		return UriBuilder.fromUri("http://localhost:8080/com.jersey.determinant").build();
	}
	
	public static void printArray(double[][] array)
	{
		for(int i=0;i<array.length;i++)
		{			
			for(int j=0;j<array[0].length;j++)
			{
				System.out.format("\t%.2f,", array[i][j]);
			}
			System.out.println();
		}		
	}

}
