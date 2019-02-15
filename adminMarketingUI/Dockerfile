# adminMarketingUI PRODUCTION DOCKERFILE
FROM node:10.15.1-stretch-slim
WORKDIR /usr/src

ADD . .

RUN apt-get update
RUN apt-get install -y nginx && systemctl stop nginx
RUN mv -f ./nginx.conf /etc/nginx/nginx.conf
RUN npm install -g @angular/cli
RUN npm install
RUN ng build --prod --build-optimizer --base-href=/ui/ && systemctl start nginx

EXPOSE 4200

CMD tail -f /var/log/nginx/access.log