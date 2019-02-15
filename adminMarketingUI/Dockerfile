# adminMarketingUI PRODUCTION DOCKERFILE
FROM node:10.15.1-stretch-slim
WORKDIR /usr/src

EXPOSE 4200

ADD . .

RUN apt-get update
RUN apt-get install -y nginx && service nginx stop
RUN mv -f ./nginx.conf /etc/nginx/nginx.conf
RUN npm install -g @angular/cli
RUN npm install
#RUN ng build --prod --build-optimizer --base-href=/ui/ && service nginx start
RUN ng build --base-href=/ui/

CMD /usr/sbin/nginx
