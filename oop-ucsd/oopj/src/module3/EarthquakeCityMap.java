package module3;

//Java utilities libraries
import java.awt.*;
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import de.fhpotsdam.unfolding.providers.Microsoft;
import module1.HelloWorld;
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Mauring, Jr.
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	private final int WIDTH = 950;
	private final int HEIGHT = 600;

	public void setup() {
		size(WIDTH, HEIGHT, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, WIDTH/4.75f, HEIGHT/12f, WIDTH/1.35f, HEIGHT/1.2f, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, WIDTH/4.75f, HEIGHT/12f, WIDTH/1.35f, HEIGHT/1.2f, new Microsoft.AerialProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		// add border for map


	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    //TODO (Step 3): Add a loop here that calls createMarker (see below)
		for (PointFeature f : earthquakes) {
			// to create a new SimplePointMarker for each PointFeature in
			// earthquakes.  Then add each new SimplePointMarker to the
			// List markers (so that it will be added to the map in the line below)
			SimplePointMarker currMarker = createMarker(f);
			markers.add(currMarker);
		}

	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * 
	 * In step 3 You can use this method as-is.  Call it from a loop in the 
	 * setp method.  
	 * 
	 * TODO (Step 4): Add code to this method so that it adds the proper 
	 * styling to each marker based on the magnitude of the earthquake.  
	*/
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
		//System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int lightYellow = color(255, 255, 0);
		int lightBlue = color(173,216,230);
		int lightRed = color(255,127,127);

		// TODO (Step 4): Add code below to style the marker's size and color 
	    // according to the magnitude of the earthquake.  
	    // Don't forget about the constants THRESHOLD_MODERATE and 
	    // THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables (and change their value in the code 
	    // above if you want to change what you mean by "moderate" and "light")
		marker.setColor(lightRed);
		marker.setRadius(16);
		if (mag < THRESHOLD_LIGHT) {
			marker.setColor(lightBlue);
			marker.setRadius(6);
		}
		else if (mag < THRESHOLD_MODERATE) {
			marker.setColor(lightYellow);
			marker.setRadius(10);
		}

	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(251,250,245);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{
		int lightYellow = color(255, 255, 0);
		int lightBlue = color(173,216,230);
		int lightRed = color(255,127,127);

		// key border layout
		fill(225,217,209);
		rect(WIDTH/38f,HEIGHT/12f,WIDTH/6.33f,HEIGHT/2.4f);

		// text attributes
		fill(65,74,76);
		textSize(11);
		text("Earthquake Key",WIDTH/19f,HEIGHT/8f);

		//textAlign(RIGHT);
		text("5.0+ Magnitude",WIDTH/12.67f,HEIGHT/4.8f);
		text("4.0+ Magnitude",WIDTH/12.67f,HEIGHT/3.42f);
		text("Below 4.0",WIDTH/12.67f,HEIGHT/2.67f);

		fill(lightRed);
		ellipse(WIDTH/19f,HEIGHT/4.8f,16,16);
		fill(lightYellow);
		ellipse(WIDTH/19f,HEIGHT/3.42f,10,10);
		fill(lightBlue);
		ellipse(WIDTH/19f,HEIGHT/2.67f,6,10);

		// Remember you can use Processing's graphics methods here
	
	}

	public static void main (String[] args) {
		EarthquakeCityMap pt = new EarthquakeCityMap();
		PApplet.runSketch(new String[]{"EarthquakeCityMap"}, pt);
	}
}
