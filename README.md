# FileParser
#### Application requirements

This application will parse multiple files in different locations looking for specific information.
Once the information is found, the application will present the output in a table format.

The location of the file will consist of:
serverName and applicationName.

Applications will be listed in this folder:
`\\ipAddress\c$\..\..\..\applications\`

Activity log files
`\\ipAddress\c$\..\..\..\applications\AppName\logs\Log`

Activity_log2016-07- 08.txt

`\\ipAddress\c$\..\..\..\applications\AppName\logs\Log\activity_log2016-07- 08.txt`

**Composite Filter**

Once the servers and the applications are chosen, the user will presented by drop down menu to choose from different options.
These options will be also populated in searchCriteria.properties.
Users should also have the ability to enter a custom name.

The output should be presented in user friendly format ordered by application name.


Example of activity_log2016-07- 08.txt

>ip-address.1467952640747.548428....,07/08/2016 00:37:34.655,,start,source,DB_Lookup
ip-address.1467952640747.548428....,07/08/2016 00:37:34.655,,start,ani,NA
ip-address.1467952640747.548428....,07/08/2016 00:37:34.655,,start,areacode,NA
ip-address.1467952640747.548428....,07/08/2016 00:37:34.655,,start,exchange,NA
ip-address.1467952640747.548428....,07/08/2016 00:37:34.655,,start,dnis,NA
ip-address.1467952640747.548428....,07/08/2016 00:37:34.655,,start,uui,408123-4567
ip-address.1467952640747.548428....,07/08/2016 00:37:34.655,,start,iidigits,NA
ip-address.1467952640747.548428....,07/08/2016 00:37:34.655,,start,parameter,ICMInfoKeys=123456
ip-address.1467952640747.548428....,07/08/2016 00:37:34.655,Start SAP,enter,
ip-address.1467952640747.548428....,07/08/2016 00:37:35.077,Start SAP,exit,done,
ip-address.1467952640747.548428....,07/08/2016 00:37:35.077,chk_search,exit,BP by BPID
ip-address.1467952640747.548428....,07/08/2016 00:37:35.077,BPID_chk_path,enter,
ip-address.1467952640747.548428....,07/08/2016 00:37:35.077,BPID_chk_path,exit,rss
ip-address.1467952640747.548428....,07/08/2016 00:37:35.077,BPID_rss_fetchAudio,enter,
ip-address.1467952640747.548428....,07/08/2016 00:37:35.077,BPID_rss_fetchAudio,interaction,audio_group,initial_audio_group
ip-address.1467952640747.548428....,07/08/2016 00:37:35.108,BPID_rss_fetchAudio,exit,done
ip-address.1467952640747.548428....,07/08/2016 00:37:35.608,wsBusinessPartner_14_BPID,custom,INFO,Number of SPIDs found=0
ip-address.1467952640747.548428....,07/08/2016 00:37:35.608,wsBusinessPartner_14_BPID,custom,INFO,No Data
ip-address.1467952640747.548428....,07/08/2016 00:37:35.608,wsBusinessPartner_14_BPID,exit,No Data
ip-address.1467952640747.548428....,07/08/2016 00:37:35.608,return_BP_NoData,enter,
ip-address.1467952640747.548428....,07/08/2016 00:37:42.171,return_BP_NoData,exit,
ip-address.1467952640747.548428....,07/08/2016 00:37:42.171,,end,how,app_session_complete
ip-address.1467952640747.548428....,07/08/2016 00:37:42.171,,end,result,normal
ip-address.1467952640747.548428....,07/08/2016 00:37:42.171,,end,duration,8
ip-address.1467952640747.548428....,07/08/2016 00:38:05.438,,start,source,DB_Lookup
ip-address.1467952640747.548428....,07/08/2016 00:38:05.438,,start,ani,NA
ip-address.1467952640747.548428....,07/08/2016 00:38:05.438,,start,areacode,NA
ip-address.1467952640747.548428....,07/08/2016 00:38:05.438,,start,exchange,NA
ip-address.1467952640747.548428....,07/08/2016 00:38:05.438,,start,dnis,NA
ip-address.1467952640747.548428....,07/08/2016 00:38:05.438,,start,uui,NA
ip-address.1467952640747.548428....,07/08/2016 00:38:05.438,,start,iidigi