package viettu.pvt.newsandweather;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import viettu.pvt.newsandweather.WeatherAdapter.WeatherPageAdapter;
import viettu.pvt.newsandweather.WeatherModel.WeatherData;

import static java.util.Objects.requireNonNull;


public class weatherFragment extends Fragment {
    private static final String JSON_OBJECT_KEY_NAME = "name";
    private static final String JSON_OBJECT_KEY_SYS = "sys";
    private static final String JSON_OBJECT_KEY_COUNTRY = "country";
    private static final String JSON_OBJECT_KEY_MAIN = "main";
    private static final String JSON_OBJECT_KEY_TEMP = "temp";
    private static final String JSON_OBJECT_KEY_WEATHER = "weather";
    private static final String JSON_OBJECT_KEY_DESCRIPTION = "description";
    private static final String JSON_OBJECT_KEY_ICON = "icon";
    private static final String URL_CONVERT_ICON = "http://openweathermap.org/img/wn/";
    private static final String URL_CONVERT_ICON_TYPE = "@2x.png";
    private static final String EXTRA_CITY_NAME = "cityName";
    private static final String QUERY_HINT = "Search City Weather...";
    public static final String CNT = "&cnt=40";
    public static final String API_KEY_WEATHER = "&appid=dc1477b44b5feb9b97590dfa57b10922";
    public static final String UNITS = "&units=metric";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    public static final String JSON_OBJECT_KEY_CITY = "city";

    public static final String JSON_OBJECT_KEY_POPULATION = "population";
    public static final String JSON_OBJECT_KEY_COORD = "coord";
    public static final String JSON_OBJECT_KEY_LAT = "lat";
    public static final String JSON_OBJECT_KEY_LON = "lon";

    public static final String JSON_OBJECT_KEY_LIST = "list";
    public static final String JSON_OBJECT_KEY_TEMP_MAX = "temp_max";
    public static final String JSON_OBJECT_KEY_TEMP_MIN = "temp_min";
    public static final String JSON_OBJECT_KEY_HUMIDITY = "humidity";
    public static final String JSON_OBJECT_KEY_PRESSURE = "pressure";
    public static final String JSON_OBJECT_KEY_DATE_TIME = "dt";
    public static final String JSON_OBJECT_KEY_WIND = "wind";
    public static final String JSON_OBJECT_KEY_SPEED = "speed";
    public static final String DATE_TIME_FORMAT_CURRENT_DAY = "EEEE MM/dd/yyyy";
    public static final String DATE_TIME_FORMAT_CURRENT_HOURS = "HH";
    public static final String EXTRA_QUERY_NAME = "cityName";
    private WeatherPageAdapter weatherPageAdapter;
    public ArrayList<WeatherData> weatherDataList;
    RecyclerView rvForecast;

    private String sendCityName;
    private TextView tvCity, tvTemp, tvStatus;
    private static final String DEFAULT_CITY_NAME= "Hanoi";
    private ImageView iconWeather;



    public weatherFragment() {
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weather, container, false);

        tvCity = rootView.findViewById(R.id.tv_City);
        tvStatus= rootView.findViewById(R.id.tv_status);
        rvForecast = rootView.findViewById(R.id.weatherForcast_recylceview);
        rvForecast.setHasFixedSize(true);
        rvForecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tvTemp = rootView.findViewById(R.id.tv_Temp);
        iconWeather = rootView.findViewById(R.id.iconStatusWeather);
        loadCurrentWeatherData(DEFAULT_CITY_NAME);

        loadWeatherForecastData(DEFAULT_CITY_NAME);
        loadRcycle();

        return rootView;
    }

     private void loadRcycle(){
         weatherDataList = new ArrayList<>();

         weatherPageAdapter = new WeatherPageAdapter(getContext(), weatherDataList);
         rvForecast.setAdapter(weatherPageAdapter);

     }

    private void  loadCurrentWeatherData(final String cityName){
        String queryUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=dc1477b44b5feb9b97590dfa57b10922";
        RequestQueue requestQueue = Volley.newRequestQueue(requireNonNull(getActivity()));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryUrl,
                new com.android.volley.Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Locale locale = new Locale("",jsonObject
                                    .getJSONObject(JSON_OBJECT_KEY_SYS)
                                    .getString(JSON_OBJECT_KEY_COUNTRY));
                            tvCity.setText(jsonObject
                                    .getString(JSON_OBJECT_KEY_NAME)+", "+locale.getDisplayCountry());
                            tvTemp.setText(jsonObject
                                    .getJSONObject(JSON_OBJECT_KEY_MAIN)
                                    .getString(JSON_OBJECT_KEY_TEMP)+"°C");
                            JSONObject jsonObjectWeather = jsonObject
                                    .getJSONArray(JSON_OBJECT_KEY_WEATHER)
                                    .getJSONObject(0);
                            tvStatus.setText(jsonObjectWeather
                                    .getString(JSON_OBJECT_KEY_DESCRIPTION));
                            Picasso.get()
                                    .load(URL_CONVERT_ICON
                                            +jsonObjectWeather.getString(JSON_OBJECT_KEY_ICON)+ URL_CONVERT_ICON_TYPE)
                                    .into(iconWeather);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error No Data default"+cityName, Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(stringRequest);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_weather,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weather_seach:
                SearchManager searchManager = (SearchManager) requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
                final SearchView searchView = (SearchView) item.getActionView();
                assert searchManager != null;
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setQueryHint(QUERY_HINT);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if (s.length() > 2) {
                            loadCurrentWeatherData(s);
                            sendCityName = s;
                            loadWeatherForecastData(sendCityName);
                            loadRcycle();
                        } else {
                            sendCityName = "Hanoi";
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                item.getIcon().setVisible(false, false);

                return true;
            case R.id.action_setting_weather:

                loadWeatherForecastData(sendCityName);
                loadRcycle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void loadWeatherForecastData(final String cityName) {
        String queryUrl = BASE_URL + cityName + UNITS + CNT + API_KEY_WEATHER;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryUrl,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject(JSON_OBJECT_KEY_CITY);
                            Locale locale = new Locale("", jsonObjectCity.getString(JSON_OBJECT_KEY_COUNTRY));

                            JSONArray jsonArrayList = jsonObject.getJSONArray(JSON_OBJECT_KEY_LIST);
                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                Date date_Formatter = new Date(Long.valueOf(jsonObjectList.getString(JSON_OBJECT_KEY_DATE_TIME)) * 1000L);
                                @SuppressLint("SimpleDateFormat") String detailTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT_CURRENT_DAY).format(date_Formatter) + "h " +
                                        new SimpleDateFormat(DATE_TIME_FORMAT_CURRENT_HOURS).format(date_Formatter);

                                JSONObject jsonObjectMain = jsonObjectList.getJSONObject(JSON_OBJECT_KEY_MAIN);
                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray(JSON_OBJECT_KEY_WEATHER);
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String maxTemp = jsonObjectMain.getString(JSON_OBJECT_KEY_TEMP_MAX) + "";
                                String minTemp = jsonObjectMain.getString(JSON_OBJECT_KEY_TEMP_MIN) + "";
                                String humidity = jsonObjectMain.getString(JSON_OBJECT_KEY_HUMIDITY) + "";
                                String pressure = jsonObjectMain.getString(JSON_OBJECT_KEY_PRESSURE) + "";
                                String status = jsonObjectWeather.getString(JSON_OBJECT_KEY_DESCRIPTION);
                                String icon = jsonObjectWeather.getString(JSON_OBJECT_KEY_ICON);
                                String wind = jsonObjectList.getJSONObject(JSON_OBJECT_KEY_WIND).getString(JSON_OBJECT_KEY_SPEED);

                                weatherDataList.add(new WeatherData(minTemp, maxTemp, humidity, pressure, wind, detailTimeFormat
                                        , status, icon));

                            }
                            weatherPageAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error No Data"+error.getMessage()+"  cy"+cityName, Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(stringRequest);

    }

}