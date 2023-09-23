package com.itself.xmlanalysis.case1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder ={
        "type",
        "name",
        "webValue",
        "textValue"
} )
public class Item {
    @XmlElement(name = "Type",required = true)
    private String type;

    @XmlElement(name = "Name",required = true)
    private String name;

    @XmlElement(name = "Text",required = true)
    private TextValue textValue;

    @XmlElement(name = "Web",required = true)
    private WebValue webValue;
}
