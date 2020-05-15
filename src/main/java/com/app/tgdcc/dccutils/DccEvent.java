package com.app.tgdcc.dccutils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DccEvent {
    @JsonProperty("EventId")
    private int eventId;

    public DccEvent() {
    }

    public int getEventId() {
        return eventId;
    }

    //Example
    /*
            "Id": "System1:S7_1_Device_24800001.State.Connections.OpState_1:_alert_hdl.2._value~637230706706470000~0~7378",
                "Deleted": false,
                "EventId": 7378,
                "EventText": "",
                "CategoryId": 5,
                "CategoryDescriptor": "Medium",
                "State": "Unprocessed",
                "Cause": "PLC Started (Restart)",
                "SrcPropertyId": "System1:S7_1_Device_24800001.State.Connections.OpState_1:_alert_hdl.2._value",
                "SrcObservedPropertyId": "System1:S7_1_Device_24800001.State.Connections.OpState_1:_alert_hdl.2._value",
                "SrcState": "Quiet",
                "SrcSystemId": 1,
                "SrcViewName": "ManagementView",
                "SrcViewDescriptor": "Management View",
                "SrcDesignation": "ManagementView.FieldNetworks.BA_Site01_B02_S7.CP_56000_001_11202_24800001",
                "SrcLocation": "Project.Field Networks.BA Site01 B02 S7.Device_CP_56000_001_11202",
                "SrcName": "CP_56000_001_11202_24800001.Operating State",
                "SrcDescriptor": "Device_CP_56000_001_11202.Operating State",
                "SrcDisciplineId": 50,
                "SrcDisciplineDescriptor": "Building Automation",
                "SrcSubDisciplineId": 0,
                "CreationTime": "2020-04-21T10:57:50.647Z",
                "Direction": "None",
                "InfoDescriptor": "PLC In Stop",
                "InProcessBy": "",
                "Commands": [
            {
                "Id": "Select",
                    "EventId": "System1:S7_1_Device_24800001.State.Connections.OpState_1:_alert_hdl.2._value~637230706706470000~0~7378",
                    "Configuration": 0,
                    "ValidationRules": {
                "CommentRule": "Optional",
                        "ReAuthentication": "NoNeed",
                        "Configuration": 0,
                        "IsFourEyesEnabled": false,
                        "_links": []
            },
                "_links": [
                {
                    "Rel": "command",
                        "Href": "api/eventscommands/System1%253AS7_1_Device_24800001.State.Connections.OpState_1%253A_alert_hdl.2._value%257E637230706706470000%257E0%257E7378/Select",
                        "IsTemplated": false
                }
                    ]
            }
            ],
            "MessageText": [],
            "DesignationList": [
            {
                "ViewId": 9,
                    "Descriptor": "System1.ManagementView:ManagementView.FieldNetworks.BA_Site01_B02_S7.CP_56000_001_11202_24800001"
            }
            ],
            "DescriptionList": [
            {
                "ViewId": 9,
                    "Descriptor": "Device_CP_56000_001_11202.Operating State"
            }
            ],
            "SourceDesignationList": [
            {
                "ViewId": 9,
                    "Descriptor": "CP_56000_001_11202_24800001.Operating State"
            }
            ],
            "DescriptionLocationsList": [
            {
                "ViewId": 9,
                    "Descriptor": "Project.Field Networks.BA Site01 B02 S7"
            }
            ],
            "NextCommand": "Acknowledge",
                "SuggestedAction": "Acknowledge",
                "SrcAlias": "24800001",
                "_links": [
            {
                "Rel": "category",
                    "Href": "api/tables/categories/5",
                    "IsTemplated": false
            },
            {
                "Rel": "discipline",
                    "Href": "api/tables/disciplines/50",
                    "IsTemplated": false
            }
            ]
        },*/
}
