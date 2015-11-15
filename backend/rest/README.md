# REST Best Practices
At this point we're going to talk about...

[Replace this logo] ![BEEVA](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")

## Index

* [Introduction](#rest-introduction)
* [URL construction](#url-construction)
* [Operations over resources](#operations-over-resources)
* [Status codes](#status-codes)
* [Payload formatting](#payload-formatting)
* [Filters](#filters)
* [Pagination](#pagination)
* [HATEOAS](#hateoas)
* [API Versioning](#api-versioning)
* [API throughput restrictions](api-throughput-restrictions)
* [OAuth](#oauth)
* [Errors](#errors)
* [Status and Health endpoints](#status-and-health-endpoints)
* [References](#references)

### Introduction
---

### URL Construction
---

### Operations over resources
---

### Status codes
---

### Payload formatting
---

### Filters
---

### Pagination
---

### HATEOAS
---

### API Versioning
---

### API throughput restrictions
---

### OAuth
---

### Errors
---

## Status and Health endpoints
---

A good practice for you REST API is to reserve a couple of endpoints for checking the status and health/integrity of the API.

### Status endpoint

The status endpoint allows third party applications to check if your API is UP or DOWN.

A good endpoint could be **/status**.

The convention for this endpoint is to return a 200 status code response with a very simple response. For example:

{
    "status":"UP"
}

Any other response status should be interpreted as a outline API.

### Health endpoint

The health endpoint goes a step further and it does not only informs about the availability of the API but also about its integrity and health.

A good endpoint could be **/health**.

The response in case that our API is healthy could be very similar to the one returned by the status endpoint, a 200 response with the following body:

```json
> Response body : { "health" : "OK" }
```

We should check the status of every needed sub-component for our API to work correctly. For example, we could check the status of any databases or external services.

In case we need a more verbose response, we could enable a second endpoint whose response could be more detailed. For example, **/health/systems** could return a 200 response with the following body:

```json
> Response body :
{
    "health" : {
        "subsystem A" : "OK",
        "subsystem B" : "KO",
        "subsystem C" : "OK",
        "subsystem D" : "OK"
    }
}
```

This endpoint **should not be published to third party applications** because this information is typically needed for internal development or architecture issues.


### References
---

* [Link](http://www.url.to) Description
* [oficialsite.org](http://www.oficialwebsite.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/example/) Cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
