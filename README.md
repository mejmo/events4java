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

Library searches for file events4java.properties within class path. This is the sample, how it should look like:

```
##
## Events4java Configuration file
##

## Configuration for SMTP Endpoint
SMTPUsername=
SMTPPassword=
SMTPPort=25

#Email address from which the mail will be sent, if SMTP server allows it
SMTPFromAddress=

SMTPHostname=

# SSL, TLS, none
SMTPEncryption=none

# true/false
SMTPDebugEnabled=

# true/false
SMTPAuthenticationNeeded=

## Configuration for Exchange Endpoint with EWS (Exchange Web Services) enabled
ExchangeUsername=
ExchangePassword=
# see ExchangeVersion enumeration of EWS API on Microsoft (Exchange2007_SP1, Exchange2010, Exchange2010_SP1, ...)
ExchangeVersion=
ExchangeCalendarName=
# EWS API URL
ExchangeURL=
```

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


