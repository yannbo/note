## How to add telemetry in W project. 
***Key steps***
0. Pre-condition: invoke the dependency `telemetryclient.xx.jar`

1. Define  class extend the class `TelemetryLog`
```
public class XxxxLog extends TelemetryLog {
```

2. Build the XxxxLog instance and add related attribute.
```
XxxxLog telemetryLog = new XxxxLog(); //Can used different method to construct the instance. this is just a example.
...
telemetryLog.setFeatureName("test feature");
...
```

3. Log the telemetry json string.
```
private static final TelemetryLogger telemetryLogger = TelemetryLoggerFactory.getLogger();
...
telemetryLogger.info(telemetryLog); 
...
```