# clientUI PRODUCTION DOCKERFILE
# the webserver is listening on port 80
# the webserver will respond on /

FROM node:10.15.1-stretch-slim

WORKDIR /usr/src

ADD . .

RUN apt-get update

RUN apt-get install -y nginx && service nginx stop

RUN mv -f ./nginx.conf /etc/nginx/nginx.conf

RUN npm install -g @angular/cli

RUN npm install

#RUN ng build --prod --build-optimizer

RUN ng build -c production

# flush black hole
RUN rm -rf ./node_modules

CMD /usr/sbin/nginx