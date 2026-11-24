FROM nginx:1.27-alpine

# Necesitamos OpenSSL para crear el certificado
RUN apk add --no-cache openssl

# Configuración personalizada de Nginx
COPY nginx.conf /etc/nginx/nginx.conf

# Script que generará el certificado
COPY docker-entrypoint.sh /docker-entrypoint.sh

# Permiso para ejecutar el script
RUN chmod +x /docker-entrypoint.sh

# Script que se ejecutará al arrancar el contenedor
ENTRYPOINT ["/docker-entrypoint.sh"]