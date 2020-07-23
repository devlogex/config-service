package com.tnd.pw.config.common.utils;

import com.tnd.pw.config.common.representations.*;
import com.tnd.pw.config.packages.enums.PackageState;
import com.tnd.pw.config.packages.entity.PackageEntity;
import com.tnd.pw.config.product.entity.ProductEntity;
import com.tnd.pw.config.product.enums.ProductType;
import com.tnd.pw.config.workspace.config.entity.WorkspaceConfigEntity;
import com.tnd.pw.config.workspace.entity.WorkspaceEntity;
import com.tnd.pw.config.workspace.enums.WorkspaceState;

import java.util.ArrayList;
import java.util.List;

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
        productRepresentation.setName(productEntity.getName());
        productRepresentation.setType(ProductType.values()[productEntity.getType()].name());
        return productRepresentation;
    }

    public static CsProductRepresentation buildListProductRep(List<ProductEntity> productEntities) {
        Node<ProductRepresentation> root = new Node<>(null);
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
}
