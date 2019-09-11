***Reverse Proxy***

Your task is to implement reverse proxy for proxying and balancing traffic
between applications.

Check first here : https://en.wikipedia.org/wiki/Reverse_proxy

Your application should listen hostname and port provided from configuration and 
proxy any inbound traffic into proxied endpoints and return responses.

At the beginning your should use existed ``ua.hillel.java.elementary1.proxy.AbstractConfigurationBuilder``
extend and build your own instance of ``ua.hillel.java.elementary1.proxy.Configuration`` class.

Then you should implement ``ua.hillel.java.elementary1.proxy.AbstractReverseProxy`` class 
and pass provided configuration.

Example:

```java
  class Application {
    
     public static void main(String[] args){
        Configuration configuration = new ServerBuilder()
            .withServer()
            .setHostname("localhost")
            .setPort(8080)
            .withEndpoint()
               .setHostname("localhost")
               .setPort(8081)
               .setWeight(30)
            .and()
            .withEndpoint()
               .setHostname("localhost")
               .setPort(8082)
               .setWeight(70)
            .build();
        ReverseProxy proxy = new ReverseProxy(configuration);
        proxy.start();
     }
 }
``` 

This code will start reverse proxy listening of the port 8080 and
forward 30% of traffic to "localhost:8081" endpoint and 70% to "localhost:8082".
 
Additional tasks:

1. In configuration builder implement 
``withConfigurationFile(String configurationFile)`` in JSON format (add dependency is added).
Try to use same file structure as in ``ua.hillel.java.elementary1.proxy.AbstractConfigurationBuilder``

2. In ``ua.hillel.java.elementary1.proxy.AbstractConfigurationBuilder.EndpointBuilder`` implement 
method ``ua.hillel.java.elementary1.proxy.AbstractConfigurationBuilder.EndpointBuilder.setHealthCheck``
that will check periodically health of the endpoint. It is up to you to decide how to check liveness.
Use method ``ua.hillel.java.elementary1.proxy.AbstractConfigurationBuilder.EndpointBuilder.setHealthCheckIntervalMillis``
to configure rate of checks.

3. Use methods ``ua.hillel.java.elementary1.proxy.AbstractConfigurationBuilder.ServerBuilder.setRetryCount``
to retry calls in case of failures. Default value is -1 means no retry. Use ``setRetryMillis`` to set the delay 
between calls. Use ``setBackOffMultiplier`` to inc / dec retry count. Default value is 1. 
```
long currentRetryMs = backOffMultiplier * prevRetryMs.
```
4.(*) Use methods ``setRequests`` and ``setPeriodMillis`` to limit number of requests in given time.
It is up to you to decide way of implementation. I suggest : TokenBucket, SlidingTokenBucket.

Your implementation should be done in separate module. Use provided API.
Good luck!
