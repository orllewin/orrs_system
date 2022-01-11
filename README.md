# ORRS System

Server software to power the [https://orrs.live](orrs.live/) netradio station. 

orrs.live is currently using Azuracast but scheduling is unfortunately broken and the developers are having trouble replicating the issues several users are reporting. ORRS System is an attempt to roll my own radio scheduler that will interrupt AzuraCasts General Rotation AutoDJ. 

## Requirements

* A method of scheduling an action, shows should only be scheduled to start on the hour and can be any length
* When a show isn't playing random tracks should be chosen from a general pool

## Tools

* [Icecast](https://icecast.org/)
* [Liquid Soap](https://www.liquidsoap.info/) 
* [Krontab](https://insanusmokrassar.github.io/krontab/)

## LiquidSoap

The following command will interrupt AzuraCast's General Rotation, it won't close on completion, but moving this to a file instead of a one-liner should make that easier (there are on complete callbacks or similar, eg: `on_stop=shutdown`)

```
liquidsoap \
  'output.icecast(%vorbis,
     host = "orrs.live", 
     port = 8005,
     password = "azuracastemail,azuracastpassword", 
     mount = "/",
     mksafe(playlist("p.m3u")))'
```
