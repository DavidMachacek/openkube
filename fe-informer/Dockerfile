# build environment
FROM node:13.12.0-alpine as build
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json ./
COPY package-lock.json ./
RUN npm ci --silent
RUN npm install react-scripts@3.4.1 -g --silent
COPY . ./
RUN npm run build

# production environment
FROM nginx:stable-alpine
COPY --from=build /app/build /usr/share/nginx/html

#RUN   echo $'\n\
# location / {\n\
#     sub_filter      \'<head>\'\n\
#         \'<head><script>window[\"adrum-app-key\"]=\"EC-AAB-PXY\";window[\"adrum-start-time\"]=new Date().getTime();</script><script type=\"text/javascript\" src=\"//cdn.appdynamics.com/adrum/adrum-latest.js\"></script>\';\n\
#     sub_filter_once on;\n\
# }\n\
#' >> /etc/nginx/conf.d/location.conf
COPY default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
