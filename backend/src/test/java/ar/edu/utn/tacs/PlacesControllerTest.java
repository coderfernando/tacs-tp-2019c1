package ar.edu.utn.tacs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.utn.tacs.controller.PlacesController;
import ar.edu.utn.tacs.exceptions.MissingParametersException;
import ar.edu.utn.tacs.exceptions.VenuesNotFoundException;
import ar.edu.utn.tacs.model.places.Venue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PlacesControllerTest {

    @Autowired
    PlacesController pc;

    @Test
    public void findById_returnVenue() {

        Venue cedron = new Venue();
        try {
            cedron = this.pc.getPlaceById("4ba7feaef964a520e3c439e3");
        } catch (VenuesNotFoundException | MissingParametersException e) {}

        assertEquals("Prueba al pasar id del cedron, devuelve el venue del cedron", "El Cedrón", cedron.getName());
    }


    @Test
    public void findBySearch_returnVenue() {
        ArrayList<Venue> encontrados = new ArrayList<Venue>();
        try {
            encontrados = this.pc.getPlacesBySearch("El Cedrón", "Buenos Aires", null, null, "9999");
        } catch (VenuesNotFoundException | MissingParametersException e) {}
        Venue cedron = encontrados.get(0);
        assertEquals("Prueba al buscar el cedron, devuelve una lista de venues con el cedron","El Cedrón",cedron.getName());
    }

    @Test
    public void findByExplore_returnVenues() {
        ArrayList<Venue> encontrados = new ArrayList<Venue>();
        try {
            encontrados = this.pc.getPlaces("Buenos Aires", "-34.653496", "-58.505093", "100", 3, null, null, null);
        } catch (VenuesNotFoundException | MissingParametersException e) {}
        assertTrue("Prueba de encontrar resultados",encontrados.size() >= 1);
    }
}