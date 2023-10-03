FROM node:17-alpine
WORKDIR hotel-frontend
COPY public/ /hotel-frontend/public
COPY src/ /hotel-frontend/src
COPY package.json /hotel-frontend/
RUN npm install
EXPOSE 3000
CMD ["npm", "start"]