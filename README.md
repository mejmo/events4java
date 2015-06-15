# events4java
Events4java is simple reminder sender for SMTP and Exchange endpoints (with EWS API enabled). For SMTP it uses

## Synchronous

```java
EventMailInfo mailInfo = new EventMailInfo("test@test", "SUBJECT", "EMAILBODY", "FILENAME.ics");
EventDateInfo dateInfo = new AllDayEvent("Christmas", 24, 12, 2015);
SMTPEventData eventData = new SMTPEventData(mailInfo, dateInfo);
SMTPEventEndpoint endpoint = new SMTPEventEndpoint(eventData);

endpoint.dispatchEvent();
```
## Asynchronous 

```java        
EventMailInfo mailInfo = new EventMailInfo("test@test", "SUBJECT", "EMAILBODY", "FILENAME.ics");
EventDateInfo dateInfo = new AllDayEvent("Christmas", 24, 12, 2015);
SMTPEventData eventData = new SMTPEventData(mailInfo, dateInfo);

AsyncRunner asyncRunner = new AsyncRunner(new SMTPEventEndpoint(eventData), new AsyncHandler() {
        @Override
        public void done() {
            System.out.print("OK!");
        }

        @Override
        public void error(Throwable t) {
            System.out.println(t);
        }
});
asyncRunner.start();
```
