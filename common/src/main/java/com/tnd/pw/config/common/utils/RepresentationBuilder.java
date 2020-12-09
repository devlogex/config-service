package com.tnd.pw.config.common.utils;

import com.tnd.pw.config.common.representations.*;
import com.tnd.pw.config.packages.entity.PackageCodeEntity;
import com.tnd.pw.config.packages.enums.PackageState;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.product.entity.ProductEntity;
import com.tnd.pw.config.product.enums.ProductType;
import com.tnd.pw.config.user.entity.UserProfileEntity;
import com.tnd.pw.config.workspace.config.entity.WorkspaceConfigEntity;
import com.tnd.pw.config.workspace.entity.WorkspaceEntity;
import com.tnd.pw.config.workspace.enums.WorkspaceState;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class RepresentationBuilder {

    public static PackageRepresentation buildPackageRepresentation(PackageEntity packageEntity) {
        PackageRepresentation packageRepresentation = new PackageRepresentation();
        packageRepresentation.setId(packageEntity.getId().toString());
        packageRepresentation.setName(packageEntity.getName());
        packageRepresentation.setDescription(packageEntity.getDescription());
        packageRepresentation.setCreatedAt(packageEntity.getCreatedAt().toString());
        packageRepresentation.setCreatedBy(packageEntity.getCreatedBy().toString());
        packageRepresentation.setMaxMember(packageEntity.getMaxMember());
        packageRepresentation.setMaxProduct(packageEntity.getMaxProduct());
        packageRepresentation.setPrice(packageEntity.getPrice());
        packageRepresentation.setState(PackageState.values()[packageEntity.getState()].name());
        if(packageEntity.getUpdatedAt() != null) {
            packageRepresentation.setUpdatedAt(packageEntity.getUpdatedAt().toString());
        }
        if(packageEntity.getUpdatedBy() != null) {
            packageRepresentation.setUpdatedBy(packageEntity.getUpdatedBy().toString());
        }
        packageRepresentation.setPeriodValidity(packageEntity.getPeriodValidity().toString());
        return packageRepresentation;
    }

    public static CsPackageRepresentation buildListPackageRep(List<PackageEntity> entities) {
        List<PackageRepresentation> list = new ArrayList<>();
        for(PackageEntity entity: entities) {
            list.add(buildPackageRepresentation(entity));
        }
        return new CsPackageRepresentation(list);
    }


    public static WorkspaceRepresentation buildWorkspaceRepresentation(WorkspaceEntity workspaceEntity, String config) {
        WorkspaceRepresentation workspaceRepresentation = new WorkspaceRepresentation();
        workspaceRepresentation.setConfig(config);
        workspaceRepresentation.setId(workspaceEntity.getId().toString());
        workspaceRepresentation.setState(WorkspaceState.values()[workspaceEntity.getState()].name());
        workspaceRepresentation.setOwnerId(workspaceEntity.getOwnerId().toString());
        workspaceRepresentation.setDomain(workspaceEntity.getDomain());
        workspaceRepresentation.setCreatedAt(workspaceEntity.getCreatedAt().toString());
        workspaceRepresentation.setCreatedBy(workspaceEntity.getCreatedBy().toString());
        if(workspaceEntity.getUpdatedAt() != null) {
            workspaceRepresentation.setUpdatedAt(workspaceEntity.getUpdatedAt().toString());
        }
        if(workspaceEntity.getUpdatedBy() != null) {
            workspaceRepresentation.setUpdatedBy(workspaceEntity.getUpdatedBy().toString());
        }
        return workspaceRepresentation;
    }

    public static CsWorkspaceRepresentation buildListWorkspaceRep(List<WorkspaceEntity> workspaceEntities, List<WorkspaceConfigEntity> workspaceConfigEntities) {
        List<WorkspaceRepresentation> list = new ArrayList<>();
        for(WorkspaceEntity workspaceEntity: workspaceEntities) {
            for(WorkspaceConfigEntity workspaceConfigEntity: workspaceConfigEntities) {
                if(workspaceEntity.getConfigId().compareTo(workspaceConfigEntity.getId()) == 0) {
                    list.add(buildWorkspaceRepresentation(workspaceEntity, workspaceConfigEntity.getConfig()));
                }
            }
        }
        return new CsWorkspaceRepresentation(list);
    }

    public static ProductRepresentation buildProductRepresentation(ProductEntity productEntity) {
        ProductRepresentation productRepresentation = new ProductRepresentation();
        productRepresentation.setId(productEntity.getId().toString());
        productRepresentation.setCreatedAt(productEntity.getCreatedAt().toString());
        productRepresentation.setCreatedBy(productEntity.getCreatedBy().toString());
        if(productEntity.getParent() != null) {
            productRepresentation.setParent(productEntity.getParent().toString());
        }
        productRepresentation.setWorkspaceId(productEntity.getWorkspaceId().toString());
        productRepresentation.setDescription(productEntity.getDescription());
        productRepresentation.setFiles(productEntity.getFiles());
        productRepresentation.setName(productEntity.getName());
        productRepresentation.setType(ProductType.values()[productEntity.getType()].name());
        return productRepresentation;
    }

    public static CsProductRepresentation buildListProductRep(List<ProductEntity> productEntities) {
        Node<ProductRepresentation> root = new Node<>(null);
        if(productEntities.size() == 1) {
            root.addChild(buildProductRepresentation(productEntities.get(0)));
            return new CsProductRepresentation(root);
        }

        for(ProductEntity productEntity: productEntities) {
            if(productEntity.getParent() == null) {
                root.addChild(buildProductRepresentation(productEntity));
            }
            else {
                Node<ProductRepresentation> node = new Node<>(buildProductRepresentation(productEntity));
                for(Node<ProductRepresentation> child: root.getChildren()) {
                    insertNode(child, node);
                }
            }
        }
        return new CsProductRepresentation(root);
    }

    public static void insertNode(Node<ProductRepresentation> root, Node<ProductRepresentation> node) {
        if(root == null) {
            return;
        }
        if(root.getData().getId().equals(node.getData().getParent())) {
            root.addChild(node);
            return;
        }
        for(Node<ProductRepresentation> child: root.getChildren()) {
            insertNode(child, node);
        }
    }

    public static UserRepresentation buildUserRepresentation(UserProfileEntity userProfile, String token) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setToken(token);
        userRepresentation.setId(userProfile.getId());
        userRepresentation.setEmail(userProfile.getEmail());
        userRepresentation.setAvatar(userProfile.getAvatar());
        userRepresentation.setFirstName(userProfile.getFirstName());
        userRepresentation.setLastName(userProfile.getLastName());
        userRepresentation.setCompanyName(userProfile.getCompanyName());
        userRepresentation.setDomain(userProfile.getDomain());
        userRepresentation.setRole(userProfile.getRole());
        return userRepresentation;
    }

    public static UserRepresentation buildUserRepresentation(UserProfileEntity userProfile, String token, HashMap<Long, String> productPermissions) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setToken(token);
        userRepresentation.setId(userProfile.getId());
        userRepresentation.setEmail(userProfile.getEmail());
        userRepresentation.setAvatar(userProfile.getAvatar());
        userRepresentation.setFirstName(userProfile.getFirstName());
        userRepresentation.setLastName(userProfile.getLastName());
        userRepresentation.setCompanyName(userProfile.getCompanyName());
        userRepresentation.setDomain(userProfile.getDomain());
        userRepresentation.setRole(userProfile.getRole());
        userRepresentation.setProductPermissions(productPermissions);
        return userRepresentation;
    }

    public static CsProductRepresentation buildListProductRep(List<ProductEntity> productEntities, String token, HashMap<Long, String> productPermissions) {
        Node<ProductRepresentation> root = new Node<>(null);
        if(productEntities.size() == 1) {
            root.addChild(buildProductRepresentation(productEntities.get(0)));
            return new CsProductRepresentation(root, token, productPermissions);
        }

        for(ProductEntity productEntity: productEntities) {
            if(productEntity.getParent() == null) {
                root.addChild(buildProductRepresentation(productEntity));
            }
            else {
                Node<ProductRepresentation> node = new Node<>(buildProductRepresentation(productEntity));
                for(Node<ProductRepresentation> child: root.getChildren()) {
                    insertNode(child, node);
                }
            }
        }
        return new CsProductRepresentation(root, token, productPermissions);
    }

    public static UserRepresentation buildUserRepresentation(UserProfileEntity userProfile) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(userProfile.getId());
        userRepresentation.setEmail(userProfile.getEmail());
        userRepresentation.setAvatar(userProfile.getAvatar());
        userRepresentation.setFirstName(userProfile.getFirstName());
        userRepresentation.setLastName(userProfile.getLastName());
        userRepresentation.setCompanyName(userProfile.getCompanyName());
        userRepresentation.setDomain(userProfile.getDomain());
        userRepresentation.setRole(userProfile.getRole());
        return userRepresentation;
    }

    public static CsUserRepresentation buildListUserProfile(List<UserProfileEntity> userProfiles) {
        List<UserRepresentation> userReps = new ArrayList<>();
        if(userProfiles == null) {
            userProfiles = new ArrayList<>();
        }
        for (UserProfileEntity entity : userProfiles) {
            userReps.add(buildUserProfile(entity));
        }
        return new CsUserRepresentation(userReps);
    }

    public static UserRepresentation buildUserProfile(UserProfileEntity entity) {
        UserRepresentation representation = new UserRepresentation();
        representation.setId(entity.getId());
        representation.setRole(entity.getRole());
        representation.setDomain(entity.getDomain());
        representation.setEmail(entity.getEmail());
        representation.setFirstName(entity.getFirstName());
        representation.setLastName(entity.getLastName());
        return representation;
    }

    public static CsPackageRepresentation buildStatisticalMonthly(List<PackageCodeEntity> packageCodeEntities, Long startTime, Long endTime) {
        LocalDateTime localDateTime = Instant.ofEpochMilli(startTime)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LinkedHashMap<String, StatisticalInfo> map = new LinkedHashMap<>();
        while(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() <= endTime) {
            long start = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            localDateTime = localDateTime.plusMonths(1);
            localDateTime = localDateTime.plusDays(-1);
            long end = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            List<PackageCodeEntity> packagesInMonth = packageCodeEntities.stream().filter(packageCode -> packageCode.getCreatedAt() >= start && packageCode.getCreatedAt() <= end).collect(Collectors.toList());
            long countSale = packagesInMonth.size();
            BigDecimal moneyIn = packagesInMonth.stream().map(packageCode -> packageCode.getPrice()).reduce(BigDecimal.ZERO, (cur, sum) -> cur.add(sum));

            StatisticalInfo statisticalInfo = new StatisticalInfo();
            statisticalInfo.setMonth(localDateTime.getMonth().getValue());
            statisticalInfo.setYear(localDateTime.getYear());
            statisticalInfo.setCountSale(countSale);
            statisticalInfo.setMoneyIn(moneyIn);
            map.put(String.format("%02d-%d", localDateTime.getMonth().getValue(), localDateTime.getYear()), statisticalInfo);

            localDateTime = localDateTime.plusDays(1);
        }
        CsPackageRepresentation representation = new CsPackageRepresentation();
        representation.setStatisticalMonthly(map);
        return representation;
    }

    public static CsPackageRepresentation buildStatisticalQuarterly(List<PackageCodeEntity> packageCodeEntities, Long startTime, Long endTime) {
        return null;
    }

    public static CsPackageRepresentation buildStatisticPackageList(List<PackageEntity> packageEntities, List<WorkspaceConfigEntity> workspaceConfigEntities) {
        List<PackageRepresentation> packageReps = new ArrayList<>();
        for(PackageEntity packageEntity: packageEntities) {
            PackageRepresentation packageRep = buildPackageRepresentation(packageEntity);
            long count = workspaceConfigEntities.stream()
                    .filter(config -> config.getPackageId().compareTo(packageEntity.getId()) == 0)
                    .count();
            packageRep.setWorkspaceCount(count);
            packageReps.add(packageRep);
        }
        CsPackageRepresentation representation = new CsPackageRepresentation();
        representation.setPackageReps(packageReps);
        return representation;
    }
}
