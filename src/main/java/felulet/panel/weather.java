/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package felulet.panel;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.FIODaily;
import com.github.dvdme.ForecastIOLib.ForecastIO;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

/**
 *
 * @author norbi
 */
public class weather {

    String summary, precipprob, precipint, icon, cloudcover, windbearing, apparenttemp, pressure, dewpoint, ozone, preciptype, temperature, temperatureMin, temperatureMax, humidity, time, windspeed;
    hely geo = new hely();

    class hely {

        public String formattedaddres = "", lat = "", lng = "";
    }

    public void get_hely(String hely, String api) {
        try {
            GeoApiContext context = new GeoApiContext().setApiKey(api);
            context.setQueryRateLimit(8);//10 a free
            GeocodingResult[] results = GeocodingApi.geocode(context, hely).await();//blocking mode
            for (GeocodingResult result : results) {
                System.out.println(result.formattedAddress);
                System.out.println("lat: " + result.geometry.location.lat + " lng:" + result.geometry.location.lng);
                geo.formattedaddres = result.formattedAddress;
                geo.lat = result.geometry.location.lat + "";
                geo.lng = result.geometry.location.lng + "";
            }
        } catch (Exception e) {
        }
    }
    String summary_n = "", icon_n = "", temperature_n = "";
    FIODaily daily;

    public weather(String location, String weather_api_key, String google_api_key) {

        get_hely(location, google_api_key);

        ForecastIO fio = new ForecastIO(weather_api_key);
        fio.setUnits(ForecastIO.UNITS_SI);             //sets the units as SI - optional
        fio.setExcludeURL("hourly,minutely");             //excluded the minutely and hourly reports from the reply
        fio.getForecast(geo.lat, geo.lng);   //sets the latitude and longitude - not optional
        FIOCurrently currently = new FIOCurrently(fio);
        daily = new FIODaily(fio);

        temperatureMin = currently.get().temperatureMin() + " C";
        temperatureMax = currently.get().temperatureMax() + " C";
        summary = currently.get().summary().replace("\"", "") + "";
        precipprob = currently.get().precipProbability() + "";
        precipint = currently.get().precipIntensity() + "";
        icon = currently.get().icon() + "";
        cloudcover = currently.get().cloudCover() + "";
        apparenttemp = currently.get().apparentTemperature() + " C";
        pressure = currently.get().pressure() + "";
        dewpoint = currently.get().dewPoint() + "";
        ozone = currently.get().ozone() + "";
        temperature = currently.get().temperature() + " C";
        humidity = currently.get().humidity() + "";
        time = currently.get().time() + "";

        windspeed = currently.get().windSpeed() + "";
        windbearing = currently.get().windBearing() + "";
    }

    public String get_icon(int nap) {
        return daily.getDay(nap).icon();
    }

    public String get_summary(int nap) {
        return daily.getDay(nap).icon().replace("\"", "").replace("-", " ").replace("day", "").trim();
    }

    public String get_temp_max(int nap) {
        return daily.getDay(nap).temperatureMax() + " C";
    }

    public String get_temp_min(int nap) {
        return daily.getDay(nap).temperatureMin() + " C";
    }

}
