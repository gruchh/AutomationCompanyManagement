package com.automationcompany.project.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectTechnology {

    SIEMENS_S7(ProjectTechnologyCategory.PLC),
    ALLEN_BRADLEY(ProjectTechnologyCategory.PLC),
    MITSUBISHI_PLC(ProjectTechnologyCategory.PLC),
    OMRON_PLC(ProjectTechnologyCategory.PLC),
    BECKHOFF_TWINCAT(ProjectTechnologyCategory.PLC),
    SCHNEIDER_PLC(ProjectTechnologyCategory.PLC),

    FANUC_ROBOT(ProjectTechnologyCategory.ROBOT),
    KUKA_ROBOT(ProjectTechnologyCategory.ROBOT),
    ABB_ROBOT(ProjectTechnologyCategory.ROBOT),
    YASKAWA_ROBOT(ProjectTechnologyCategory.ROBOT),
    MITSUBISHI_ROBOT(ProjectTechnologyCategory.ROBOT),
    UNIVERSAL_ROBOTS(ProjectTechnologyCategory.ROBOT),

    WINCC(ProjectTechnologyCategory.SCADA_HMI_HISTORIAN),
    IGNITION(ProjectTechnologyCategory.SCADA_HMI_HISTORIAN),
    FACTORY_TALK_VIEW(ProjectTechnologyCategory.SCADA_HMI_HISTORIAN),
    INTOUCH(ProjectTechnologyCategory.SCADA_HMI_HISTORIAN),
    CITECT_SCADA(ProjectTechnologyCategory.SCADA_HMI_HISTORIAN),
    AVEVA_EDGE(ProjectTechnologyCategory.SCADA_HMI_HISTORIAN),
    WONDERWARE(ProjectTechnologyCategory.SCADA_HMI_HISTORIAN),
    IFIX(ProjectTechnologyCategory.SCADA_HMI_HISTORIAN),
    HISTORIAN(ProjectTechnologyCategory.SCADA_HMI_HISTORIAN),
    PI_SYSTEM(ProjectTechnologyCategory.SCADA_HMI_HISTORIAN),

    JAVA(ProjectTechnologyCategory.PROGRAMMING),
    PYTHON(ProjectTechnologyCategory.PROGRAMMING),
    C_SHARP(ProjectTechnologyCategory.PROGRAMMING),
    C_PLUS_PLUS(ProjectTechnologyCategory.PROGRAMMING),
    JAVASCRIPT(ProjectTechnologyCategory.PROGRAMMING),
    TYPESCRIPT(ProjectTechnologyCategory.PROGRAMMING),
    SQL(ProjectTechnologyCategory.PROGRAMMING),
    HTML(ProjectTechnologyCategory.PROGRAMMING),
    CSS(ProjectTechnologyCategory.PROGRAMMING),

    OPC_UA(ProjectTechnologyCategory.COMMUNICATION),
    MQTT(ProjectTechnologyCategory.COMMUNICATION),
    MODBUS(ProjectTechnologyCategory.COMMUNICATION),
    PROFINET(ProjectTechnologyCategory.COMMUNICATION),
    ETHERNET_IP(ProjectTechnologyCategory.COMMUNICATION),
    DEVICENET(ProjectTechnologyCategory.COMMUNICATION),
    CCLINK(ProjectTechnologyCategory.COMMUNICATION),

    LINUX(ProjectTechnologyCategory.PLATFORM),
    WINDOWS(ProjectTechnologyCategory.PLATFORM),
    DOCKER(ProjectTechnologyCategory.PLATFORM),
    KUBERNETES(ProjectTechnologyCategory.PLATFORM),
    GIT(ProjectTechnologyCategory.PLATFORM),
    JIRA(ProjectTechnologyCategory.PLATFORM),

    OTHER_TECH(ProjectTechnologyCategory.OTHER);

    private final ProjectTechnologyCategory category;
}
