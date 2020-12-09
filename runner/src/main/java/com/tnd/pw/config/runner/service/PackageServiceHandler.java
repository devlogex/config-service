package com.tnd.pw.config.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsPackageRepresentation;
import com.tnd.pw.config.common.representations.PackageRepresentation;
import com.tnd.pw.config.common.requests.AdminRequest;
import com.tnd.pw.config.common.requests.AnonymousRequest;
import com.tnd.pw.config.common.requests.UserRequest;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;


public interface PackageServiceHandler {
    CsPackageRepresentation addPackage(AdminRequest request) throws DBServiceException, PackageNotFoundException;
    CsPackageRepresentation getPackage(AnonymousRequest request) throws DBServiceException, PackageNotFoundException;
    PackageRepresentation updatePackage(AdminRequest request) throws DBServiceException, PackageNotFoundException;
    CsPackageRepresentation registerPackage(UserRequest request) throws DBServiceException, PackageNotFoundException;

    CsPackageRepresentation statisticPackageDetail(AdminRequest request) throws DBServiceException;
    CsPackageRepresentation statisticPackageList(AdminRequest request) throws DBServiceException;
}
