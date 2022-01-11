# ORRS System

Server software to power the [https://orrs.live](orrs.live/) netradio station. 

orrs.live is currently using Azuracast but scheduling is unfortunately broken and the developers are having trouble replicating the issues several users are reporting. ORRS System is an attempt to roll my own radio scheduler. 

## Requirements

* A method of scheduling an action, shows should only be scheduled to start on the hour and can be any length
* When a show isn't playing random tracks should be chosen from a general pool

## Tools

* [Icecast](https://icecast.org/)
* [Liquid Soap](https://www.liquidsoap.info/) 
* [Krontab](https://insanusmokrassar.github.io/krontab/)
