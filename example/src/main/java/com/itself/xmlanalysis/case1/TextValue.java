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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Text")
@XmlType(propOrder ={
        "value"
} )
public class TextValue {
    @XmlElement(name = "Value",required = true)
    private String value;
}
