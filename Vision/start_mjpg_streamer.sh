#!/bin/sh -
env LD_LIBRARY_PATH=/usr/local/lib:$LD_LIBRARY_PATH mjpg_streamer -o "output_http.so -w ./www -p 1180" -i "input_uvc.so -ex 1 -f 15 -r 320x240 -y -n" 

