package org.mercury.EventService.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.mercury.EventService.bean.Event;
import org.mercury.EventService.criteria.SearchCriteria;
import org.mercury.EventService.filter.EventFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

/**
 * @ClassName EventSpecification
 * @Description TODO
 * @Author katefu
 * @Date 8/27/23 11:17 PM
 * @Version 1.0
 **/
public class EventSpecification implements Specification<Event> {
    private final SearchCriteria criteria;
    public EventSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }
    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            Date dateValue = (Date) criteria.getValue();
            return builder.greaterThanOrEqualTo(root.<Date>get(criteria.getKey()), dateValue);
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            Date dateValue = (Date) criteria.getValue();
            return builder.lessThanOrEqualTo(
                    root.<Date>get(criteria.getKey()), dateValue);
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else if (root.get(criteria.getKey()).getJavaType() == Boolean.class) {
                return builder.equal(root.get(criteria.getKey()), Boolean.valueOf(criteria.getValue().toString()));
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
