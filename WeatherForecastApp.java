import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class WeatherForecastApp {
    private static JFrame frame;
    private static JTextField locationField;
    private static JTextArea weatherDisplay;
    private static JButton fetchButton;

    private static String apikey = "b95306c54d88b80a2d9239aa7c14e683";

    private static String fetchWeatherData(String city) {
        try {
            URL url = new URL(
                "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apikey + "&units=metric"
            );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                response.append(line);
                line = reader.readLine();
            }
            reader.close();

            // Parse JSON
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());

            JSONObject main = (JSONObject) json.get("main");
            double temp = (double) main.get("temp");
            double feelsLike = (double) main.get("feels_like");

            JSONArray weatherArray = (JSONArray) json.get("weather");
            JSONObject weather = (JSONObject) weatherArray.get(0);
            String description = (String) weather.get("description");

            return "Temperature: " + temp + "°C\n"
                 + "Feels Like: " + feelsLike + "°C\n"
                 + "Weather: " + description;

        } catch (Exception e) {
            return "Failed to fetch weather data. Please check the city name or try again.";
        }
    }

    public static void main(String[] args) {
        frame = new JFrame("Weather Forecast App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        locationField = new JTextField(20);
        fetchButton = new JButton("Fetch Weather");
        weatherDisplay = new JTextArea(10, 30);
        weatherDisplay.setEditable(false);

        frame.add(new JLabel("Enter city name:"));
        frame.add(locationField);
        frame.add(fetchButton);
        frame.add(weatherDisplay);

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = locationField.getText().trim();
                String weatherInfo = fetchWeatherData(city);
                weatherDisplay.setText(weatherInfo);
            }
        });

        frame.setVisible(true);
    }
}
