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

The following .liq file will play a file or playlist once then temrinate, returning orrs.live to play from the General Rotation AutoDJ

```
#!/usr/bin/liquidsoap

track = once(single("test.mp3"))

output.icecast(%vorbis,
  host = "orrs.live", 
  port = 8000,
  password = "REDACTED", 
  mount = "/",
  on_stop = shutdown,
  fallible = true,
  track)
```
