package org.mercury.EventService.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName SearchCriteria
 * @Description TODO
 * @Author katefu
 * @Date 8/19/23 11:20 AM
 * @Version 1.0
 **/

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}