# EarthQuakes -- Android App

Requirements Analysis

1. The app displays the earthquakes that occurred during in the last day.  For each earthquake, it  displays basic info:
      	- Date occurred => converted to the default timezone
       	- Place occurred	
       	- Magnitude
        
2. If the user clicks on an earthquake, display additional information for that earthquake, along with the basic info.  At minimum, include
	- Date occurred => converted to the default timezone
	- Place occurred
	- Magnitude
	- Latitude/longitude
	- Whether a tsunami potential exists.
  
3. Add some way for the user to filter the visible earthquakes by setting a minimum magnitude to display.

4. The app is using GeoJSON format (http://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php). Here’s an excerpt from the JSON structure returned with the relevant elements identified:

```json
"features":[  
{
  "type":"Feature",
  "properties":{  

  "mag":2.51,
  "place":"2km NE of Granite Falls, Washington",
  "time":1497570149880,  

  "tsunami":0, 

},
"geometry":{  
  "type":"Point",
  "coordinates":[  
  -121.940834,
  48.097168,

  ]
},
```

A filter feature is requested for setting the minimum magnitude.  Also, the timeframe is “last day” implying 24 hours back from current time. 
Here’s an example how to incorporate the filter and time into the query:

https://earthquake.usgs.gov/fdsnws/event/1/query?&format=geojson&minmagnitude=3.0&starttime=2017-06-18T14:55:08-07:00&orderby=time

where the starttime string is created dynamically from current time.

## Design Decisions and Assumptions

- The Minimum SDK is set to API 19 (KitKat, Android 4.4), which lets the app run on approximately 73.9% of the devices that are active on the Google Play Store.
- There is no specific layout design and implementation for landscape mode and tablets.
- Default timezone and locale are obtained from the device running the app.
- The number of entries queried from the web service is limited to 20000, which is the default value. The amount of data even in the maximum case is negligible.
- The app is built against version 1.5.8 (https://earthquake.usgs.gov/fdsnws/event/1/version)  of the API. There’s no version check implemented in current app.

## Design and Implementation

The app is divided into three packages: model, webservice and ui. The dependency chain is from ui->webservice, ui-> model and webservice->model. Therefore, model resides in the middle forming the domain (ref. Domain Driven Design by Eric Evans) and main vocabulary.  We’ll go through each package next.

### com.amyllykoski.earthquakes.model package

Model consist of value objects, which mostly hold (typed) data received from the webservice. Their dependencies on external world objects are practically eliminated excluding the Time class, which leverages text and util packages for calendar functions. Model objects are consumed by the UI in order to display information about the recent earthquakes. The aggregate root (a holder of the other value objects) is the EarthQuakeRecord class, which becomes the main item in the payload sent to UI to be presented. Model

### com.amyllykoski.earthquakes.webservice package

The webservice package maps a bounded context from the USGS Restful interface domain to the above mentioned model. It executes queries over HTTPS in order to obtain a list of relevant earthquake records. Then it maps those records to the domain supported by the com.amyllykoski.earthquakes.model domain. The webservice package leverages third party libraries, namely Retrofit and Gson for communicating with the Restful service and parsing the JSON delivered as a payload.

### com.amyllykoski.earthquakes.ui package

The ui package consists of activities for both the list (based on RecyclerView) and details. The latter view also leverages a specific Fragment in order to offer support for different layout in case tablet support is required in the future. Applying the Model-View-Presenter pattern, the Model is introduced already above, View lives mostly in the list provided by the RecyclerView and EarthQuakeRecordDetailFragment. The Presenter functionality can be located in the EarthQuakeRecordListAdapter, which manages the communication to the webservice, updates the list accordingly and launches the detailed view when user clicks a list item. 

### Libraries Used

- GSON for JSON parsing.
- Retrofit for REST/HTTP communication.
- OkHttp for webservice testing, mostly using the MockWebServer.
- Joda to provide support for creating time string for the webservice query.

## Testing Approach

Following the logic and dependencies laid out above, model objects (value objects)  are unit tested with JUnit. Since majority of the model objects are simple POJOs, only Time class is tested as it contains one converter function. 

The webservice package is tested using Android Instrumentation because there are dependencies to Android context. The tests focus on verifying the main entry point of the webservice package, namely the EarthQuakeAPIClient, which is used by the EarthQuakeRecordListAdapter. The main interface is the execute() method call, which is  offered in two overloaded forms: with baseURL and without baseURL. The latter one is used by the app. The first one is used in testing so, that the baseURL can point to the one provided by the MockWebServer.  The MockWebServer allows for setting the response explicitly and therefore we can verify the HTTP GET roundtrip works through asynchronous Retrofit interface with a set of predefined JSON responses.

And finally, the ui package testing relies on Espresso. The test script verifies correct view is shown when the app starts and that the magnitude setting dialog is displayed correctly. One could call this type of testing either end-to-end or just UI testing.

The number of test cases in current version of the app is minimal: it does not suffice from test coverage point of view. The objective here is merely to demonstrate the testing approach with a selected set of tests.

## Technology Roadmap

Given the time constraints when developing this app, several possible (technology) improvements were excluded. For example, making the testing more fluent Dagger and Mockito can be helpful in terms of offering mocking support and dependency injections of selected parts of the services. Also, using RxJava with the Retrofit would streamline the testing and usage of the API when the main handle to the network communication is an Observable. Instead of employing Material Design in its fullest, default styling is used in the UI. As for the bindings from UI elements to the corresponding code, either Butterknife or the new Android Data Binding feature would eliminate some boilerplate code.


