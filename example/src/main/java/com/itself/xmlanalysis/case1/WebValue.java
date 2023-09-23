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
@XmlRootElement(name = "Web")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder ={
        "title",
        "url"
} )
public class WebValue {
    @XmlElement(name = "Title",required = true)
    private String title;
    @XmlElement(name = "Url",required = true)
    private String url;

}
