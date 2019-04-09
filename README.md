# jmx-json-javaagent

This agent provides a read-only view of the JMX metrics for a running java process in JSON format. \
Sample run below:
``` shell
java -cp jmx-json-javaagent.jar \
     -javaagent:jmx-json-javaagent.jar=8185  \
     org.npopa.samples.JettyServer 8184
```
 
The generated JSON at [http://localhost:8185/jmx](http://localhost:8185/jmx) looks similar to this:

``` shell
{
  "beans" : [ {
    "name" : <bean name>,
    <attribute> : <value>
    ...
    },{
    "name" : <bean name>,
    <attribute> : <value>
    ...
    }
  ]
}    

```


eg.
``` shell

{
  "beans" : [ {
    "name" : "java.lang:type=Memory",
    "modelerType" : "sun.management.MemoryImpl",
    "Verbose" : false,
    "ObjectPendingFinalizationCount" : 0,
    "HeapMemoryUsage" : {
      "committed" : 257425408,
      "init" : 268435456,
      "max" : 3817865216,
      "used" : 68399168
    },
    "NonHeapMemoryUsage" : {
      "committed" : 15466496,
      "init" : 2555904,
      "max" : -1,
      "used" : 14738688
    },
    "ObjectName" : "java.lang:type=Memory"
  }, {
    "name" : "java.lang:type=Threading",
    "modelerType" : "sun.management.ThreadImpl",
    "ThreadAllocatedMemorySupported" : true,
    "ThreadAllocatedMemoryEnabled" : true,
    "ThreadCount" : 13,
    "PeakThreadCount" : 14,
    "TotalStartedThreadCount" : 571,
    "DaemonThreadCount" : 3,
    "AllThreadIds" : [ 577, 576, 571, 570, 567, 18, 15, 14, 7, 5, 4, 3, 2 ],
    "ThreadContentionMonitoringSupported" : true,
    "ThreadContentionMonitoringEnabled" : false,
    "CurrentThreadCpuTime" : 160482000,
    "CurrentThreadUserTime" : 147123000,
    "ThreadCpuTimeSupported" : true,
    "CurrentThreadCpuTimeSupported" : true,
    "ThreadCpuTimeEnabled" : true,
    "ObjectMonitorUsageSupported" : true,
    "SynchronizerUsageSupported" : true,
    "ObjectName" : "java.lang:type=Threading"
  }  
 ...
 ]
}
``` 

* The optional `qry` parameter may be used to query only a subset of the JMX Beans. \
For example [http://127.0.0.1:8185/jmx?qry=java.lang:*](http://127.0.0.1:8185/jmx?qry=java.lang:*) will return all `java.lang` metrics exposed through JMX.

* The optional `get` parameter is used to query an specific attribute of a JMX bean. \
For example [http://127.0.0.1:8185/jmx?get=java.lang:type=Threading::ThreadCount](http://127.0.0.1:8185/jmx?get=java.lang:type=Threading::ThreadCount) will return the `ThreadCount` of the `java.lang:type=Threading` mxbean.






