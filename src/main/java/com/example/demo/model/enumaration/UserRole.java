package com.example.demo.model.enumaration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum UserRole {
//    PRODUCT_OWNER,
//    CUSTOMER,
//    ADMIN;
    CUSTOMER("CUSTOMER",2,null),
    PRODUCT_OWNER("PRODUCT_OWNER",1,null),

    ADMIN("ADMIN",0, Arrays.asList(PRODUCT_OWNER, CUSTOMER));

    public final String shortName;
    public final int roleOrder;
    private List<UserRole> includings;

    public String role(){
        return "ROLE_" + this.name();
    }
    public static String getRoleHierarchy() {
        return Arrays.stream(UserRole.values())
                .sorted(Comparator.comparingInt(UserRole::getRoleOrder))
                .map(UserRole::getRole)
                .collect(Collectors.joining(" > "));


    }

    public String getRole() {
        return "ROLE_" + name();
    }

    public static List<String> getRoleHierarchyList() {

        List<String> hierarchyList = new ArrayList<>();
        // iterate over all defined system roles ...
        for (UserRole r : UserRole.values()) {
            if (!CollectionUtils.isEmpty(r.getIncludings())) {
                // iterate over all includings
                for (UserRole includedRole : r.getIncludings()) {
                   hierarchyList.add(r.role() + " > " + includedRole.role());

                }
            }
        }
        return hierarchyList;
    }
}
