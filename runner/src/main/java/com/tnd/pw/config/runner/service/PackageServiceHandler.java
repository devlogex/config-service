package com.tnd.pw.config.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.config.common.representations.CsPackageRepresentation;
import com.tnd.pw.config.common.representations.PackageRepresentation;
import com.tnd.pw.config.common.requests.ConfigRequest;
import com.tnd.pw.config.packages.exception.PackageNotFoundException;

import java.io.IOException;

public interface PackageServiceHandler {
    CsPackageRepresentation addPackage(ConfigRequest request) throws IOException, DBServiceException, PackageNotFoundException;
    CsPackageRepresentation getPackage(ConfigRequest request) throws IOException, DBServiceException, PackageNotFoundException;
    PackageRepresentation updatePackage(ConfigRequest request) throws IOException, DBServiceException, PackageNotFoundException;
    CsPackageRepresentation registerPackage(ConfigRequest request) throws DBServiceException, IOException, PackageNotFoundException;
}
