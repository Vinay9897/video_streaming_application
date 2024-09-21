# Use Node.js image for building the React application
FROM node:20 AS build

# Set the working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package*.json ./

RUN npm install

# Copy the application code
COPY . .

# Start the application in development mode
CMD ["npm", "run", "dev"]

