# Services

This project uses a microservice architechture and is deployed inside containers managed by Docker. The project can be deployed either in either a development or production environment.

## Development Deployment

In development mode each service is run using its own development server with debugging enabled. Containers mount their respective services directory so changes to their source files will be reflected and live-reloaded.

To launch the project in development mode cd into run `docker-compose up -d --build`.

## Production Deployment

In production mode all source files are transpiled, optimized, minified, and copied into their respective containers at build time and served using NGINX.

To launch the project in development mode cd into run `docker-compose -f docker-compose.prod.yml up -d --build`.
