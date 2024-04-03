#stage 1
FROM node:latest as node
WORKDIR /app
COPY . .
RUN npm cache clean --force
RUN npm install --force
RUN npm run build
#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/taas-frontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

