#!/bin/sh

set -e

CERT_DIR=/etc/nginx/certs

KEY_FILE="$CERT_DIR/server.key"
CRT_FILE="$CERT_DIR/server.crt"

if [ ! -f "$KEY_FILE" ] || [ ! -f "$CRT_FILE" ]; then

    echo "[nginx] Certificado no encontrado, generando uno autofirmado..."

    mkdir -p "$CERT_DIR"

    openssl req -x509 -nodes -days 365 \
        -newkey rsa:2048 \
        -keyout "$KEY_FILE" \
        -out "$CRT_FILE" \
        -subj "/C=ES/ST=Andalucia/L=Castilleja de la Cuesta/O=TicketLogger/OU=DAW/CN=localhost"

else

    echo "[nginx] Certificado ya existe, reutilizándolo."

fi

echo "[nginx] Arrancando Nginx..."

exec nginx -g "daemon off;"