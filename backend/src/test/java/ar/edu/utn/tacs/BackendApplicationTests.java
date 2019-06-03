package ar.edu.utn.tacs;

import ar.edu.utn.tacs.model.PlacesList;
import ar.edu.utn.tacs.model.places.Venue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	PlacesList list = new PlacesList();

	@Before
	public void setUp() {
		list.setId("1");
		list.setName("test1");

		ArrayList<Venue> places = new ArrayList<Venue>();
		Venue venue1 = new Venue();
		venue1.setId("1");
		places.add(venue1);

		Venue venue2 = new Venue();
		venue2.setId("2");
		places.add(venue2);

		Venue venue3 = new Venue();
		venue3.setId("3");
		places.add(venue3);

		list.setPlaces(places);

		ArrayList<String> placesIds = new ArrayList<String>();
		placesIds.add("1");
		list.setVisitedPlacesIds(placesIds);
	}

	@Test
	public void testPlacesList() {
		Assert.assertEquals("1", list.getId());
		Assert.assertEquals("test1", list.getName());
		Assert.assertEquals(3, list.getPlaces().size());
		Assert.assertEquals(1, list.getVisitedPlacesIds().size());
		Assert.assertEquals(true, list.getVisitedPlacesIds().contains("1"));
		list.checkin("2");
		Assert.assertEquals(true, list.getVisitedPlacesIds().contains("2"));
	}

}
