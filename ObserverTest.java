import java.util.ArrayList;
import java.util.List;

interface Display {
    void update(float temperature, float humidity);
}

interface Subject {
    void registerObserver(Display d);
    void removeObserver(Display d);
    void notifyObservers();
}

class WeatherStation implements Subject {
    private List<Display> observers = new ArrayList<>();
    private float temperature;
    private float humidity;

    @Override
    public void registerObserver(Display d) { observers.add(d); }

    @Override
    public void removeObserver(Display d) { observers.remove(d); }

    @Override
    public void notifyObservers() {
        for (Display display : observers) {
            display.update(temperature, humidity);
        }
    }

    public void setMeasurements(float temp, float hum) {
        this.temperature = temp;
        this.humidity = hum;
        notifyObservers();
    }
}

class CurrentConditionsDisplay implements Display {
    @Override
    public void update(float temp, float hum) {
        System.out.println("Current Conditions: Temp=" + temp + "C, Humidity=" + hum + "%");
    }
}

class ForecastDisplay implements Display {
    @Override
    public void update(float temp, float hum) {
        if (temp > 30) {
            System.out.println("Forecast: Next day will be HOT.");
        } else {
            System.out.println("Forecast: Next day will be pleasant.");
        }
    }
}

public class ObserverTest {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();
        Display current = new CurrentConditionsDisplay();
        Display forecast = new ForecastDisplay();

        station.registerObserver(current);
        station.registerObserver(forecast);

        System.out.println("--- First update ---");
        station.setMeasurements(32.5f, 65.0f);

        System.out.println("\n--- Second update ---");
        station.setMeasurements(25.0f, 70.0f);

        station.removeObserver(forecast);
        System.out.println("\n--- Third update (Forecast removed) ---");
        station.setMeasurements(40.0f, 50.0f);
    }
}
