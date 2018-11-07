FROM node:latest
WORKDIR /usr/src

ADD . .

RUN npm install

EXPOSE 4200
CMD ng serve -h 0.0.0.0