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

## Introduction
---

## URL Construction
---

## Operations over resources
---

## Status codes
---

## Payload formatting
---

## Filters
---

## Pagination
---

## HATEOAS
---

## API Versioning
---

## API throughput restrictions
---
For performance reasons and to ensure a homogeneous response times APIs, it is good practice to limit the consumption of APIs. This limitation can be performed based on many factors:

* **Limit requests in a time slot for an authenticated user.**. Such limitations are usually carried out in public APIs to control abusive access to the APIs. There are several approaches such as restricting the number of day / month requests for authenticated users.
* **Limit requests for public / private consumption API depending on the authenticated user profile**. Usually public APIs have limited consumption, always with the concept of ensuring homogeneous consumption of all users allowing resizes infrastructure in stages. Now another factor to consider, the payment appears APIs. If someone pays for higher demand requests, we can not make this service affects the service consumer of public APIs, so normally corresponding changes will be made in infrastructure to ensure the number of requests the client demands, and there is a unique routing requests to the user profile.

To manage the rate of requests are often used the following headers in responses of each request:

* **X-Rate-Limit-Limit**: The number of allowed requests in the current period
* **X-Rate-Limit-Remaining**: The number of remaining requests in the current period
* **X-Rate-Limit-Reset**: The number of seconds left in the current period. It is necessary to clarify at this point that should not be confused with a timestamp, you should be the seconds remaining to avoid problems with time zones.

As we can see in the following [link](http://stackoverflow.com/questions/16022624/examples-of-http-api-rate-limiting-http-response-headers), There are multiple APIs that use these headers (and sometimes more), to inform the user of the limits.

For ending this section, when the request limit is reached, the response will return this code status: ***HTTP - 429 Too Many Requests*** as indicated in the [RFC 6585](http://tools.ietf.org/html/rfc6585#section-4)


## OAuth
---

## Errors
---

## Status and Health endpoints
---

A good practice for you REST API is to reserve a couple of endpoints for checking the status and health/integrity of the API.

### Status endpoint

The status endpoint allows third party applications to check if your API is UP or DOWN.

A good endpoint could be **/status**.

The convention for this endpoint is to return a 200 status code response with a very simple response. For example:

```json
{
    "status":"UP"
}
```

Any other response status should be interpreted as an outline API.

### Health endpoint

The health endpoint goes a step further and it does not only informs about the availability of the API but also about its integrity and health.

A good endpoint could be **/health**.

The response in case that our API is healthy could be very similar to the one returned by the status endpoint, a 200 response with the following body:

```json
{
    "health" : "OK"
}
```

We should check the status of every needed sub-component for our API to work correctly. For example, we could check the status of any databases or external services.

In case we need a more verbose response, we could enable a second endpoint whose response could be more detailed. For example, **/health/systems** could return a 200 response with the following body:

```json
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
