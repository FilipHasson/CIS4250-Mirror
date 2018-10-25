#!/bin/sh

# Run nginx server
nginx -g 'daemon off;' &

# Reload config changes automatically
inotifywait -m -q -e close_write /etc/nginx/conf.d/http.conf | while read events
  do echo "reloading..."
  nginx -s reload
done
