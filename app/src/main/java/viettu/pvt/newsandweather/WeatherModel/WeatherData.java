package viettu.pvt.newsandweather.WeatherModel;

public class WeatherData {
    public String Temp_Min;
    public String Temp_Max;
    public String Humidity;
    public String Pressure;
    public String Wind;
    public String DateTime;
    public String Description_status;
    private String Icon_status;

    public WeatherData(String temp_Min, String temp_Max, String humidity, String pressure,
                       String wind, String dateTime, String description_status,
                       String icon_status) {
        Temp_Min = temp_Min;
        Temp_Max = temp_Max;
        Humidity = humidity;
        Pressure = pressure;
        Wind = wind;
        DateTime = dateTime;
        Description_status = description_status;
        Icon_status = icon_status;
    }


    public String getIcon_status() {
        return Icon_status;
    }
}
