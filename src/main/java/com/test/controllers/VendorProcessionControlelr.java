package com.test.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class VendorProcessionControlelr {

    private final List<String> vendors = new CopyOnWriteArrayList<>();

    public VendorProcessionControlelr() {
        // preload a couple of sample vendors
        vendors.add("Acme Supplies");
        vendors.add("Global Traders");
    }

    // added annotation
    @GetMapping("/getVendors")
    public ResponseEntity<List<String>> getVendors() {
        return new ResponseEntity<>(Collections.unmodifiableList(vendors), HttpStatus.OK);
    }

    // added annotation
    @PostMapping("/addVendors")
    public ResponseEntity<String> addVendors(@RequestBody List<String> newVendors) {
        if (newVendors == null || newVendors.isEmpty()) {
            return new ResponseEntity<>("No vendors provided", HttpStatus.BAD_REQUEST);
        }
        List<String> added = new ArrayList<>();
        for (String v : newVendors) {
            if (v != null && !v.trim().isEmpty()) {
                vendors.add(v.trim());
                added.add(v.trim());
            }
        }
        return new ResponseEntity<>("Added vendors: " + added, HttpStatus.OK);
    }
}
