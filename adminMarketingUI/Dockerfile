# adminMarketingUI PRODUCTION DOCKERFILE
FROM node:10.15.1-stretch-slim
WORKDIR /usr/src

ADD . .

RUN apt-get update
RUN apt-get install -y nginx && service nginx stop
RUN mv -f ./nginx.conf /etc/nginx/nginx.conf && chown www-data /etc/nginx/nginx.conf && www-data nginx /etc/nginx/nginx.conf
RUN npm install -g @angular/cli
RUN npm install
#RUN ng build --prod --build-optimizer --base-href=/ui/ && service nginx start
RUN ng build --base-href=/ui/
RUN nginx -c /etc/nginx/nginx.conf -t && service nginx start

EXPOSE 4200

CMD tail -f /var/log/nginx/access.log