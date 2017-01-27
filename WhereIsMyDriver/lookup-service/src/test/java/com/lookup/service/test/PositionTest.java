package com.lookup.service.test;

import org.junit.Test;
import com.lookup.service.core.InvalidPositionException;
import com.lookup.service.core.Position;

public class PositionTest {
	
	@Test(expected = InvalidPositionException.class)
    public void validateShouldFailWhenInvalidLatitudePositionLessThan90Degree() throws Exception {
        Position.validate(-110, 50);
    }

    @Test(expected = InvalidPositionException.class)
    public void validateShouldFailWhenInvalidLatitudePositionMoreThan90Degree() throws Exception {
    	Position.validate(125, 50);
    }

    @Test(expected = InvalidPositionException.class)
    public void validateShouldFailWhenInvalidLongitudePositionLessThanMinus180() throws Exception {
    	Position.validate(10, -185.02);
    }

    @Test(expected = InvalidPositionException.class)
    public void validateShouldFailWhenInvalidLongitudePositionGreaterThan180() throws Exception {
    	Position.validate(20, 197.3);
    }

    @Test
    public void shouldNotFailForValidLatLongs() {
    	Position.validate(89.999, 179.99);
    }

}
