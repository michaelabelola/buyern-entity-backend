package com.buyern.buyern.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{entityId}/asset")
public class AssetController {
    private String registerAsset() {
        return null;
    }

    private String updateAsset() {
        return null;
    }

    private String getAsset() {
        return null;
    }

    /**
     * <h3>Get Assets</h3>
     * get a particular asset
     *
     * @param id the asset id
     */
    private String getAsset(String id) {
        return null;
    }

    /**
     * <h3>Get Assets</h3>
     * get all assets using entity Id
     */
    private String getCompanyAssets() {
        return null;
    }

    private String getAssets(String byIds) {
        return null;
    }
}
