FROM quay.io/keycloak/keycloak:26.0.6
USER root
RUN microdnf update -y && \
    microdnf install -y bash curl && \
    microdnf clean all
COPY init-users.sh /opt/keycloak/bin/init-users.sh
RUN chmod +x /opt/keycloak/bin/init-users.sh
USER keycloak
ENV KC_DB=postgres
RUN /opt/keycloak/bin/kc.sh build
ENV KC_HTTP_ENABLED=true
ENV KC_HEALTH_ENABLED=true
ENV KC_HOSTNAME_STRICT=false
ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
