package org.mercury.TicketService.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.mercury.TicketService.bean.Ticket;
import org.mercury.TicketService.criteria.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

/**
 * @ClassName TicketSpecification
 * @Description TODO
 * @Author katefu
 * @Date 8/22/23 10:44 AM
 * @Version 1.0
 **/
public class TicketSpecification implements Specification<Ticket> {
    private SearchCriteria criteria;
    public TicketSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}
