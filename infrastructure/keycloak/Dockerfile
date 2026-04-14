FROM quay.io/keycloak/keycloak:26.0.6
USER root
RUN microdnf update -y && \
    microdnf install -y bash curl && \
    microdnf clean all

COPY init-users.sh /opt/keycloak/bin/init-users.sh
RUN chmod +x /opt/keycloak/bin/init-users.sh
USER keycloak