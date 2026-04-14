FROM quay.io/keycloak/keycloak:26.0.5

ENV KC_DB=postgres
RUN /opt/keycloak/bin/kc.sh build

COPY ./realm-export.json /opt/keycloak/data/import/realm-export.json

ENV KC_HTTP_ENABLED=true
ENV KC_HEALTH_ENABLED=true
ENV KC_HOSTNAME_STRICT=false

ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
# TU USUNĘLIŚMY CMD