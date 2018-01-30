package com.example.g00296814.send_sensor_data;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private SensorManager mSensorManager;
    private LocationManager mLocationManager;

    private Sensor mLight;
    private Sensor mAccelerometer;
    private Sensor mHumidity;
    private Sensor mPressure;
    private Sensor mMagnetometer;
    private Sensor mProximity;
    private Sensor mTemperature;

    private ListView listView;

    private ArrayList<String> sensorList = new ArrayList<>();
    private ArrayAdapter<String> listAdapter ;

    private String[] sensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("hey", ":)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        listView = (ListView) findViewById(R.id.mainListView);

        sensors = new String[] {
                "Light\t", "Accelerometer\t", "Pressure\t", "Temperature\t",
                "Proximity\t", "Humidity\t", "Magnetic Field\t", "GEO Location\t"

        };

        sensorList.addAll(Arrays.asList(sensors));

        listAdapter = new ArrayAdapter<String>(this, R.layout.row, sensorList);

        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mHumidity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        sensorList.clear();

        switch(event.sensor.getType()) {
            case Sensor.TYPE_LIGHT:
                sensors[0] = "Light (lux): " + event.values[0];
                break;
            case Sensor.TYPE_ACCELEROMETER:
                sensors[1] = "Accelerometer: x=" + String.format("%.2f", event.values[0]) + ", y=" + String.format("%.2f", event.values[1]) + ", z=" + String.format("%.2f", event.values[2]);
                break;
            case Sensor.TYPE_PRESSURE:
                sensors[2] = "Pressure (hPa): " + event.values[0];
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                sensors[3] = "Temperature (°C): " + event.values[0];
                break;
            case Sensor.TYPE_PROXIMITY:
                sensors[4] = "Proximity (cm): " + event.values[0];
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                sensors[5] = "Humidity (%): " + event.values[0];
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                sensors[6] = "Magnetic Field (uT): x=" + String.format("%.2f", event.values[0]) + ", y=" + String.format("%.2f", event.values[1]) + ", z=" + String.format("%.2f", event.values[2]);
                break;
        }
        
        sensorList.addAll(Arrays.asList(sensors));
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLocationChanged(Location location) {
        sensorList.clear();

        sensors[7] = "GEO Location: Lat=" + location.getLatitude() + ", Long=" + location.getLongitude();

        sensorList.addAll(Arrays.asList(sensors));
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
