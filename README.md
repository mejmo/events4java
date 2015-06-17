# events4java
Events4java is simple reminder sender for SMTP and Exchange endpoints (with EWS API enabled). For SMTP it uses ICS files in enclosed as attachment. Two possible ways of dispatching are available:

Requirements:
- JDK 1.6+
- SMTP or EWS API enabled Exchange server
- for libraries requirements see pom.xml

## Settings

events4java can be loaded with two different ways. Loading from properties file located within class path or with
Properties object.

### Load settings from file

Library searches for file events4java.properties within class path. Have a look at events4java.properties sample file how
it should look like.

### Loading from Properties object

```java
Properties properties = new Properties();
properties.put("ExchangeURL", "http://gogo");
E4JConfiguration.setProperties(properties);
```
## Create event object

You can create an all day event or time specific event:

```java
EventMailInfo mailInfo = new EventMailInfo("test@test", "SUBJECT", "EMAILBODY", "FILENAME.ics");
EventDateInfo dateInfo = new AllDayEvent("Christmas", 24, 12, 2015);
```

Time specific event:

Start time: 2015/12/24 10:00
End time: 2015/12/24 10:30

```java
EventMailInfo mailInfo = new EventMailInfo("test@test", "SUBJECT", "EMAILBODY", "FILENAME.ics");
EventDateInfo dateInfo = new TimeSpecificEvent("Christmas", new DateTime(2015, 12, 24, 10, 0), new DateTime(2015, 12, 24, 10, 30));
```

## How to send ICS file with SMTP

When your EventMailInfo and EventDateInfo are created, you can use synchronous or asynchronous way to send ICS file to the user.

### Synchronous call

```java
EventMailInfo mailInfo = new EventMailInfo("test@test", "SUBJECT", "EMAILBODY", "FILENAME.ics");
EventDateInfo dateInfo = new AllDayEvent("Christmas", 24, 12, 2015);
SMTPEventData eventData = new SMTPEventData(mailInfo, dateInfo);
SMTPEventEndpoint endpoint = new SMTPEventEndpoint(eventData);

endpoint.dispatchEvent();
```
### Asynchronous call

```java        
EventMailInfo mailInfo = new EventMailInfo("test@test", "SUBJECT", "EMAILBODY", "FILENAME.ics");
EventDateInfo dateInfo = new AllDayEvent("Christmas", 24, 12, 2015);
SMTPEventData eventData = new SMTPEventData(mailInfo, dateInfo);

AsyncRunner asyncRunner = new AsyncRunner(new SMTPEventEndpoint(eventData), new AsyncHandler() {
        @Override
        public void done(EventEndpoint e) {
            System.out.print("OK!");
        }

        @Override
        public void error(EventEndpoint e, Throwable t) {
            System.out.println(t);
        }
});
asyncRunner.start();
```

# Bug reports, feature requests and contact

If you found any bugs, if you have feature requests or any questions, please, either file an issue at GitHub.

# License

event4java is published under the MIT license.


