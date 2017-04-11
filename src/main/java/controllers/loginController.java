package controllers;

import engine.Preludio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeff on 4/1/17.
 */
public class loginController {

    @FXML
    private TextField usernameBox;

    @FXML
    private PasswordField passwordBox;

    @FXML
    void initialize() {

    }

    @FXML
    void submit() {
        if (!usernameBox.getText().isEmpty() && !passwordBox.getText().isEmpty()) {
            try {
                URL url = new URL("http://ec2-54-214-225-63.us-west-2.compute.amazonaws.com/extern_login.php");
                Map<String, Object> params = new HashMap<>();
                params.put("username", usernameBox.getText());
                params.put("password", passwordBox.getText());

                StringBuilder postData = new StringBuilder();
                // POST as urlencoded is basically key-value pairs, as with GET
                // This creates key=value&key=value&... pairs
                for (Map.Entry<String,Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }

                byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                // Tell server that this is POST and in which format is the data
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postDataBytes);

                // This gets the output from your server
                Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                String response = "";

                for (int c; (c = in.read()) >= 0;)
                    response += (char) c;

                if (response.equals("Valid")) {
                    Preludio.getInstance().setUserName(usernameBox.getText());
                    Preludio.getInstance().setNewScene("/view/fxml/mainMenu.fxml");
                } else if (response.equals("Invalid")){

                }
            } catch (Exception mue) {
                mue.printStackTrace();
            }
        }
    }
}
