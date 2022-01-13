#!/usr/bin/liquidsoap

show = once(single("te_waves_clip.mp3"))

output.icecast(%vorbis,
  host = "orrs.live", 
  port = 8005,
  password = "orllewin@fastmail.uk,password", 
  mount = "/",
  on_stop = shutdown,
  fallible = true,
  show)