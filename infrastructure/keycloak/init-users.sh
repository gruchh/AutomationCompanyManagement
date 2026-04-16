#!/bin/sh
set -e

echo ">>> START INIT SCRIPT"
echo ">>> KEYCLOAK_URL: $KEYCLOAK_URL"
echo ">>> KEYCLOAK_REALM: $KEYCLOAK_REALM"
echo ">>> KEYCLOAK_ADMIN: $KEYCLOAK_ADMIN"

CONFIG_PATH="/tmp/kcadm.config"

/opt/keycloak/bin/kcadm.sh config credentials \
  --server "$KEYCLOAK_URL" \
  --realm master \
  --user "$KEYCLOAK_ADMIN" \
  --password "$KEYCLOAK_ADMIN_PASSWORD" \
  --config "$CONFIG_PATH"

echo ">>> Logged in successfully"
echo ">>> INIT SCRIPT FINISHED"