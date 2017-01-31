package com.lookup.service.loader;

import com.lookup.service.client.Position;

public class DataLoader {

	public class Request {
		private final double lat;
		private final double lng;
		private final int radius;
		private final int limit;

		public Request(final double lat, final double lng, final int radius,
				final int limit) {

			this.lat = lat;
			this.lng = lng;
			this.radius = radius;
			this.limit = limit;
		}

		public double getLat() {
			return lat;
		}

		public double getLng() {
			return lng;
		}

		public int getRadius() {
			return radius;
		}

		public int getLimit() {
			return limit;
		}
	}
	private final static String DRIVER_UPDATE_URL = "/v1/driver/%s/location";
    private final static String GET_DRIVERS_URL = "/v1/drivers?latitude=%2.6f&longitude=%2.6f&radius=%d&limit=%d";
    //data for lookup request
    private final static Position MANKAR_CHOWK = new Position(18.59, 73.7732);
    private final static Position WAKAD = new Position(18.5989, 73.7653);
    private final static Position BANER = new Position(18.5597, 73.7799);
    static private final int RADIUS = 50;
    static private final int RANGE_LIMIT = 50;
    static final private int LIMIT_GET_DRIVER = 20;

    //drivers position
    private final static Position  KALEWADI_PHATA = new Position(18.6340, 73.8085);
    private final static Position PIMPLE_SAUDAGAR = new Position(18.5935, 73.7929);
    private final static Position DANGE_CHOWK = new Position(18.6138, 73.7664);
    private final static Position AUNDH = new Position(18.562622, 73.808723);
    private final static Position PIMPRI = new Position(18.6298, 73.7997);
    private final static Position HINJAWADI = new Position(18.5971, 73.7188);
    private final static int CONCENTRIC_CIRCLES = 1000;
    private final static int STEP_DISTANCE_DRIVER = 200;
    private final static int STARTING_ID = 1;

    public static void main(String[] args){
    	//com.lookup.service.core
    }
}
