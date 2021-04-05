package viettu.pvt.newsandweather.WeatherAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import viettu.pvt.newsandweather.R;
import viettu.pvt.newsandweather.WeatherModel.WeatherData;

public class WeatherPageAdapter extends RecyclerView.Adapter<WeatherPageAdapter.WeatherViewHolder> {
    private Context context;
    private ArrayList<WeatherData> weatherDataList;

    public WeatherPageAdapter(Context context, ArrayList<WeatherData> weatherDataList) {
        this.context = context;
        this.weatherDataList = weatherDataList;
    }

    @NonNull
    @Override
    public WeatherPageAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_weather,
                parent, false);

        return new WeatherViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WeatherPageAdapter.WeatherViewHolder holder, int position) {
        Picasso.get().load("http://openweathermap.org/img/wn/" + weatherDataList.get(position)
                .getIcon_status() +
                "@2x.png").into(holder.iconWeather);
        holder.txt_detailStatus.setText(new StringBuffer(weatherDataList.get(position).Description_status));
        holder.txt_detailTime.setText(weatherDataList.get(position).DateTime);
        holder.txt_detailTemp.setText(weatherDataList.get(position).Temp_Min+"°C - "+weatherDataList.get(position).Temp_Max+"°C");
        holder.txt_detailWind.setText(weatherDataList.get(position).Wind+" m/s");
        holder.txt_detailHumidity.setText(weatherDataList.get(position).Humidity+"%");
        holder.txt_detailPressure.setText(weatherDataList.get(position).Pressure+" hPa");

    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }
    class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView txt_detailStatus ,txt_detailTemp, txt_detailWind , txt_detailHumidity, txt_detailPressure;
        TextView txt_detailTime;
        ImageView iconWeather;

        WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_detailStatus = itemView.findViewById(R.id.tv_detailStatus);
            txt_detailTime = itemView.findViewById(R.id.tv_timeUpdate);
            txt_detailTemp = itemView.findViewById(R.id.tv_detailTemp);
            txt_detailWind = itemView.findViewById(R.id.tv_detailWind);
            txt_detailHumidity = itemView.findViewById(R.id.tv_detailHumidity);
            txt_detailPressure = itemView.findViewById(R.id.tv_detailPressure);
            iconWeather = itemView.findViewById(R.id.iconWeatherDetail);
        }
    }
}
