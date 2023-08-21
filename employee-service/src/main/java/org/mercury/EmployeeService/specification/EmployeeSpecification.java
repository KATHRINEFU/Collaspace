package org.mercury.EmployeeService.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.mercury.EmployeeService.bean.Employee;
import org.mercury.EmployeeService.criteria.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import java.util.Date;


/**
 * @ClassName EmployeeSpecification
 * @Description TODO
 * @Author katefu
 * @Date 8/18/23 11:09 AM
 * @Version 1.0
 **/
public class EmployeeSpecification implements Specification<Employee> {
    private SearchCriteria criteria;

    public EmployeeSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

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
