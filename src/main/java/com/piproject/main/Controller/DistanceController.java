package com.piproject.main.Controller;

import com.pi4j.io.gpio.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DistanceController {

    private static GpioPinDigitalOutput sensorTriggerPin ;
    private static GpioPinDigitalInput sensorEchoPin ;
    private double Distance;
    private float d;

    final static GpioController gpio = GpioFactory.getInstance();

    @RequestMapping(value = "/graph",method = RequestMethod.GET)
    public String graph(){

        return "graph";
    }

    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public @ResponseBody
    String run() throws JSONException {

        JSONObject distance = new JSONObject();
        JSONArray result = new JSONArray();
        JSONObject jsonObj = new JSONObject();


        sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00); // Trigger pin as OUTPUT
        sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,PinPullResistance.PULL_DOWN); // Echo pin as INPUT

                try {Thread.sleep(2000);
                sensorTriggerPin.high();
                Thread.sleep((long) 0.01);// Delay for 10 microseconds
                sensorTriggerPin.low();

                while(sensorEchoPin.isLow()){ //Wait until the ECHO pin gets HIGH

                }
                long startTime= System.nanoTime(); // Store the current time to calculate ECHO pin HIGH time.
                while(sensorEchoPin.isHigh()){ //Wait until the ECHO pin gets LOW

                }
                long endTime= System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.

                    sensorTriggerPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
                    sensorEchoPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
                    gpio.shutdown();
                    gpio.unprovisionPin(sensorTriggerPin);
                    gpio.unprovisionPin(sensorEchoPin);

                    Distance =((((endTime-startTime)/1e3)/2) / 29.1); //Printing out the distance in cm

                    d = (float) Distance;

                   float[] data = {d};

                    distance.put("distance", data);
                    result.put(data);

                    jsonObj.put("measuredData", result);


                }catch (InterruptedException e) {
                    e.printStackTrace();
        }

        System.out.println("Sending this data to view (graph.jsp): " + jsonObj.toString());

        return jsonObj.toString();
    }
}
